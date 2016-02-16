import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ExamEngine implements ExamServer {

    private List<String> assessmentList = new ArrayList<>(1);
    private Assessment ass1;
    private Map<String, Assessment> completedAssignments = new HashMap<>();

    // Constructor is required
    public ExamEngine() {
        //super();
        assessmentList.add("Maths MCQ");
        assessmentList.add("Programming MCQ");
        ass1 = new MathsMCQ();
    }

    // Implement the methods defined in the ExamServer interface...
    // Return an access token that allows access to the server for some time period
    public int login(String studentid, String password) throws 
                UnauthorizedAccess, RemoteException {

    	// allow user attempt to login 10 times
    	int count = 0;
    	int maxTries = 10;
    	while (true) {
//    		try {
    			if (studentid.equals("a")) {
		            if (password.equals("a")) {
		                return 999;
		            }
		            else {throw new UnauthorizedAccess("Invalid Password");}
		        }
		        else {throw new UnauthorizedAccess("Invalid Student ID");}
//    		} catch (UnauthorizedAccess e) {
//    			System.out.println("HERE ***************");
//    			if (++count == maxTries) throw e;
//    			else System.out.println(e.getMessage());
//    		}
    	}
    }

    // Return a summary list of Assessments currently available for this studentid
    public List<String> getAvailableSummary(int token, String studentid) throws
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        if (token == 999) {
            if (studentid.equals("a")) {
                return assessmentList;
            }
            else {
                throw new NoMatchingAssessment("No Matching Assessment for Student");
            }
        }
        else {
            throw new UnauthorizedAccess("Student Does not have Access to this List");
        }
    }

    // Return an Assessment object associated with a particular course code
    public Assessment getAssessment(int token, String studentid, String courseCode) throws
    						UnauthorizedAccess, NoMatchingAssessment, RemoteException {
        try { 
	    	if (token == 999) {
	            if (studentid.equals("a")) {
	                if (courseCode.equals("Maths MCQ")) {
                        return ass1;
	                }
	                else {
	                    throw new NoMatchingAssessment("No Matching Assessment for Course Code " + courseCode);
	                }
	            }
	            else {
	                throw new NoMatchingAssessment("No Matching Assessment for Student " + studentid);
	            }
	        }
	        else {
	            throw new UnauthorizedAccess("Student Does not have Access to this List");
	        }
        } catch(Exception e) {
        	System.out.println(e.getMessage());
        }
        return null;
    }

    // Submit a completed assessment
    public void submitAssessment(int token, String studentid, Assessment completed) throws 
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        //For the moment we are assuming the user can only submit one type of assignment
        String identifier = studentid;
        Date timeOfSubmission = new Date();
        if(timeOfSubmission.before(completed.getClosingDate())) {
            completedAssignments.put(identifier, completed);
        }
        else {
            System.out.println("Deadline Expired");
        }
    }

    public boolean[] queryResults(int token, String studentid, String courseCode) throws
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        int[] usersAnswers = completedAssignments.get(studentid).getUserAnswers();
        int[] answers = completedAssignments.get(studentid).getAnswers();
        boolean[] results = new boolean[answers.length];

        for(int i=0; i<usersAnswers.length; i++) {
            if(usersAnswers[i] == answers[i]) {
                results[i] = true;
            }
            else {results[i] = false;}
        }

        return results;
    }

    public static void main(String[] args) {
//        if (System.getSecurityManager() == null) {
//            System.setSecurityManager(new SecurityManager());
//        }
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
