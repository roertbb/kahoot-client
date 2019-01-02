package sample.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.ScreenManager;

import java.util.Arrays;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.length;
import static sample.Main.socketHandler;

public class ChooseKahootController {

    private ScreenManager screenManager = new ScreenManager();
    Stage current = null;

    @FXML ListView kahootListing;
    @FXML Button choose;
    @FXML TextField nick;
    @FXML TextField pin;
    @FXML Label warning;

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
        String parsedRoomId = roomId.replace("[","").replace("]","");
        String pin = this.pin.getText();
        String nick = this.nick.getText();
        if (parsedRoomId.length() == 0)
            this.warning.setText("Choose room from above");
        else if (pin.length() == 0)
            this.warning.setText("Enter pin");
        else if (nick.length() == 0)
            this.warning.setText("Enter nick");
        else
            socketHandler.sendMessage("JOIN_ROOM",parsedRoomId + "|" + pin + "|" + nick + "|");
    }

    public void joinKahootAck(String [] data) {
        if (data[1].equals("success")) {
            this.current = (Stage) kahootListing.getScene().getWindow();
            screenManager.setScreen("lobby", this.current);
        } else if (data[1].equals("nick is not unique")) {
            this.warning.setText("User with such a nick already exists");
        }
        else if (data[1].equals(("incorrect pin"))){
            this.warning.setText("Incorrect pin");
        }
    }
}
