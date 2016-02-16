/*
Niall Grogan - 12429338
Stephen Dooley - 12502947
 */
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class MathClient {
    public static void main(String args[]) {
        try {
            if (System.getSecurityManager() == null) {
                System.setProperty("java.security.policy", "java.policy");
                System.setSecurityManager(new SecurityManager());
            }
            //Setting up registry and server
            String name = "ExamServer";
            Registry registry = LocateRegistry.getRegistry("localhost");
            ExamServer exam = (ExamServer) registry.lookup(name);
            Scanner in = new Scanner(System.in);
            boolean completed = false;
            boolean loginSuccesfull = false;
            String user = "";

            String username = "";
            int token = 0;

            //Logging in
            while(!loginSuccesfull) {
                System.out.println("Enter Username");
                username = in.nextLine();
                System.out.println("Enter Password");
                String password = in.nextLine();
                token = exam.login(username, password);
                if (token == 100) {
                    loginSuccesfull = false;
                    System.out.println("\nInvalid login credentials");
                }
                else if (token == 999) {
                    loginSuccesfull = true;
                }
            }

            while(!completed) {
                displayAssignments(in, exam, token, username);

                Assessment a = startAssignment(in, exam, token, username);
                user = a.getAssociatedID();
                gradeAssessment(exam, token, username, a);

                System.out.println("\nWould you like to make another submission? (y/n)");
                String reSubmission = in.nextLine();
                if(reSubmission.equals("n")) {
                    completed = true;
                }
            }

            System.out.println("\nUser '"+user+"' has been logged out"
                    +"\nThanks for using the exam system");
        }
        catch (Exception e) {
            System.err.println("MathClient exception");
            e.printStackTrace();
        }
    }

    private static void completeAssessment(Assessment a, Scanner in) throws
            InvalidQuestionNumber, InvalidOptionNumber {
        for(int i=0; i<a.getQuestions().size(); i++){
            System.out.println("____________________________________________");
            System.out.println("\nQuestion Number "+a.getQuestion(i+1).getQuestionNumber()+":");
            System.out.println(a.getQuestion(i+1).getQuestionDetail());
            String[] answers = a.getQuestion(i+1).getAnswerOptions();
            for (int j=0; j<answers.length;j++) {
                System.out.println("Option "+(j+1)+": "+answers[j]);
            }

            System.out.println("\nPlease select correct option for question "+a.getQuestion(i+1).getQuestionNumber()+"\n" +
                    "For Example: If you want to select Option one of a question you would enter 1");
            String answer1 = in.nextLine();
            a.selectAnswer(a.getQuestion(i+1).getQuestionNumber(),Integer.parseInt(answer1));
        }
    }

    private static void displayAssignments(Scanner in, ExamServer exam, int token, String username) throws
            UnauthorizedAccess, NoMatchingAssessment, RemoteException {
        //Display available assignments for user
        System.out.println("\nWould you like a summary of available assignments? (y/n)");
        String showAssignments = in.nextLine();
        if(showAssignments.equals("y")) {
            List<String> l = exam.getAvailableSummary(token, username);
            if(l == null) System.out.println("No assessments available for student: " + username
                                           + "\nPlease come back later");
            else System.out.println(l);
        }
        else {
            // log user out
            System.out.println("\nThanks for using the exam system. \nYou have been logged out.");
            System.exit(0);
        }
    }

    private static Assessment startAssignment(Scanner in, ExamServer exam, int token, String username) throws
            UnauthorizedAccess, NoMatchingAssessment, RemoteException, InvalidOptionNumber, InvalidQuestionNumber {

        System.out.println("\nPlease enter the name of the assessment you wish to complete:");
        String courseCode = in.nextLine();
        Assessment a = exam.getAssessment(token, username, courseCode);
        System.out.println("\n"+a.getInformation());

        System.out.println("\nWould you like to begin? (y/n)");
        String begin = in.nextLine();
        if(begin.equals("y")) {
            completeAssessment(a,in);
        }

        boolean submitted = false;

        while(!submitted) {
            System.out.println("\nWould you like to submit your assessment? (y/n)");
            String submit = in.nextLine();
            if(submit.equals("y")) {
                submitted = true;
            }
            else {
                System.out.println("\nWould you like to redo your assessment? (y/n)");
                String redo = in.nextLine();
                if(redo.equals("y")) {
                    completeAssessment(a,in);
                }
            }
        }
        return a;
    }

    private static void gradeAssessment(ExamServer exam, int token, String username, Assessment a) throws
           UnauthorizedAccess, NoMatchingAssessment, RemoteException, InvalidQuestionNumber {
        String confirmation = exam.submitAssessment(token, a);
        if(confirmation.equals("Deadline Expired")) {
            System.out.println("Deadline Expired\nYou will now be signed out");
            System.exit(0);
        }
        double counter=0.0;
        boolean[] results = exam.queryResults(token, username);
        System.out.println("____________________________________________");
        for(int i=0; i<results.length; i++) {
            if(results[i]) {
                System.out.println("\nYou answered "+a.getQuestion(i+1).getQuestionNumber()+" Correctly"+
                        "\nThe correct answer is Option "+a.getAnswers()[i]);
                counter++;
            }
            else {
                System.out.println("\nYou answered "+a.getQuestion(i+1).getQuestionNumber()+" Incorrectly"+
                        "\nThe correct answer is Option "+a.getAnswers()[i]);
            }
        }
        System.out.println("\nOverall you scored "+(counter/results.length)*100+"%");
        System.out.println("____________________________________________");
    }
}
