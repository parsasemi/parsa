package Graphics;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class Menu2Controller {
    public static final Manager manager = new Manager();
    String username;

    @FXML
    private TextField Password;
    @FXML
    javafx.scene.control.Button loginButton;
    @FXML
    Button signupButton;
    @FXML
    private Button backButton;
    @FXML
    private Button exitButton;

    @FXML
    MenuButton options;


     Stage stage;
     Scene scene;
     Parent root;


    Boolean signup;

    public void switchToMenu3(javafx.event.ActionEvent actionEvent) {

        if (!Password.getText().equals("")) {
            try {
                if(manager.usernameCheck(username)) {
                    if (manager.login(username, Password.getText())) {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("mapp.fxml"));
                        root = loader.load();
                        MapController mapController = loader.getController();
                        mapController.username = username;
                        ArrayList<Button> buttons = new ArrayList<>();
                        buttons.add(mapController.level1);
                        buttons.add(mapController.level2);
                        buttons.add(mapController.level3);
                        buttons.add(mapController.level4);
                        buttons.add(mapController.level5);
                        buttons.add(mapController.level6);
                        buttons.add(mapController.level7);
                        buttons.add(mapController.level8);
                        int max = manager.maxLevelReturner(username);
                        for (Button button: buttons) {
                            if(Integer.parseInt(button.getText())>max){
                                button.setDisable(true);
                            }
                        }


                        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.show();




                    } else {
                        Stage popupWindow = new Stage();
                        popupWindow.initModality(Modality.APPLICATION_MODAL);
                        popupWindow.setTitle("Error");
                        Label label1 = new Label("Wrong password entered!");
                        Button close = new Button("Close");
                        close.setId("close");
                        close.setOnAction(e -> popupWindow.close());
                        VBox vBox = new VBox(10);
                        vBox.getChildren().addAll(label1, close);
                        vBox.setAlignment(Pos.CENTER);
                        Scene scene1 = new Scene(vBox, 300, 250);

                        scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                        popupWindow.setScene(scene1);
                        popupWindow.showAndWait();


                    }
                }else{
                    manager.signUp(username,Password.getText());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("mapp.fxml"));
                    root = loader.load();
                    MapController mapController = loader.getController();
                    mapController.username = username;
                    ArrayList<Button> buttons = new ArrayList<>();
                    buttons.add(mapController.level1);
                    buttons.add(mapController.level2);
                    buttons.add(mapController.level3);
                    buttons.add(mapController.level4);
                    buttons.add(mapController.level5);
                    buttons.add(mapController.level6);
                    buttons.add(mapController.level7);
                    buttons.add(mapController.level8);
                    for (Button button: buttons) {
                        if(Integer.parseInt(button.getText())>manager.levelCheck(username)){
                            button.setDisable(true);
                        }
                    }


                    stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();



                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


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
