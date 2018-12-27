package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.Arrays;

import static sample.Main.socketHandler;

public class ChooseKahootController {
    @FXML ListView kahootListing;
    @FXML Button choose;

    public void initialize() {
        socketHandler.sendMessage("GET_ROOMS",null);
        socketHandler.receiver.setChooseKahootController(this);
    }

    public void updateListing(String [] data) {
        String[] kahootIds = Arrays.copyOfRange(data, 1, data.length);
        kahootListing.setItems(FXCollections.observableArrayList(kahootIds));
    }
}
