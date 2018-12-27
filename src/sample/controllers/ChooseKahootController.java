package sample.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.ScreenManager;

import java.util.Arrays;

import static sample.Main.socketHandler;

public class ChooseKahootController {

    private ScreenManager screenManager = new ScreenManager();
    Stage current = null;

    @FXML ListView kahootListing;
    @FXML Button choose;
    @FXML TextField nick;
    @FXML TextField pin;

    public void initialize() {
        socketHandler.sendMessage("GET_ROOMS",null);
        socketHandler.receiver.setChooseKahootController(this);
    }

    public void updateListing(String [] data) {
        String[] kahootIds = Arrays.copyOfRange(data, 1, data.length);
        kahootListing.setItems(FXCollections.observableArrayList(kahootIds));
    }

    public void joinKahoot(ActionEvent actionEvent) {
        String roomId = (this.kahootListing.getSelectionModel().getSelectedItems()).toString();
        roomId = roomId.replace("[","").replace("]","");
        System.out.println(roomId);
        String pin = this.pin.getText();
        String nick = this.nick.getText();
        if (roomId != null && pin != null && nick != null) {
            current = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            socketHandler.sendMessage("JOIN_ROOM",roomId + "|" + pin + "|" + nick + "|");
        }
    }

    public void joinKahootAck(String [] data) {
        System.out.println(data[1]);
        if (data[1].equals("success")) {
            screenManager.setScreen("lobby", this.current);
        }
    }
}
