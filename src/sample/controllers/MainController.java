package sample.controllers;

import javafx.event.ActionEvent;
import java.io.IOException;

import static sample.Main.getSocketHandler;

public class MainController {

    public void createKahoot(ActionEvent actionEvent) throws IOException {
        getSocketHandler().sendMessage("MESSAGE","test message");
    }
}
