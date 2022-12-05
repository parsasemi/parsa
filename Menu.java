package Graphics;

import com.sun.scenario.effect.impl.prism.PrImage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Menu {
    public static final Manager manager = new Manager();


    @FXML
    private Button EnterButton;
    @FXML
    private TextField Username;


    Stage stage;
    Scene scene;
    Parent root;

    Boolean usernameCheck;
    String username;

    public void switchToMenu2(javafx.event.ActionEvent actionEvent) throws IOException {
        username = Username.getText();
        if (!username.equals("")) {
            usernameCheck = manager.usernameCheck(username);

            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu2.fxml"));
            root = loader.load();
            Menu2Controller menu2Controller = loader.getController();
            menu2Controller.signup = usernameCheck;
            if (usernameCheck) {
                menu2Controller.signupButton.setDisable(true);
            } else {
                menu2Controller.loginButton.setDisable(true);
            }
            scene = new Scene(root,900,500);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            menu2Controller.username = username;

            stage.setScene(scene);
            stage.show();
        }

    }

    public void loading(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("loading.fxml"));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        root = loader.load();
        scene = new Scene(root,900,500);
        stage.setScene(scene);
        stage.show();
    }

    public void Exit() {
        System.exit(0);
    }


}