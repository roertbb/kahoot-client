package sample;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class SocketHandler {
    private Socket socket;
    private OutputStreamWriter outputStreamWriter;
    private InputStreamReader inputStreamReader;
    private BufferedReader br;
    public Receiver receiver;
    private Thread t;

    SocketHandler() {
        ArrayList<String> server_data = this.readConfig();
        try {
            socket = new Socket(server_data.get(0), Integer.parseInt(server_data.get(1)));
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            inputStreamReader = new InputStreamReader(socket.getInputStream(), "UTF-8");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            receiver = new Receiver();
            this.t = new Thread(receiver);
            this.t.setDaemon(true);
            this.t.start();
        } catch (ConnectException e) {
            System.err.println("Connection Exception - Couldn't connect to server");
            //e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Streams Exception");
            //e.printStackTrace();
        }
    }

    public void sendMessage(String messageType, String data) {

        String message = null;

        switch(messageType) {
            case "CLOSE_CONNECTION":
                message = "01|";
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
            case "SEND_ANSWER":
                message = "06|" + data;
                break;
            case "REQUEST_ROOM_MEMBERS":
                message = "11|";
                break;
            case "CHECK_IF_ALREADY_STARTED":
                message = "13|";
                break;
            default:
                System.out.println("Unrecognized message");
                message = "99|Unrecognized message";
                break;
        }
        try {
            if (outputStreamWriter != null) {
                outputStreamWriter.write(message + "\n");
                outputStreamWriter.flush();
            }
        } catch (IOException e) {
            System.err.print("Writing Message Exception: ");
            //e.printStackTrace();
        }
    }

    public String[] receiveMessage() throws IOException {
        String [] data;
        String line;

        line = br.readLine();
        if (line == null)
            throw new IOException();

        data = line.split("\\|");

        return data;
    }

    private ArrayList<String> readConfig() {
        BufferedReader in = null;
        ArrayList<String> data = null;
        try {
            in = new BufferedReader(new FileReader(".env"));
            data = new ArrayList<>();
            String str;
            while ((str = in.readLine()) != null)
                data.add(str);
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find config file");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void closeConnection() {
        this.sendMessage("CLOSE_CONNECTION", null);
    }
}
