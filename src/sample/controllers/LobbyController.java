package sample.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.ScreenManager;

import java.util.Arrays;

import static sample.Main.socketHandler;

public class LobbyController {
    @FXML ListView playersListing;
    @FXML AnchorPane panel;

    private ScreenManager screenManager = new ScreenManager();
    private Stage stage;

    public void initialize() {
        socketHandler.receiver.setChooseKahootController(null);
        socketHandler.receiver.setLobbyController(this);
        socketHandler.sendMessage("REQUEST_ROOM_MEMBERS",null);
    }

    public void updateUsersList(String [] data) {
        String[] playersNicks = Arrays.copyOfRange(data, 1, data.length-1);
        playersListing.setItems(FXCollections.observableArrayList(playersNicks));
    }

    public void startKahootAck() {
        this.stage = (Stage) panel.getScene().getWindow();
        screenManager.setScreen("kahoot", this.stage);
    }
}
