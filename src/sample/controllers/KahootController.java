package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import static sample.Main.socketHandler;

public class KahootController {

    @FXML Pane overlayPane;
    @FXML Label overlayLabel;
    @FXML Pane questionPane;
    @FXML Label question;
    @FXML Button answerA;
    @FXML Button answerB;
    @FXML Button answerC;
    @FXML Button answerD;

    public void initialize() {
        socketHandler.receiver.setLobbyController(null);
        socketHandler.receiver.setKahootController(this);
    }

    public void clickedAnswerA(ActionEvent actionEvent) {
        this.sendAnswer("A");
    }
    public void clickedAnswerB(ActionEvent actionEvent) {
        this.sendAnswer("B");
    }
    public void clickedAnswerC(ActionEvent actionEvent) {
        this.sendAnswer("C");
    }
    public void clickedAnswerD(ActionEvent actionEvent) {
        this.sendAnswer("D");
    }

    public void receivedQuestion(String [] data) {
        String [] processedData = data[1].split("#");
        this.question.setText(processedData[0]);
        this.answerA.setText(processedData[1]);
        this.answerB.setText(processedData[2]);
        this.answerC.setText(processedData[3]);
        this.answerD.setText(processedData[4]);
        this.overlayPane.setVisible(false);
        this.questionPane.setVisible(true);
    }

    public void sendAnswer(String answer) {
        socketHandler.sendMessage("SEND_ANSWER", answer);
        this.overlayLabel.setText("Answer send... waiting for other players ;)");
        this.overlayPane.setVisible(true);
        this.questionPane.setVisible(false);
    }
}
