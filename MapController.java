package Graphics;

import LevelDesign.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MapController {

    Stage stage;
    Scene scene;
    Parent root;

    @FXML
    Button level1;
    @FXML
    Button level2;
    @FXML
    Button level3;
    @FXML
    Button level4;
    @FXML
    Button level5;
    @FXML
    Button level6;
    @FXML
    Button level7;
    @FXML
    Button level8;



    public static final Manager manager = new Manager();
    static int levelNumber;
    static String username;
    static Level playerLevel;

    public void switchToGameplay(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        levelNumber = Integer.parseInt(button.getText());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainGame.fxml"));
        root = loader.load();
        Controller controller = loader.getController();
        controller.playerLevel = manager.levelReturner(levelNumber);
        playerLevel = manager.levelReturner(levelNumber);



        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root,900,500);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

    }

    public static int getLevelNumber(){
        return levelNumber;
    }
    public static Level getPlayerLevel(){
        return playerLevel;
    }
    public static String getUsername(){
        return username;
    }
    }

