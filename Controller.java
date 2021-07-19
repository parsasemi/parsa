package Graphics;

import LevelDesign.Level;
import LevelDesign.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    Level playerLevel;
    Manager manager = new Manager();
    Parent root;
    Stage stage;
    Scene scene;
    ArrayList<Level> levels = new ArrayList<>();
    @FXML
    private ChoiceBox<String> ingredient;

    @FXML
    private Button Storage;
    @FXML
    private Button Bucket;
    private Image image = new Image(getClass().getResourceAsStream("Grass.png"));
    private Image chickenpng = new Image(getClass().getResourceAsStream("Chicken.png"));
    @FXML
    private GridPane Map;

    public void readLevel() throws IOException {

        final int levelNumber = MapController.getLevelNumber();
        final String username = MapController.getUsername();
        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += ("/Graphics/LevelsInfo" + username + ".json");
        File file2 = new File(absolutePath);
        //ArrayList<Level> levels = new ArrayList<>();
        if (file2.exists()) {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(absolutePath));
            levels.clear();
            List<Level> temp = Arrays.asList(gson.fromJson(reader, Level[].class));
            levels.addAll(temp);
            for (Level l : levels) {
                if (l.levelNumber == levelNumber) {
                    playerLevel = l;
                    playerLevel.levelStarted = true;
                    break;
                }
            }
            reader.close();
        } else {

            Level level = new Level();
            level = manager.readBasicLevelInfo(level, 1);
            manager.mapInitialize(level);
            level.playerName = username;
            level.levelStarted = false;
            playerLevel = level;
            levels.add(level);
            saveLevel();

        }
    }

    public void saveLevel() throws IOException {
        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        for (Level l : levels) {
            if (l.levelNumber == 1) {
                absolutePath += "/Graphics/LevelsInfo" + l.playerName + ".json";
                break;
            }
        }
        Gson write = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter(absolutePath);
        write.toJson(levels, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            readLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
       /* if(playerLevel.TaskCheck){
            try {
                playerLevel = manager.levelEnd(playerLevel);
                saveLevel();


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


            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/

        Bucket.setOnAction(actionEvent -> {
              /*  playerLevel = manager.levelEnd(playerLevel);
                saveLevel();*/


            Stage popupWindow = new Stage();
            popupWindow.initModality(Modality.APPLICATION_MODAL);
            popupWindow.setTitle("Error");
            Label label1 = new Label("Level finished!");
            Button close = new Button("Close");
            close.setId("close");
            close.setOnAction(e -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("mapp.fxml"));

                try {
                    root = loader.load();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.close();


                MapController mapController = loader.getController();
                mapController.username = MapController.getUsername();
                ArrayList<Button> buttons = new ArrayList<>();
                buttons.add(mapController.level1);
                buttons.add(mapController.level2);
                buttons.add(mapController.level3);
                buttons.add(mapController.level4);
                buttons.add(mapController.level5);
                buttons.add(mapController.level6);
                buttons.add(mapController.level7);
                buttons.add(mapController.level8);
                int max = 0;
                try {
                    max = manager.maxLevelReturner(MapController.getUsername());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                for (Button button: buttons) {
                    if(Integer.parseInt(button.getText())>max){
                        button.setDisable(true);
                    }
                }
                scene = new Scene(root, 700, 500);
                scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();


            });
            VBox vBox = new VBox(10);
            vBox.getChildren().addAll(label1, close);
            vBox.setAlignment(Pos.CENTER);
            Scene scene1 = new Scene(vBox, 400, 400);
            popupWindow.centerOnScreen();

            scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            popupWindow.setScene(scene1);
            popupWindow.showAndWait();


            // manager.Well(playerLevel);
        });

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Tile tile = new Tile(i, j);
                final int x = i + 1;
                final int y = j + 1;
                tile.grass.setOnMouseClicked(mouseEvent -> {
                    try {
                        readLevel();
                        manager.Plant(x, y, playerLevel);
                        System.out.println(playerLevel.map.map[x - 1][y - 1].grass);
                        if (playerLevel.map.map[x - 1][y - 1].grass >= 1) {
                            tile.grass.setImage(image);
                        }
                        saveLevel();
                        System.out.println(playerLevel.bucket.capacity);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
                try {
                    readLevel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tile.grassLabel.setText("");
                if (playerLevel.map.map[x - 1][y - 1].grass >= 1) {
                    tile.grass.setImage(image);
                    tile.grassLabel.setText(playerLevel.map.map[x - 1][y - 1].grass + "");
                    System.out.println(872);
                }
                tile.cCounter.setText("");
                tile.tCounter.setText("");
                tile.bCounter.setText("");
                int r = 0;
                for (int t = 0; t < playerLevel.chickens.size(); t++) {
                    if (playerLevel.chickens.get(t).x == x && playerLevel.chickens.get(t).y == y) {
                        tile.turkey.setImage(chickenpng);
                        r++;
                    }
                    if (r != 0) {
                        tile.cCounter.setText(r + "");
                        r = 0;
                    }
                }
                tile.grassLabel.setOnMouseClicked(mouseEvent -> {
                    try {
                        readLevel();
                        manager.Plant(y, x, playerLevel);
                        //System.out.println(playerLevel.map.map[x-1][y-1].grass);
                        saveLevel();
                        System.out.println(playerLevel.bucket.capacity);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

                ArrayList<String> s = new ArrayList<>();
                s.add("ingredients");
                try {
                    readLevel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int t = 0; t < playerLevel.ingredients.size(); t++) {
                    if (playerLevel.ingredients.get(t).x == x && playerLevel.ingredients.get(t).y == y) {
                        s.add(playerLevel.ingredients.get(t).name);
                    }
                }
                tile.ingredient.setValue("ingredients");
                tile.ingredient.getItems().addAll(s);
                s.clear();
                tile.ingredient.getItems().addAll(s);
                for (int t = 0; t < playerLevel.chickens.size(); t++) {
                    if (playerLevel.chickens.get(t).x == x && playerLevel.chickens.get(t).y == y) {
                        s.add("Chicken    " + playerLevel.chickens.get(t).health);
                    }
                }
                s.add("Animals");
                tile.animal.setValue("Animals");
                tile.animal.getItems().addAll(s);
                try {
                    saveLevel();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Map.add(tile, j, i);
            }
        }

    }

}

