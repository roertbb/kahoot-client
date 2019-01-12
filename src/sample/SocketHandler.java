package sample;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SocketHandler {
    private Socket socket;
    private OutputStreamWriter outputStreamWriter;
    private InputStreamReader inputStreamReader;
    private BufferedReader br;
    public Receiver receiver;
    private Thread t;

    public InputStreamReader getInputStreamReader() { return this.inputStreamReader; }

    SocketHandler() {
        ArrayList<String> server_data = this.readConfig();
        try {
            socket = new Socket(server_data.get(0),Integer.parseInt(server_data.get(1)));
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            inputStreamReader = new InputStreamReader(socket.getInputStream(), "UTF-8");
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
            //outputStreamWriter.write(String.format("%04d", message.length()));
            //outputStreamWriter.flush();
            outputStreamWriter.write(message+"\n");
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] receiveMessage() {
        String [] data = new String[0];

        String line="";
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line.split("\\|");
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

    public void closeConnection() throws IOException {
        this.sendMessage("CLOSE_CONNECTION", null);
        System.out.println("Closing connection");
        receiver.setRunning(false);
        //outputStreamWriter.close();
        //inputStreamReader.close();
        socket.shutdownOutput();
        socket.shutdownInput();
        socket.close();
        t.interrupt();
    }

}
