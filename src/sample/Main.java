package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static SocketHandler socketHandler;

    public static SocketHandler getSocketHandler() {
        return socketHandler;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        socketHandler = new SocketHandler();

        Parent root = FXMLLoader.load(getClass().getResource("views/createKahoot.fxml"));
        primaryStage.setTitle("Kahoot");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            try {
                socketHandler.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
