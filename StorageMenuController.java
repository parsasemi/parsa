package Graphics;

import LevelDesign.Level;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;

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

public class StorageMenuController implements Initializable {
    Level playerLevel;
    Manager manager = new Manager();
    Parent root;
    ArrayList<Level> levels = new ArrayList<>();

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
    @FXML private Label Capacity;
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
        try {
            readLevel();
            int r=0;
            for (int i=0 ; i<playerLevel.storage.names.size(); i++){
                if (playerLevel.storage.names.get(i).equals("egg"))
                    r++;
            }
            EggLabel.setText("X" + r);
            r=0;

            for (int i=0 ; i<playerLevel.storage.names.size(); i++){
                if (playerLevel.storage.names.get(i).equals("feather"))
                    r++;
            }
            FeatherLabel.setText("X" + r);
            r=0;

            for (int i=0 ; i<playerLevel.storage.names.size(); i++){
                if (playerLevel.storage.names.get(i).equals("milk"))
                    r++;
            }
            MilkLabel.setText("X" + r);
            r=0;

            for (int i=0 ; i<playerLevel.storage.names.size(); i++){
                if (playerLevel.storage.names.get(i).equals("flour"))
                    r++;
            }
            FlourLabel.setText("X" + r);
            r=0;
            for (int i=0 ; i<playerLevel.storage.names.size(); i++){
                if (playerLevel.storage.names.get(i).equals("weave"))
                    r++;
            }
            WeaveLabel.setText("X" + r);
            r=0;
            for (int i=0 ; i<playerLevel.storage.names.size(); i++){
                if (playerLevel.storage.names.get(i).equals("cmilk"))
                    r++;
            }
            CMilkLabel.setText("X" + r);
            r=0;
            for (int i=0 ; i<playerLevel.storage.names.size(); i++){
                if (playerLevel.storage.names.get(i).equals("bread"))
                    r++;
            }
            BreadLabel.setText("X" + r);
            r=0;
            for (int i=0 ; i<playerLevel.storage.names.size(); i++){
                if (playerLevel.storage.names.get(i).equals("cloth"))
                    r++;
            }
            ClothLabel.setText("X" + r);
            r=0;
            for (int i=0 ; i<playerLevel.storage.names.size(); i++){
                if (playerLevel.storage.names.get(i).equals("icecream"))
                    r++;
            }
            IceCreamLabel.setText("X" + r);
            r=0;
            for (int i=0 ; i<playerLevel.storage.names.size();i++){
                if (playerLevel.storage.names.get(i).equals("lion"))
                    r++;

            }
            LionLabel.setText("X"+r);
            r=0;
            for (int i=0 ; i<playerLevel.storage.names.size();i++){
                if (playerLevel.storage.names.get(i).equals("bear"))
                    r++;

            }
            BearLabel.setText("X"+r);
            r=0;
            for (int i=0 ; i<playerLevel.storage.names.size();i++){
                if (playerLevel.storage.names.get(i).equals("tiger"))
                    r++;

            }

            TigerLabel.setText("X"+r);
            r=0;

            Capacity.setText(playerLevel.storage.capacity+"");








        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
