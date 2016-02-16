public class MathsQuestions implements Question {

    private int questionNumber;
    private String questionDetails;
    private String[] answerOptions = new String[4];

    public MathsQuestions(int number, String detail, String[] answers) {
        questionNumber = number;
        questionDetails = detail;
        answerOptions = answers;
    }

    // Return the question number
    public int getQuestionNumber() {
        return questionNumber;
    }

    // Return the question text
    public String getQuestionDetail() {
        return questionDetails;
    }

    // Return the possible answers to select from
    public String[] getAnswerOptions() {
        return answerOptions;
    }
}
