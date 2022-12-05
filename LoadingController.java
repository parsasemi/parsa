package Graphics;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {
    @FXML
    ImageView image;

    Stage stage;
    Scene scene;
    Parent root;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*TranslateTransition translate = new TranslateTransition();
        translate.setNode(image);
        translate.setDuration(Duration.millis(1000));
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setByX(250);
        translate.setAutoReverse(true);
        translate.play();*/
       // Image i = new Image(new File("progress.gif").toURI().toString());
     //   image.setImage(i);

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(actionEvent ->{
            System.out.println("hi");
        });
        pause.play();


    }

    public void switchToMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu1.fxml"));
        root = loader.load();
        scene = new Scene(root,900,500);
        stage.setScene(scene);
        stage.show();
    }
}
