package Graphics;

import LevelDesign.Level;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

public class Controller implements Initializable
{

    Level playerLevel;
    Manager manager = new Manager();
    Parent root;
    ArrayList<Level> levels = new ArrayList<>();
    @FXML private ChoiceBox<String> ingredient;
    Stage stage;
    Scene scene;
    @FXML private ImageView BucketImage;
    @FXML private Button Storage;
    @FXML private Button Truck;
    @FXML private Button Menu;
    @FXML private Button Bucket;
    @FXML private Button Turn;
    @FXML private Button pause;
    @FXML private GridPane StorageView;

    @FXML private Label EggLabel;
    @FXML private Label FeatherLabel;
    @FXML private Label MilkLabel;
    @FXML private Label FlourLabel;
    @FXML private Label WeaveLabel;
    @FXML private Label CMilkLabel;
    @FXML private Label BreadLabel;
    @FXML private Label ClothLabel;
    @FXML private Label IceCreamLabel;
    @FXML private Label LionLabel;
    @FXML private Label BearLabel;
    @FXML private Label TigerLabel;



    private Image fillBucketpng = new Image(getClass().getResourceAsStream("FillBucket.png"));
    private Image emptyBucketpng = new Image(getClass().getResourceAsStream("EmptyBucket.png"));
    private Image image = new Image(getClass().getResourceAsStream("Grass.png"));
    private Image chickenpng = new Image(getClass().getResourceAsStream("Chicken.png"));
    private Image turkeypng = new Image(getClass().getResourceAsStream("Turkey.png"));
    private Image buffalopng = new Image(getClass().getResourceAsStream("Buffalo.png"));
    private Image lionpng = new Image(getClass().getResourceAsStream("Lion.png"));
    private Image bearpng = new Image(getClass().getResourceAsStream("Bear.png"));
    private Image tigerpng = new Image(getClass().getResourceAsStream("Tiger.png"));
    @FXML
    private GridPane Map;

    public void readLevel() throws IOException {

        final int levelNumber = MapController.getLevelNumber();
        final String username = MapController.getUsername();
        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += ("/Graphics/LevelsInfo"+username+".json");
        File file2 = new File(absolutePath);
        //ArrayList<Level> levels = new ArrayList<>();
        if (file2.exists()) {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(absolutePath));
            levels.clear();
            List<Level> temp = Arrays.asList(gson.fromJson(reader, Level[].class));
            levels.addAll(temp);
            for (Level l:levels) {
                if(l.levelNumber==levelNumber){
                    playerLevel = l;
                    break;
                }
            }
            if (!playerLevel.levelStarted){
                manager.mapInitialize(playerLevel);
            }
            reader.close();
        }
        else{
            Level level = new Level();
            level = manager.readBasicLevelInfo(level,1);
            manager.mapInitialize(level);
            level.playerName  = username;
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
                System.out.println(absolutePath);
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










        //CHECKING IF THE LEVEL IS FINISHED
        try {
            readLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (playerLevel.task.taskCheck) {
            Stage popupWindow = new Stage();
            popupWindow.initModality(Modality.APPLICATION_MODAL);
            popupWindow.setTitle("Level finished!");

            String string = "";

            if (playerLevel.task.taskCounter == playerLevel.task.taskObj) {
                try {
                    playerLevel = manager.levelEnd(playerLevel,levels);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!playerLevel.task.timeCheck1 && !playerLevel.task.timeCheck2 && !playerLevel.task.timeCheck3) {
                    string += ("Level finished! Level " + (playerLevel.levelNumber + 1) + " unlocked!\n" + "You recieve a golden Medal for finishing the level before the first time objective!");
                } else if (playerLevel.task.timeCheck1 && !playerLevel.task.timeCheck2 && !playerLevel.task.timeCheck3) {

                    string += ("Level finished! Level " + (playerLevel.levelNumber + 1) + " unlocked!\n" + "You recieve a silver Medal for finishing the level before the first time objective!");
                } else if (playerLevel.task.timeCheck1 && playerLevel.task.timeCheck2 && !playerLevel.task.timeCheck3) {

                    string += ("Level finished! Level " + (playerLevel.levelNumber + 1) + " unlocked!\n" + "You recieve a bronze Medal for finishing the level before the first time objective!");

                } else if (playerLevel.task.timeCheck1 && playerLevel.task.timeCheck2 && playerLevel.task.timeCheck3) {

                    string += ("Level finished! Level " + (playerLevel.levelNumber + 1) + " unlocked!\n" + "You recieve a NOTHING!!! for finishing the level before the first time objective!");

                }
            }


            Label label1 = new Label(string);
            label1.setText(string);
            Button close = new Button("Close");
            close.setId("close");
            close.setOnAction(e -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("mapp.fxml"));
                try {
                    root = loader.load();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
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
                    max = manager.levelCheck(MapController.getUsername());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                for (Button button : buttons) {
                    if (Integer.parseInt(button.getText()) > max) {
                        button.setDisable(true);
                    }
                }
                scene = new Scene(root, 700, 500);
                scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
                try {
                    saveLevel();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            });
            VBox vBox = new VBox(10);
            vBox.getChildren().addAll(label1, close);
            vBox.setAlignment(Pos.CENTER);
            Scene scene1 = new Scene(vBox, 500, 500);
            popupWindow.centerOnScreen();

            scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            popupWindow.setScene(scene1);
            popupWindow.showAndWait();

        }
        try {
            saveLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }





        Menu.setOnAction(actionEvent -> {

        });



        pause.setOnAction(actionEvent -> {
            try {
                readLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage popupWindow = new Stage();
            popupWindow.initModality(Modality.APPLICATION_MODAL);
            popupWindow.setTitle("Level finished");

            Button close = new Button("Resume");
            close.setOnAction(e -> {
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.close();
            });

            Button map = new Button("Map");
            map.setOnAction(e->{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("mapp.fxml"));
                try {
                    root = loader.load();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
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
                    max = manager.levelCheck(MapController.getUsername());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                for (Button button : buttons) {
                    if (Integer.parseInt(button.getText()) > max) {
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
            vBox.getChildren().addAll(close,map);
            vBox.setAlignment(Pos.CENTER);
            Scene scene1 = new Scene(vBox, 400, 400);
            popupWindow.centerOnScreen();

            scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            popupWindow.setScene(scene1);
            popupWindow.showAndWait();
            try {
                saveLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        Storage.setOnAction(actionEvent -> {
            Stage popupWindow = new Stage();
            try {
                StorageView = FXMLLoader.load(getClass().getResource("StorageMenu.fxml"));
                Scene sCene = new Scene(StorageView);
                popupWindow.centerOnScreen();
                popupWindow.setScene(sCene);
                popupWindow.initModality(Modality.APPLICATION_MODAL);
                popupWindow.setTitle("Storage");
                readLevel();
                popupWindow.show();
                saveLevel();

            } catch (IOException e) {
                e.printStackTrace();
            }
//            Label label1 = new Label("Level finished!");
//            Button close = new Button("Close");
//            close.setOnAction(e->{
//                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//                stage.close();
//            });
//            close.setId("close");
//            VBox vBox = new VBox(10);
//            vBox.getChildren().addAll(label1, close);
//            vBox.setAlignment(Pos.CENTER);
//            Scene scene1 = new Scene(vBox, 400, 400);
//            popupWindow.centerOnScreen();
//
//            scene1.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//            popupWindow.setScene(scene1);
//            popupWindow.showAndWait();

        });
        try {
            readLevel();
            if (playerLevel.bucket.capacity>=5) {
                BucketImage.setImage(fillBucketpng);
            }
            else {
                BucketImage.setImage(emptyBucketpng);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bucket.setOnAction(actionEvent -> {
            try {
                readLevel();
                playerLevel.bucket.duration=0;
                saveLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Tile[][] tiles  = new Tile[6][6];
        for (int i=0; i <6; i++){
            for (int j=0; j<6; j++){
                Tile tile = new Tile(i,j);
                tiles[i][j] = tile;
                final int x = i+1;
                final int y = j+1;
                //////////grass plant
                tile.grass.setOnMouseClicked(mouseEvent -> {
                    try {
                        readLevel();
                        if (playerLevel.bucket.capacity<=1){
                            BucketImage.setImage(emptyBucketpng);
                        }
                        manager.Plant(x,y,playerLevel);
                        System.out.println(playerLevel.map.map[x-1][y-1].grass);
                        if (playerLevel.map.map[x-1][y-1].grass>=1){
                            tile.grassLabel.setText(playerLevel.map.map[x-1][y-1].grass+"");
                            tile.grass.setImage(image);
                        }
                        saveLevel();
                        System.out.println(playerLevel.bucket.capacity);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
                tile.grassLabel.setOnMouseClicked(mouseEvent -> {
                    try {
                        readLevel();
                        manager.Plant(x,y,playerLevel);
                        if (playerLevel.map.map[x-1][y-1].grass>=1){
                            tile.grassLabel.setText(playerLevel.map.map[x-1][y-1].grass+"");
                            tile.grass.setImage(image);
                        }
                        saveLevel();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });
                try {
                    readLevel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (playerLevel.map.map[x-1][y-1].grass==0){
                    tile.grass.setImage(null);
                    tile.grassLabel.setText("");
                }

                ///////Domestic Animals
                tile.cCounter.setText("");
                tile.tCounter.setText("");
                tile.bCounter.setText("");
                int r=0;
                for (int t=0 ; t<playerLevel.chickens.size(); t++) {
                    if (playerLevel.chickens.get(t).x == x && playerLevel.chickens.get(t).y == y) {
                        tile.turkey.setImage(chickenpng);
                        r++;
                    }
                }
                if (r!=0) {
                        tile.cCounter.setText(r + "");
                        r=0;
                }

                r=0;
                for (int t=0 ; t<playerLevel.turkies.size(); t++) {
                    if (playerLevel.turkies.get(t).x == x && playerLevel.turkies.get(t).y == y) {
                        tile.chicken.setImage(turkeypng);
                        r++;
                    }
                }
                if (r!=0) {
                        tile.tCounter.setText(r + "");
                        r=0;
                }

                r=0;
                for (int t=0 ; t<playerLevel.buffalos.size(); t++) {
                    if (playerLevel.buffalos.get(t).x == x && playerLevel.buffalos.get(t).y == y) {
                        tile.buffalo.setImage(buffalopng);
                        r++;
                    }
                }
                if (r!=0) {
                        tile.bCounter.setText(r + "");
                        r=0;
                }

                //////Wild Animals

                tile.cCounter.setText("");
                tile.tCounter.setText("");
                tile.bCounter.setText("");
                for (int t=0 ; t<playerLevel.lions.size(); t++) {
                    if (playerLevel.lions.get(t).x == x && playerLevel.lions.get(t).y == y) {
                        tile.turkey.setImage(lionpng);
                        r++;
                    }
                }
                if (r!=0) {
                    tile.cCounter.setText(r + "");
                    r=0;
                }

                r=0;
                for (int t=0 ; t<playerLevel.bears.size(); t++) {
                    if (playerLevel.bears.get(t).x == x && playerLevel.bears.get(t).y == y) {
                        tile.chicken.setImage(bearpng);
                        r++;
                    }
                }
                if (r!=0) {
                    tile.tCounter.setText(r + "");
                    r=0;
                }

                r=0;
                for (int t=0 ; t<playerLevel.tigers.size(); t++) {
                    if (playerLevel.tigers.get(t).x == x && playerLevel.tigers.get(t).y == y) {
                        tile.buffalo.setImage(tigerpng);
                        r++;
                    }
                }
                if (r!=0) {
                    tile.bCounter.setText(r + "");
                    r=0;
                }

                ////////





                ////////// ChoiceBox
                ArrayList<String> s = new ArrayList<>();
                try {
                    readLevel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int t=0 ; t<playerLevel.ingredients.size();t++){
                            if (playerLevel.ingredients.get(t).x==x && playerLevel.ingredients.get(t).y==y){
                                s.add(playerLevel.ingredients.get(t).name + "   " + playerLevel.ingredients.get(t).expire);
                            }
                        }
                //tile.ingredient.setValue("ingredients");
                tile.ingredient.getItems().addAll(s);
                s.clear();
                tile.ingredient.getItems().addAll(s);
                if (!tile.ingredient.getItems().contains("ingredient")){
                    tile.ingredient.getItems().add("ingredient");
                }
                for (int t=0 ; t<playerLevel.chickens.size();t++){
                    if (playerLevel.chickens.get(t).x==x && playerLevel.chickens.get(t).y==y){
                        s.add("Chicken    "+ playerLevel.chickens.get(t).health);
                    }
                }

                //tile.animal.setValue("Animals");
                tile.animal.getItems().addAll(s);
                try {
                    saveLevel();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //////

                tile.ingredient.valueProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                        if (t1 != null) {

                            String[] e = t1.split("\\s+");
                            try {
                                readLevel();
                                for (int i = 0; i < playerLevel.ingredients.size(); i++) {
                                    if (e[0].equals(playerLevel.ingredients.get(i).name) && e[1].equals(playerLevel.ingredients.get(i).expire+"")) {
                                        if (playerLevel.ingredients.get(i).x-1 == tile.x && playerLevel.ingredients.get(i).y-1 == tile.y) {
                                            System.out.println(t1);
                                            playerLevel.storage.names.add(playerLevel.ingredients.get(i).name);
                                            playerLevel.ingredients.remove(playerLevel.ingredients.get(i));
                                            tile.ingredient.getItems().remove(t1);
                                            break;
                                        }
                                    }

                                }
                                saveLevel();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    }
                });


                Map.add(tile, j,i);
            }
        }
        Turn.setOnAction(actionEvent -> {
            try {
                readLevel();
                manager.turn(playerLevel, 1);
                ////Animals & ChoiceBox
                for (int i=0 ; i<6;i++){
                    for (int j=0 ; j<6 ;j++){
                        final int x = i+1;
                        final int y = j+1;
                        Tile tile = tiles[i][j];
                        //grass
                        if (playerLevel.map.map[x-1][y-1].grass==0){
                            tile.grass.setImage(null);
                            tile.grassLabel.setText("");
                        }
                        else{
                            tile.grass.setImage(image);
                            tile.grassLabel.setText(playerLevel.map.map[x-1][y-1].grass+"");
                        }

                        ///Animal
                        tile.cCounter.setText("");
                        tile.tCounter.setText("");
                        tile.bCounter.setText("");

                        int r=0;
                        int a = 0;
                        for (int t=0 ; t<playerLevel.chickens.size(); t++) {
                            if (playerLevel.chickens.get(t).x == x && playerLevel.chickens.get(t).y == y) {
                                tile.turkey.setImage(chickenpng);
                                r++;
                                a=1;
                            }

                        }
                        if (a==0){
                            tile.turkey.setImage(null);
                        }
                        a=0;
                        if (r!=0) {
                            tile.cCounter.setText(r + "");
                            r=0;
                        }

                        r=0;
                        for (int t=0 ; t<playerLevel.turkies.size(); t++) {
                            if (playerLevel.turkies.get(t).x == x && playerLevel.turkies.get(t).y == y) {
                                tile.chicken.setImage(turkeypng);
                                r++;
                                a=1;
                            }
                        }
                        if (a==0){
                            tile.chicken.setImage(null);
                        }
                        a=0;
                        if (r!=0) {
                            tile.tCounter.setText(r + "");
                            r=0;
                        }

                        r=0;
                        for (int t=0 ; t<playerLevel.buffalos.size(); t++) {
                            if (playerLevel.buffalos.get(t).x == x && playerLevel.buffalos.get(t).y == y) {
                                tile.buffalo.setImage(buffalopng);
                                r++;
                                a=1;
                            }
                        }
                        if (r!=0) {
                            tile.bCounter.setText(r + "");
                            r=0;
                        }
                        if (a==0){
                            tile.buffalo.setImage(null);
                        }
                        a=0;
                        for (int t=0 ; t<playerLevel.lions.size(); t++) {
                            if (playerLevel.lions.get(t).x == x && playerLevel.lions.get(t).y == y) {
                                tile.turkey.setImage(lionpng);
                                r++;
                            }
                        }
                        if (r!=0) {
                            tile.cCounter.setText(r + "");
                            r=0;
                        }

                        r=0;
                        for (int t=0 ; t<playerLevel.bears.size(); t++) {
                            if (playerLevel.bears.get(t).x == x && playerLevel.bears.get(t).y == y) {
                                tile.chicken.setImage(bearpng);
                                r++;
                            }
                        }
                        if (r!=0) {
                            tile.tCounter.setText(r + "");
                            r=0;
                        }

                        r=0;
                        for (int t=0 ; t<playerLevel.tigers.size(); t++) {
                            if (playerLevel.tigers.get(t).x == x && playerLevel.tigers.get(t).y == y) {
                                tile.buffalo.setImage(tigerpng);
                                r++;
                            }
                        }
                        if (r!=0) {
                            tile.bCounter.setText(r + "");
                            r=0;
                        }

                        //ChoiceBox


                        ArrayList<String> s = new ArrayList<>();
                        s.add("ingredient");
                        for (int t=0 ; t<tile.ingredient.getItems().size(); t++){
                            tile.ingredient.getItems().remove(t);
                        }
                        for (int t=0 ; t<playerLevel.ingredients.size();t++){
                            if (playerLevel.ingredients.get(t).x==x && playerLevel.ingredients.get(t).y==y){
                                s.add(playerLevel.ingredients.get(t).name + "   " + playerLevel.ingredients.get(t).expire);

                            }
                        }
                        tile.ingredient.getItems().addAll(s);
                        //tile.ingredient.setValue("ingredients");
                        s.clear();

                        for (int t=0 ; t<tile.animal.getItems().size(); t++){
                            tile.animal.getItems().remove(t);
                        }
                        for (int t=0 ; t<playerLevel.chickens.size();t++){
                            if (playerLevel.chickens.get(t).x==x && playerLevel.chickens.get(t).y==y){
                                s.add("Chicken    "+ playerLevel.chickens.get(t).health);
                            }
                        }
                        tile.animal.getItems().addAll(s);

                        //tile.animal.setValue("Animals");
                        try {
                            saveLevel();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                }

                if (!playerLevel.bucket.full){
                    BucketImage.setImage(emptyBucketpng);
                }
                else{
                    BucketImage.setImage(fillBucketpng);
                }
                /////


                saveLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}

