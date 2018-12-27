package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class LobbyController {
    @FXML ListView playersListing;

    public void updateListing(String [] data) {
        System.out.println(data);
    }
}
