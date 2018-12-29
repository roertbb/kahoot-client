package sample.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static sample.Main.socketHandler;

public class HostKahootController {

    @FXML ListView points;
    @FXML BarChart answerChart;

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
        System.out.println(data);
    }

    public void summarizeQuestion(String[] data) {
        // after all clients send answers
    }

    public void receiveAnswer(String[] data) {
        System.out.println("len - " +  data.length);
        answers.replace(data[1],answers.get(data[1])+1);

        // refresh list with points if exists
        if (data.length > 3) {
            points.setItems(FXCollections.observableArrayList(Arrays.copyOfRange(data,3,data.length-1)));
        }

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
        this.initializeHashMap();
        answerChart.getData().clear();
        answerChart.layout();
    }
}
