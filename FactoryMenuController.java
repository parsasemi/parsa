package Graphics;

import LevelDesign.Level;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

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

public class FactoryMenuController implements Initializable {
    Level playerLevel;
    Manager manager = new Manager();
    Parent root;
    ArrayList<Level> levels = new ArrayList<>();

    @FXML private Button MillFactory;
    @FXML private Button WeaveFactory;
    @FXML private Button MilkFactory;
    @FXML private Button BreadFactory;
    @FXML private Button ClothFactory;
    @FXML private Button IceCreamFactory;
    @FXML private Button ChickenFactory;

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
        MillFactory.setOnAction(actionEvent ->{
            try {
                readLevel();
                if (!playerLevel.millFactory.existence){
                    manager.Build(playerLevel,"millfactory");
                    if (playerLevel.millFactory.existence){
                        MillFactory.setText("Mill Factory");
                    }
                }
                else{
                    manager.Work(playerLevel, "millfactory");

                }
                saveLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        MilkFactory.setOnAction(actionEvent ->{
            try {
                readLevel();
                if (!playerLevel.milkFactory.existence){
                    manager.Build(playerLevel,"milkfactory");
                    if (playerLevel.milkFactory.existence){
                        MilkFactory.setText("Milk Factory");
                    }
                }
                else{
                    manager.Work(playerLevel, "milkfactory");

                }
                saveLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        WeaveFactory.setOnAction(actionEvent ->{
            try {
                readLevel();
                if (!playerLevel.weaveFactory.existence){
                    manager.Build(playerLevel,"weavefactory");
                    if (playerLevel.weaveFactory.existence){
                        MillFactory.setText("Weave Factory");
                    }
                }
                else{
                    manager.Work(playerLevel, "weavefactory");

                }
                saveLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        BreadFactory.setOnAction(actionEvent ->{
            try {
                readLevel();
                if (!playerLevel.bakery.existence){
                    manager.Build(playerLevel,"bakery");
                    if (playerLevel.bakery.existence){
                        MillFactory.setText("Bread Factory");
                    }
                }
                else{
                    manager.Work(playerLevel, "bakery");

                }
                saveLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ClothFactory.setOnAction(actionEvent ->{
            try {
                readLevel();
                if (!playerLevel.sewingFactory.existence){
                    manager.Build(playerLevel,"sewingfactory");
                    if (playerLevel.sewingFactory.existence){
                        MillFactory.setText("Sewing Factory");
                    }
                }
                else{
                    manager.Work(playerLevel, "sewingfactory");

                }
                saveLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        IceCreamFactory.setOnAction(actionEvent ->{
            try {
                readLevel();
                if (!playerLevel.millFactory.existence){
                    manager.Build(playerLevel,"icefactory");
                    if (playerLevel.iceFactory.existence){
                        MillFactory.setText("IceCream Factory");
                    }
                }
                else{
                    manager.Work(playerLevel, "icefactory");

                }
                saveLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ChickenFactory.setOnAction(actionEvent ->{
            try {
                readLevel();
                if (!playerLevel.chickenFactory.existence){
                    manager.Build(playerLevel,"chickenfactory");
                    if (playerLevel.chickenFactory.existence){
                        MillFactory.setText("Chicken Factory");
                    }
                }
                else{
                    manager.Work(playerLevel, "chickenfactory");

                }
                saveLevel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
