package sample;

import javafx.application.Platform;
import sample.controllers.*;

import java.io.IOException;
import java.net.SocketException;

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
            while (this.running) {
                String[] data = socketHandler.receiveMessage();
                if (data.length > 0) {
                    System.out.println("running - " + data[0]);
                    // kahoots listing
                    if (data[0].equals("03")) {
                        if (chooseKahootController != null) {
                            Platform.runLater(() -> chooseKahootController.updateListing(data));
                        }
                    }
                    // join room ack
                    else if (data[0].equals("04")) {
                        if (chooseKahootController != null) {
                            Platform.runLater(() -> chooseKahootController.joinKahootAck(data));
                        }
                    }
                    // receive players in room
                    else if (data[0].equals("14")) {
                        if (lobbyController != null) {
                            Platform.runLater(() -> lobbyController.updateUsersList(data));
                        }
                        if (hostLobbyController != null) {
                            Platform.runLater(() -> hostLobbyController.updateUsersList(data));
                        }
                    }
                    // start kahoot
                    else if (data[0].equals("06")) {
                        if (lobbyController != null) {
                            Platform.runLater(() -> lobbyController.startKahootAck());
                        }
                        if (hostLobbyController != null) {
                            Platform.runLater(() -> hostLobbyController.startKahootAck());
                        }
                    }
                    // receive question
                    else if (data[0].equals("07")) {
                        if (kahootController != null) {
                            Platform.runLater(() -> kahootController.receivedQuestion(data));
                        }
                        if (hostKahootController != null) {
                            Platform.runLater(() -> hostKahootController.receivedQuestion(data));
                        }
                    }
                    // receive information if answer was correct/incorrect
                    else if (data[0].equals("08")) {
                        if (kahootController != null) {
                            Platform.runLater(() -> kahootController.receivedAnswer(data));
                        }
                        if (hostKahootController != null) {
                            Platform.runLater(() -> hostKahootController.summarizeQuestion(data));
                        }
                    }
                    // prepare before next question
                    else if (data[0].equals("09")) {
                        if (kahootController != null) {
                            Platform.runLater(() -> kahootController.prepareQuestion(data));
                        }
                        if (hostKahootController != null) {
                            Platform.runLater(() -> hostKahootController.clearData(data));
                        }
                    }
                    // host receiving answer
                    else if (data[0].equals("10")) {
                        if (hostKahootController != null) {
                            Platform.runLater(() -> hostKahootController.receiveAnswer(data));
                        }
                    } else if (data[0].equals("12")) {
                        if (lobbyController != null) {
                            Platform.runLater(() -> lobbyController.ownerDisconnected());
                        }
                    }
                    // get pin
                    else if (data[0].equals("15")) {
                        if (hostLobbyController != null)
                            Platform.runLater(() -> hostLobbyController.setPin(data));
                    }
                }
            }
        } catch(SocketException e) {
            this.setRunning(false);
            System.err.println("Socket Exception");
            socketHandler.closeConnection();
        } catch (IOException e) {
            this.setRunning(false);
            System.err.println("Server Disconnected");
        } catch (Exception e) {
            this.setRunning(false);
            System.err.println("RECEIVER EXCEPTION");
            e.printStackTrace();
            socketHandler.closeConnection();
        }
    }
}
