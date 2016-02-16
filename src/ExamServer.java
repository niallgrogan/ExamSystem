/*
Niall Grogan - 12429338
Stephen Dooley - 12502947
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ExamServer extends Remote {

	// Return an access token that allows access to the server for some time period
	int login(String studentid, String password) throws
		UnauthorizedAccess, RemoteException;

	// Return a summary list of Assessments currently available for this studentid
	List<String> getAvailableSummary(int token, String studentid) throws
		UnauthorizedAccess, NoMatchingAssessment, RemoteException;

	// Return an Assessment object associated with a particular course code
	Assessment getAssessment(int token, String studentid, String courseCode) throws
		UnauthorizedAccess, NoMatchingAssessment, RemoteException;

	// Submit a completed assessment
	String submitAssessment(int token, Assessment completed) throws
		UnauthorizedAccess, NoMatchingAssessment, RemoteException;

	//Query Assessment Results
	boolean[] queryResults(int token, String studentid) throws
			UnauthorizedAccess, NoMatchingAssessment, RemoteException;

}

