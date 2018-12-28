package sample.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.util.Arrays;

import static sample.Main.socketHandler;

public class LobbyController {
    @FXML ListView playersListing;

    public void initialize() {
        socketHandler.receiver.setChooseKahootController(null);
        socketHandler.receiver.setLobbyController(this);
    }

    public void updateUsersList(String [] data) {
        String[] playersNicks = Arrays.copyOfRange(data, 1, data.length-1);
        playersListing.setItems(FXCollections.observableArrayList(playersNicks));
    }
}
