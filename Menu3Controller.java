package Graphics;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.stage.Stage;

import java.io.IOException;

public class Menu3Controller {

     Stage stage;
     Scene scene;
     Parent root;

    @FXML
    private Button mapButton;

    @FXML
    private MenuButton options;

    public void switchToMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        stage = (Stage) options.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu1.fxml"));
        root = loader.load();
        scene = new Scene(root,900,500);
        stage.setScene(scene);
        stage.show();

    }


    public void Exit() {
        System.exit(0);
    }

}
