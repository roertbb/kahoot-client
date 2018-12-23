package sample;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketHandler {
    private Socket socket;
    private OutputStreamWriter outputStreamWrite;
    private InputStreamReader inputStreamReader;

    SocketHandler() {
        try {
            socket = new Socket("127.0.0.1",1234);
            outputStreamWrite = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
            inputStreamReader = new InputStreamReader(socket.getInputStream(), "UTF-8");

            // send data
            String s = "lorem ipsum";
            outputStreamWrite.write(s);
            outputStreamWrite.flush();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
