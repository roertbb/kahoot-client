package sample.models;

public class Question {
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private String correctAnswer;
    private int timeForAnswer;

    public Question(String question, String answerA, String answerB, String answerC, String answerD, String correctAnswer, int timeForAnswer) {
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
        this.timeForAnswer = timeForAnswer;
    }

    @Override
    public String toString() {
        return this.question;
    }
}
