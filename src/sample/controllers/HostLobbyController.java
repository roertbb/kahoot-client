package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import sample.ScreenManager;

import java.io.IOException;

import static sample.Main.socketHandler;

public class HostLobbyController {
    @FXML ListView playersListing;
    @FXML Button start;

    private ScreenManager screenManager = new ScreenManager();

    public HostLobbyController() {
//        socketHandler.sendMessage("GET_ROOMS",null);
//        String [] playersData = socketHandler.receiveMessage();
    }

    public void updateListing(String [] data) {

    }

    public void startKahoot(ActionEvent actionEvent) throws IOException {
        socketHandler.sendMessage("START_KAHOOT",null);
        //screenManager.setScreen("");
    }
}
