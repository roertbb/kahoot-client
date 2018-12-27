package sample;

import sample.controllers.ChooseKahootController;
import sample.controllers.HostLobbyController;
import sample.controllers.LobbyController;

import static sample.Main.socketHandler;

public class Receiver implements Runnable {

    private boolean running = true;
    private HostLobbyController hostLobbyController;
    private LobbyController lobbyController;
    private ChooseKahootController chooseKahootController;

    public void setChooseKahootController(ChooseKahootController chooseKahootController) {
        this.chooseKahootController = chooseKahootController;
    }

    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    public void setHostLobbyController(HostLobbyController hostLobbyController) {
        this.hostLobbyController = hostLobbyController;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        try {
            while(this.running) {
                System.out.println("running");
                //String [] data = socketHandler.receiveMessage();
                char [] buffer = new char [1024];
                socketHandler.getInputStreamReader().read(buffer);
                String [] data = String.valueOf(buffer).split("\\|");
                System.out.println(data);
                if (data[0].equals("03")) {
                    if (chooseKahootController!=null) {
                        chooseKahootController.updateListing(data);
                    }
                }
            }
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            this.setRunning(false);
            System.out.println("EXCEPT");
            e.printStackTrace();
        }
    }
}
