package scrumble;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class that starts up the Scrumble app.
 */
public class Scrumble extends Application {

    private static final int SCENE_WIDTH = 900;
    private static final int SCENE_HEIGHT = 650;

    @Override
    public void start(Stage stage) {
        showScrumbleGui(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showScrumbleGui(Stage stage) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/scrumble_gui.fxml"));
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Scrumble.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        stage.setTitle("Welcome to Scrumble!");
        stage.setScene(scene);
        stage.show();
    }
}
