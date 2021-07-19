package Graphics;

import LevelDesign.Level;
import LevelDesign.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

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

    @FXML private Button Storage;
    @FXML private Button Bucket;
    @FXML private Button Turn;
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

        Bucket.setOnAction(actionEvent -> {
            try {

                readLevel();
                System.out.println(playerLevel.bucket.capacity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // manager.Well(playerLevel);
            System.out.println(playerLevel.bucket.capacity);
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
                s.add("ingredients");
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
                tile.ingredient.setValue("ingredients");
                tile.ingredient.getItems().addAll(s);
                s.clear();
                tile.ingredient.getItems().addAll(s);
                for (int t=0 ; t<playerLevel.chickens.size();t++){
                    if (playerLevel.chickens.get(t).x==x && playerLevel.chickens.get(t).y==y){
                        s.add("Chicken    "+ playerLevel.chickens.get(t).health);
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


                //////


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
                        s.add("ingredients");
                        tile.ingredient.getItems().removeAll();
                        for (int t=0 ; t<playerLevel.ingredients.size();t++){
                            if (playerLevel.ingredients.get(t).x==x && playerLevel.ingredients.get(t).y==y){
                                s.add(playerLevel.ingredients.get(t).name + "   " + playerLevel.ingredients.get(t).expire);
                            }
                        }
                        tile.ingredient.getItems().addAll(s);
                        tile.ingredient.setValue("ingredients");
                        s.clear();

                        s.add("Animals");
                        tile.animal.getItems().removeAll();
                        for (int t=0 ; t<playerLevel.chickens.size();t++){
                            if (playerLevel.chickens.get(t).x==x && playerLevel.chickens.get(t).y==y){
                                s.add("Chicken    "+ playerLevel.chickens.get(t).health);
                                System.out.println(s.get(1));
                            }
                        }
                        tile.animal.getItems().addAll(s);
                        tile.animal.setValue("Animals");
                        try {
                            saveLevel();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }

                }
                /////


                saveLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}

