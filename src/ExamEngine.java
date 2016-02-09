import sun.org.mozilla.javascript.internal.ast.Assignment;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExamEngine implements ExamServer {

    private List<String> assessmentList = new ArrayList<String>(1);
    private Assessment ass1;

    // Constructor is required
    public ExamEngine() {
        //super();
        assessmentList.add("MathsMCQ");
        ass1 = new MathsMCQ();
    }

    // Implement the methods defined in the ExamServer interface...
    // Return an access token that allows access to the server for some time period
    public int login(int studentid, String password) throws 
                UnauthorizedAccess, RemoteException {

        if (studentid == 123456 & password.equals("lala")) {
            return 999;
        }
        else {
            throw new UnauthorizedAccess("Invalid Student ID");
            //Not sure if I need to return anything
        }
    }

    // Return a summary list of Assessments currently available for this studentid
    public List<String> getAvailableSummary(int token, int studentid) throws
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        if (token == 999) {
            if (studentid == 123456) {
                return assessmentList;
            }
            else {
                throw new NoMatchingAssessment("Not Matching Assessment for Student");
            }
        }
        else {
            throw new UnauthorizedAccess("Student Does not have Access to this List");
        }
    }

    // Return an Assessment object associated with a particular course code
    public Assessment getAssessment(int token, int studentid, String courseCode) throws
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        if (token == 999) {
            if (studentid == 123456) {
                if (courseCode.equals("4ECE")) {
                    return ass1;
                }
                else {
                    throw new NoMatchingAssessment("Not Matching Assessment for Course Code");
                }
            }
            else {
                throw new NoMatchingAssessment("Not Matching Assessment for Student");
            }
        }
        else {
            throw new UnauthorizedAccess("Student Does not have Access to this List");
        }
    }

    // Submit a completed assessment
    public void submitAssessment(int token, int studentid, Assessment completed) throws 
                UnauthorizedAccess, NoMatchingAssessment, RemoteException {

        // TBD: You need to implement this method!
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
