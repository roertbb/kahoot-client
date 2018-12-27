package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;


import static sample.Main.receiver;

public class LobbyController {
    @FXML ListView playersListing;

    public void  LobbyController() {
        receiver.setLobbyController(this);
    }

    public void updateListing(String [] data) {
        System.out.println(data);
    }
}
