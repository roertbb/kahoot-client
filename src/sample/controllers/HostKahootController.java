package sample.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static sample.Main.socketHandler;

public class HostKahootController {

    @FXML ListView points;
    @FXML BarChart answerChart;
    @FXML ListView log;
    @FXML Label status;
    @FXML Button exit;

    HashMap<String,Integer> answers;

    public void initialize() {
        socketHandler.receiver.setHostLobbyController(null);
        socketHandler.receiver.setHostKahootController(this);
        this.initializeHashMap();
    }

    private void initializeHashMap() {
        answers = new HashMap<>();
        answers.put("A",0);
        answers.put("B",0);
        answers.put("C",0);
        answers.put("D",0);
    }

    public void receivedQuestion(String [] data) {
        this.status.setText("Status: Users got question, they're answering");
    }

    public void summarizeQuestion(String[] data) {
        this.status.setText("Status: Question finished");
    }

    public void receiveAnswer(String[] data) {
        // add notification to log
        log.getItems().add("User " + data[1] + " answered " + data[2] + " which is " + (data.length > 5 ? "correct" : "incorrect") + " within " + data[3] + " seconds");

        // refresh list with points if exists
        if (data.length > 5) {
            points.setItems(FXCollections.observableArrayList(Arrays.copyOfRange(data,4,data.length-1)));
        }

        // update chart data
        answers.replace(data[2],answers.get(data[2])+1);

        // update chart
        XYChart.Series series = new XYChart.Series();
        for (Map.Entry<String, Integer> entry : answers.entrySet()) {
            series.getData().add(new XYChart.Data(entry.getKey(), entry.getValue()));
        }
        answerChart.getData().clear();
        answerChart.layout();
        answerChart.getData().addAll(series);
    }

    public void clearData(String[] data) {
        this.status.setText("Status: Preparing before question");
        this.initializeHashMap();
        answerChart.getData().clear();
        answerChart.layout();
    }
}
