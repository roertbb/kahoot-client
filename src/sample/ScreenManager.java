package sample;

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
        Parent newScene = null;
        try {
            newScene = FXMLLoader.load(getClass().getResource("views/"+screen+".fxml"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        if (newScene != null)
            current.setScene(new Scene(newScene));
    }
}
