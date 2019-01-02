package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import sample.Receiver;
import sample.ScreenManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    @FXML BarChart answerChart;
    @FXML Label answerLabel;
    @FXML Button exit;

    private ScreenManager screenManager = new ScreenManager();

    public void initialize() {
        socketHandler.receiver.setLobbyController(null);
        socketHandler.receiver.setKahootController(this);
        socketHandler.sendMessage("CHECK_IF_ALREADY_STARTED",null);
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
        this.question.setWrapText(true);
        this.question.setText(processedData[0]);
        this.answerA.setText(processedData[1]);
        this.answerB.setText(processedData[2]);
        this.answerC.setText(processedData[3]);
        this.answerD.setText(processedData[4]);
        this.overlayPane.setVisible(false);
        this.questionPane.setVisible(true);
    }

    public void sendAnswer(String answer) {
        socketHandler.sendMessage("SEND_ANSWER", answer+"|");
        this.overlayLabel.setText("Answer send... waiting for other players ;)");
        this.overlayLabel.setVisible(true);
        this.answerLabel.setVisible(false);
        this.answerChart.setVisible(false);
        this.overlayPane.setVisible(true);
        this.questionPane.setVisible(false);
    }

    public void receivedAnswer(String[] data) {
        this.overlayPane.setVisible(true);
        this.questionPane.setVisible(false);
        this.overlayLabel.setVisible(false);

        // update chart
        HashMap<String, Integer> answers = new HashMap<>();
        answers.put("A",0);
        answers.put("B",0);
        answers.put("C",0);
        answers.put("D",0);
        String [] answersData = Arrays.copyOfRange(data,3,data.length);
        for (String s : answersData) {
            String [] entry = s.split(":");
            answers.replace(entry[0],Integer.valueOf(entry[1]));
        }
        XYChart.Series series = new XYChart.Series();
        for (Map.Entry<String,Integer> entry : answers.entrySet()) {
            series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }

        answerChart.getData().clear();
        answerChart.layout();
        answerChart.getData().addAll(series);
        String answerMessage;
        if (data[1].equals("1")) {
            answerMessage = "You answered correctly!";
        } else if (data[1].equals("0")) {
            answerMessage = "Your answer is wrong - correct one is "+data[2];
        } else {
            answerMessage = "You haven't sent us your answer :(";
        }
        this.answerLabel.setText(answerMessage);
        this.answerLabel.setVisible(true);
        this.answerChart.setVisible(true);
    }

    public void prepareQuestion(String[] data) {
        this.answerChart.setVisible(false);
        this.answerLabel.setVisible(false);
        if (data.length == 1 ) {
            this.overlayLabel.setText("Prepare before next question...");
        } else {
            this.overlayLabel.setText("It was last question, you are " + data[2] + " and you got " + data[3] + " points");
            this.exit.setVisible(true);
        }
        this.overlayLabel.setVisible(true);
    }

    public void exitKahoot(ActionEvent actionEvent) {
        socketHandler.receiver.setKahootController(null);
        screenManager.setScreen("main",actionEvent);
    }
}
