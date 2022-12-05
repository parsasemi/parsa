package Graphics;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    public static final Manager manager = new Manager();

    public static void main(String[] args) throws IOException {
      /*  Graphics.Manager manager = new Graphics.Manager();
        Graphics.InputProcessor inputProcessor = new Graphics.InputProcessor(manager);
        inputProcessor.run();
*/
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("menu1.fxml"));
        stage.setTitle("Warm Friendzy");
        Scene scene = new Scene(root,900,500);
        String cssAddress = this.getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(cssAddress);
        stage.setScene(scene);
       // stage.setFullScreen(true);
        stage.centerOnScreen();
        stage.show();

    }
}
