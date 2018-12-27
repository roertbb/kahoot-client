package sample.controllers;

import javafx.event.ActionEvent;
import sample.ScreenManager;

public class MainController {

    private ScreenManager screenManager = new ScreenManager();

    public void joinKahoot(ActionEvent actionEvent){
        screenManager.setScreen("chooseKahoot", actionEvent);
    }

    public void createKahoot(ActionEvent actionEvent) {
        screenManager.setScreen("createKahoot", actionEvent);
    }
}
