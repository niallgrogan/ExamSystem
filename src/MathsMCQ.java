import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MathsMCQ implements Assessment{

    private int numQuestions = 2;
    private int numAnswerOptions = 4;
    //Not sure how these should be instantiated
    private List<Question> questionList = new ArrayList<Question>(2);
    private int[] selectedAnswers = {0,0};

    public MathsMCQ() {
        //Not sure if this is needed
        //super();
        String[] qs1Answers = {"1","2","3","4"};
        QuestionOne qs1 = new QuestionOne(1,"2+2=",qs1Answers);
        String[] qs2Answers = {"2","3","4","5"};
        QuestionOne qs2 = new QuestionOne(2,"2+1=",qs2Answers);
        //This may be an issue
        questionList.add(qs1);
        questionList.add(qs2);
    }

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
        return questionList;
    }

    // Return one question only with answer options
    public Question getQuestion(int questionNumber) throws
            InvalidQuestionNumber {

        switch (questionNumber) {
            case 1: return questionList.get(1);
            case 2: return questionList.get(2);
            default: throw new InvalidQuestionNumber();
        }
    }

    // Answer a particular question
    public void selectAnswer(int questionNumber, int optionNumber) throws
            InvalidQuestionNumber, InvalidOptionNumber {
        if(questionNumber <= numQuestions & questionNumber > 0) {
            if (optionNumber <= numAnswerOptions & optionNumber > 0) {
                selectedAnswers[questionNumber] = optionNumber;
            }
            else {throw new InvalidOptionNumber();}
        }
        else {throw new InvalidQuestionNumber();}
    }

    // Return selected answer or zero if none selected yet
    public int getSelectedAnswer(int questionNumber) {
        return selectedAnswers[questionNumber];
    }

    // Return studentid associated with this assessment object
    // This will be preset on the server before object is downloaded
    public int getAssociatedID() {
        //Implement later
        return 0;
    }
}
