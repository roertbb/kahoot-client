package sample;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketHandler {
    private Socket socket;
    private OutputStreamWriter outputStreamWrite;
    private InputStreamReader inputStreamReader;

    SocketHandler() {
        try {
            socket = new Socket("127.0.0.1",1234);
            outputStreamWrite = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            inputStreamReader = new InputStreamReader(socket.getInputStream(), "UTF-8");
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
            default:
                System.out.println("Unrecognized message");
                message = "99|Unrecognized message";
                break;
        }
        try {
            outputStreamWrite.write(message);
            outputStreamWrite.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] receiveMessage() {
        char buffer[] = new char[1024];
        String [] data = new String[0];
        try {
            inputStreamReader.read(buffer);
            data = String.valueOf(buffer).split("|");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void closeConnection() throws IOException {
        this.sendMessage("CLOSE_CONNECTION", null);
        System.out.println("Closing connection");
        inputStreamReader.close();
        outputStreamWrite.close();
        socket.close();
    }

}
