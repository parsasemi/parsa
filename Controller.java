package Graphics;

import LevelDesign.Level;
import LevelDesign.Map;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.io.File;
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


    @FXML private Button Storage;
    @FXML private Button Bucket;
    private Image image = new Image(getClass().getResourceAsStream("Grass.png"));
    @FXML
    private GridPane Map;

    public void readLevel() throws IOException {

      /*  FXMLLoader loader = new FXMLLoader(getClass().getResource("mapp.fxml"));
        root = loader.load();
        MapController mapController = loader.getController();*/
        final int levelNumber = MapController.getLevelNumber();
        File file1 = new File("");
        String absolutePath = file1.getAbsolutePath();
        absolutePath += "/Graphics/LevelsInfoparsa.json";
        File file2 = new File(absolutePath);
        ArrayList<Level> levels = new ArrayList<>();
        if (file2.exists()) {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(absolutePath));
            levels.clear();
            List<Level> temp = Arrays.asList(gson.fromJson(reader, Level[].class));
            levels.addAll(temp);
            for (Level l:levels) {
                if(l.levelNumber==levelNumber){
                    playerLevel = l;
                    System.out.println("levelNumber "+levelNumber);
                    break;
                }
            }
            reader.close();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Bucket.setOnAction(actionEvent -> {
            try {
                System.out.println("fuck you"+ MapController.getPlayerLevel().bucket.capacity);
                readLevel();
                System.out.println(playerLevel.bucket.capacity);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // manager.Well(playerLevel);
            System.out.println(playerLevel.bucket.capacity);
        });

        for (int i=0; i <6; i++){
            for (int j=0; j<6; j++){
                Tile tile = new Tile(i,j);
                tile.grass.setOnMouseClicked(mouseEvent -> tile.grass.setImage(image));
                tile.grassLabel.setOnMouseClicked(mouseEvent -> {
                    tile.grass.setImage(image);
                });
                Map.add(tile, j,i);
            }
        }

    }

}

