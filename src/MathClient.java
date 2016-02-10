import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class MathClient {
    public static void main(String args[]) {
        try {
            //Setting up registry and server
            String name = "ExamServer";
            Registry registry = LocateRegistry.getRegistry("localhost");
            ExamServer exam = (ExamServer) registry.lookup(name);

            //Logging in
            Scanner in = new Scanner(System.in);
            System.out.println("Enter Username");
            int username = Integer.parseInt(in.nextLine());
            System.out.println("Enter Password");
            String password = in.nextLine();
            //Login token received
            int token = exam.login(username, password);

            //Display available assignments for user
            System.out.println("Would you like a summary of available assignments? (Answer Y or N)");
            String showAssignments = in.nextLine();
            if(showAssignments.equals("Y")) {
                List<String> l = exam.getAvailableSummary(token, username);
                System.out.println(l);
            }

            System.out.println("Please enter the course code of the assignment you wish to complete:");
            String courseCode = in.nextLine();
            Assessment a = exam.getAssessment(token, username, courseCode);
            System.out.println(a.getInformation());
            System.out.println("Closing date is: "+a.getClosingDate());
//            System.out.println(a.getQuestions());
//            System.out.println(a.getQuestion(1).getQuestionNumber());
//            System.out.println(a.getQuestion(1).getQuestionDetail());
//            String[] answers = a.getQuestion(1).getAnswerOptions();
//            for (int i=0; i<answers.length;i++) {
//                System.out.println("Option "+(i+1)+":"+answers[i]);
//            }
//            a.selectAnswer(1,2);
//            System.out.println(a.getSelectedAnswer(1));
//            a.selectAnswer(1,3);
//            System.out.println(a.getSelectedAnswer(1));
//            a.selectAnswer(2,4);
//            System.out.println(a.getSelectedAnswer(2));
//            exam.submitAssessment(token,123456,a);
//            System.out.println(exam.queryResults(token, 123456, "4ECE"));
//
//            a.selectAnswer(1,4);
//            exam.submitAssessment(token,123456,a);
//            System.out.println(exam.queryResults(token, 123456, "4ECE"));
        }
        catch (Exception e) {
            System.err.println("MathClient exception");
            e.printStackTrace();
        }
    }
}
