package sample.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import sample.ScreenManager;

import java.io.IOException;
import java.util.Arrays;

import static sample.Main.socketHandler;

public class HostLobbyController {
    @FXML ListView playersListing;
    @FXML Button start;

    private ScreenManager screenManager = new ScreenManager();

    public void initialize() {
        socketHandler.receiver.setChooseKahootController(null);
        socketHandler.receiver.setHostLobbyController(this);
    }

    public void updateUsersList(String [] data) {
        String[] playersNicks = Arrays.copyOfRange(data, 1, data.length);
        playersListing.setItems(FXCollections.observableArrayList(playersNicks));
    }

    public void startKahoot(ActionEvent actionEvent) throws IOException {
        socketHandler.sendMessage("START_KAHOOT",null);
        //screenManager.setScreen("");
    }
}
