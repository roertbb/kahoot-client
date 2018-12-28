package sample;

import javafx.application.Platform;
import sample.controllers.*;

import static sample.Main.socketHandler;

public class Receiver implements Runnable {

    private boolean running = true;
    private HostLobbyController hostLobbyController;
    private LobbyController lobbyController;
    private ChooseKahootController chooseKahootController;
    private KahootController kahootController;
    private HostKahootController hostKahootController;

    public void setChooseKahootController(ChooseKahootController chooseKahootController) {
        this.chooseKahootController = chooseKahootController;
    }
    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }
    public void setHostLobbyController(HostLobbyController hostLobbyController) {
        this.hostLobbyController = hostLobbyController;
    }
    public void setKahootController(KahootController kahootController) {
        this.kahootController = kahootController;
    }

    public void setHostKahootController(HostKahootController hostKahootController) {
        this.hostKahootController = hostKahootController;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        try {
            while(this.running) {
                String [] data = socketHandler.receiveMessage();
                System.out.println("running - " + data[0]);
                // kahoots listing
                if (data[0].equals("03")) {
                    if (chooseKahootController!=null) {
                        chooseKahootController.updateListing(data);
                    }
                }
                // join room ack
                else if (data[0].equals("04")) {
                    if (chooseKahootController!=null) {
                        chooseKahootController.joinKahootAck(data);
                    }
                }
                // receive players in room
                else if (data[0].equals("05")) {
                    if (lobbyController!=null) {
                        lobbyController.updateUsersList(data);
                    }
                    if (hostLobbyController!=null) {
                        hostLobbyController.updateUsersList(data);
                    }
                }
                // start kahoot
                else if (data[0].equals("06")) {
                    if (lobbyController!=null) {
                        lobbyController.startKahootAck();
                    }
                    if (hostLobbyController!=null) {
                        hostLobbyController.startKahootAck();
                    }
                }
                // receive question
                else if (data[0].equals("07")) {
                    if (kahootController!=null) {
                        Platform.runLater(() -> kahootController.receivedQuestion(data));
                    }
                    if (hostKahootController!=null) {
                        hostKahootController.receivedQuestion(data);
                    }
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            this.setRunning(false);
            System.out.println("RECEIVER EXCEPTION");
            e.printStackTrace();
        }
    }
}
