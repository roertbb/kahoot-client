package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ScreenManager {

    public void setScreen(String screen, ActionEvent actionEvent) {
        Stage current = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        setScene(screen,current);
    }

    public void setScreen(String screen, Stage current) {
        setScene(screen,current);
    }

    private void setScene(String screen, Stage current) {
        Parent newScene = null;
        try {
            newScene = FXMLLoader.load(getClass().getResource("views/"+screen+".fxml"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        if (newScene != null) {
            Parent finalNewScene = newScene;
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    current.setScene(new Scene(finalNewScene));
                }
            });
        }
    }
}
