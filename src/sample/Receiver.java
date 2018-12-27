package sample;

import sample.controllers.HostLobbyController;
import sample.controllers.LobbyController;

import static sample.Main.socketHandler;

public class Receiver implements  Runnable{

    private HostLobbyController hostLobbyController;
    private LobbyController lobbyController;
    
    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    public void setHostLobbyController(HostLobbyController hostLobbyController) {
        this.hostLobbyController = hostLobbyController;
    }

    @Override
    public void run() {
        String [] data = socketHandler.receiveMessage();
        if (data[0].equals("03")) {
            if (lobbyController!=null) {
                lobbyController.updateListing(data);
            }
        }
    }
}
