package sample;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketHandler {
    private Socket socket;
    private OutputStreamWriter outputStreamWriter;
    private InputStreamReader inputStreamReader;
    public Receiver receiver;
    private Thread t;

    public InputStreamReader getInputStreamReader() { return this.inputStreamReader; }

    SocketHandler() {
        try {
            socket = new Socket("127.0.0.1",1234);
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            inputStreamReader = new InputStreamReader(socket.getInputStream(), "UTF-8");
            receiver = new Receiver();
            this.t = new Thread(receiver);
            this.t.setDaemon(true);
            this.t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String messageType, String data) {

        String message = null;

        switch(messageType) {
            case "CLOSE_CONNECTION":
                message = "01|Disconnected";
                break;
            case "SEND_KAHOOT":
                message = "02|" + data;
                break;
            case "GET_ROOMS":
                message = "03|";
                break;
            case "JOIN_ROOM":
                message = "04|" + data;
                break;
            case "START_KAHOOT":
                message = "05|";
                break;
            default:
                System.out.println("Unrecognized message");
                message = "99|Unrecognized message";
                break;
        }
        try {
            outputStreamWriter.write(message);
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] receiveMessage() {
        char buffer[] = new char[1024];
        String [] data = new String[0];
        try {
            inputStreamReader.read(buffer);
            data = String.valueOf(buffer).split("\\|");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void closeConnection() throws IOException {
        this.sendMessage("CLOSE_CONNECTION", null);
        System.out.println("Closing connection");
        receiver.setRunning(false);
        inputStreamReader.close();
        outputStreamWriter.close();
        System.out.println(t.isAlive());
    }

}
