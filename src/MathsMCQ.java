import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MathsMCQ implements Assessment{

    public MathsMCQ() {super();}

    // Return information about the assessment
    public String getInformation() {
        return "This is a Maths Assessment";
    }

    // Return the final date / time for submission of completed assessment
    public Date getClosingDate() {
        //Return the current date for now
        Date d = new Date();
        return d;
    }

    // Return a list of all questions and answer options
    public List<Question> getQuestions() {
        //Return a null list for the moment
        List<Question> l = null;
        return l;
    }

    // Return one question only with answer options
    public Question getQuestion(int questionNumber) throws
            InvalidQuestionNumber {
        if(questionNumber > 3) {
            throw new InvalidQuestionNumber();
        }
        else {
            //Return corresponding question (null for now)
            return null;
        }
    }

    // Answer a particular question
    public void selectAnswer(int questionNumber, int optionNumber) throws
            InvalidQuestionNumber, InvalidOptionNumber {
        //Implement later
    }

    // Return selected answer or zero if none selected yet
    public int getSelectedAnswer(int questionNumber) {
        //Implement later
        return 0;
    }

    // Return studentid associated with this assessment object
    // This will be preset on the server before object is downloaded
    public int getAssociatedID() {
        //Implement later
        return 0;
    }
}
