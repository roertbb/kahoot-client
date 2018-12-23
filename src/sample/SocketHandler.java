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

    public void sendMessage(String messageType, Object data) throws IOException {

        String message = null;

        switch(messageType) {
            case "CLOSE_CONNECTION":
                message = "Disconnected";
                break;
            case "MESSAGE":
                message = (String) data;
                break;
            default:
                System.out.println("Unrecognized message");
                message = "Unrecognized message";
                break;
        }
        outputStreamWrite.write(message);
        outputStreamWrite.flush();
    }

    public void closeConnection() throws IOException {
        this.sendMessage("CLOSE_CONNECTION", null);
        System.out.println("Closing connection");
        inputStreamReader.close();
        outputStreamWrite.close();
        socket.close();
    }

}
