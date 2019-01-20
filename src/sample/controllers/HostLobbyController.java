package sample.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.ScreenManager;

import java.io.IOException;
import java.util.Arrays;

import static sample.Main.socketHandler;

public class HostLobbyController {
    @FXML AnchorPane panel;
    @FXML ListView playersListing;
    @FXML Button start;
    @FXML Label pin;

    private ScreenManager screenManager = new ScreenManager();
    private Stage stage;

    public void initialize() {
        socketHandler.receiver.setChooseKahootController(null);
        socketHandler.receiver.setHostLobbyController(this);
        socketHandler.sendMessage("GET_PIN",null);
    }

    public void updateUsersList(String [] data) {
        String[] playersNicks = Arrays.copyOfRange(data, 1, data.length);
        playersListing.setItems(FXCollections.observableArrayList(playersNicks));
    }

    public void startKahoot(ActionEvent actionEvent) throws IOException {
        socketHandler.sendMessage("START_KAHOOT",null);
    }

    public void startKahootAck() {
        this.stage = (Stage) panel.getScene().getWindow();
        screenManager.setScreen("hostKahoot", this.stage);
    }

    public void setPin(String[] data) {
        this.pin.setText("Pin: "+data[1]);
    }
}
