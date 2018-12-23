package sample.controllers;

import javafx.event.ActionEvent;
import sample.ScreenManager;

import java.io.IOException;

import static sample.Main.getSocketHandler;

public class MainController {

    private ScreenManager screenManager = new ScreenManager();

    public MainController() throws IOException {
    }

    public void createKahoot(ActionEvent actionEvent) throws IOException {
        //getSocketHandler().sendMessage("MESSAGE","test message");
        screenManager.setScreen("createKahoot", actionEvent);
    }
}
