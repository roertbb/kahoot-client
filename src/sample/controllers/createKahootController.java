package sample.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.models.Question;
import java.io.IOException;
import java.util.ArrayList;

public class createKahootController  {

    @FXML public ListView questionList;
    @FXML private TextArea question;
    @FXML private TextField answerA;
    @FXML private TextField answerB;
    @FXML private TextField answerC;
    @FXML private TextField answerD;
    @FXML private TextField timeForQuestion;
    @FXML private ToggleGroup answer;

    private ArrayList<Question> questions = new ArrayList<>();

    @FXML
    public void AddNewQuestion(ActionEvent actionEvent) throws IOException {
        RadioButton selectedToggle = (RadioButton) answer.getSelectedToggle();
        String correctAnswer = selectedToggle.getText();
        Question q = new Question(question.getText(),answerA.getText(),answerB.getText(),answerC.getText(),answerD.getText(),correctAnswer,Integer.parseInt(timeForQuestion.getText()));
        questions.add(q);
        questionList.setItems(FXCollections.observableArrayList(questions));
    }
}
