/*
Niall Grogan - 12429338
Stephen Dooley - 12502947
 */
import java.io.Serializable;

public interface Question extends Serializable {

	// Return the question number
	int getQuestionNumber();

	// Return the question text
	String getQuestionDetail();

	// Return the possible answers to select from
	String[] getAnswerOptions();

}
