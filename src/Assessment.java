/*
Niall Grogan - 12429338
Stephen Dooley - 12502947
 */
import java.util.Date;
import java.util.List;
import java.io.Serializable;

public interface Assessment extends Serializable {

	// Return information about the assessment	
	String getInformation();

	// Return the final date / time for submission of completed assessment
	Date getClosingDate();

	// Return a list of all questions and answer options
	List<Question> getQuestions();

	// Return one question only with answer options
	Question getQuestion(int questionNumber) throws
		InvalidQuestionNumber;

	// Answer a particular question
	void selectAnswer(int questionNumber, int optionNumber) throws
		InvalidQuestionNumber, InvalidOptionNumber;

	// Return selected answer or zero if none selected yet
	int getSelectedAnswer(int questionNumber);

	// Return studentid associated with this assessment object
	// This will be preset on the server before object is downloaded
	String getAssociatedID();

	//Returns the correct answers to an assessment
	int[] getAnswers();

}