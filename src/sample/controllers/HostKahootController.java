package sample.controllers;

import static sample.Main.socketHandler;

public class HostKahootController {

    public void initialize() {
        socketHandler.receiver.setHostLobbyController(null);
        socketHandler.receiver.setHostKahootController(this);
    }

    public void receivedQuestion(String [] data) {
        System.out.println(data);
    }
}
