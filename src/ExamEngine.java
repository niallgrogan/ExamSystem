/*
Niall Grogan - 12429338
Stephen Dooley - 12502947
 */
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ExamEngine implements ExamServer {

    //Contains list of assessment names
    private List<String> assessmentList = new ArrayList<>(1);
    // Key-value pairs of assessments linked to a student id
    private Map<String, Assessment> completedAssignments = new HashMap<>();

    public ExamEngine() {
        //Setting Assessment names
        assessmentList.add("Maths MCQ");
        assessmentList.add("Programming MCQ");
    }

    // Return an access token that allows access to the server for some time period
    public int login(String studentid, String password) throws 
                UnauthorizedAccess, RemoteException {
        //Only permitting username "a" and password "a"
        try {
            if (studentid.equals("a")) {
                if (password.equals("a")) {
                    return 999;
                }
                else {throw new UnauthorizedAccess("Invalid Password");}
            }
            else {throw new UnauthorizedAccess("Invalid Student ID");}
        }
        //Returns an error code which can be handled by the client
        catch (UnauthorizedAccess e) {
            int errorCode = 100;
            System.out.println("Error Code: " + errorCode);
            System.out.println(e.getMessage() + "\n");
            return errorCode;
        }
    }

    // Return a summary list of Assessments currently available for this studentid
    public List<String> getAvailableSummary(int token, String studentid) throws
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {
        //Login token of 999 is always used
        try {
            if (token == 999) {
                if (studentid.equals("a")) {
                    return assessmentList;
                } else {
                    throw new NoMatchingAssessment("No Matching Assessment for Student");
                }
            } else {
                throw new UnauthorizedAccess("Student Does not have Access to this List");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n");
            return null;
        }

    }

    // Return an Assessment object associated with a particular course code
    public Assessment getAssessment(int token, String studentid, String courseCode) throws
    						UnauthorizedAccess, NoMatchingAssessment, RemoteException {
        try { 
	    	if (token == 999) {
	            if (studentid.equals("a")) {
                    switch(courseCode) {
                        case "Maths MCQ": return new MathsMCQ(studentid);
                        case "Programming MCQ": return new ProgrammingMCQ(studentid);
                        default: throw new NoMatchingAssessment("No Matching Assessment for Course Code " + courseCode);
                    }
	            }
	            else {throw new NoMatchingAssessment("No Matching Assessment for Student " + studentid);}
	        }
	        else {throw new UnauthorizedAccess("Student Does not have Access to this List");}
        } catch(Exception e) {System.out.println(e.getMessage());}

        return null;
    }

    // Submit a completed assessment (returns a confirmation string)
    public String submitAssessment(int token, Assessment completed) throws
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        //For the moment we are assuming the user can only submit one type of assignment
        String identifier = completed.getAssociatedID();
        Date timeOfSubmission = new Date();
        //Ensures the user cannot submit after
        if(timeOfSubmission.before(completed.getClosingDate())) {
            completedAssignments.put(identifier, completed);
            return "Submission Confirmed";
        }
        else {
            return "Deadline Expired";
        }
    }

    public boolean[] queryResults(int token, String studentid) throws
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        int[] answers = completedAssignments.get(studentid).getAnswers();
        boolean[] results = new boolean[answers.length];

        for(int i=0; i<answers.length; i++) {
            if(completedAssignments.get(studentid).getSelectedAnswer(i+1) == answers[i]) {
                results[i] = true;
            }
            else {results[i] = false;}
        }

        return results;
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "java.policy");
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "ExamServer";
            ExamServer engine = new ExamEngine();
            ExamServer stub =
                (ExamServer) UnicastRemoteObject.exportObject(engine, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("ExamEngine bound");
        } catch (Exception e) {
            System.err.println("ExamEngine exception:");
            e.printStackTrace();
        }
    }
}
