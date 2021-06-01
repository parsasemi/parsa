

import Animals.DomesticAnimals;
import Animals.SpecialAnimals;
import LevelDesign.Level;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import Animals.WildAnimals;

public class Main {

    public static void main(String[] args) throws IOException {


        Manager manager = new Manager();
        InputProcessor inputProcessor = new InputProcessor(manager);
        inputProcessor.run();
       /* DomesticAnimals domesticAnimal = new DomesticAnimals();
        Gson save = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter("Level2.json");
        save.toJson(domesticAnimal, fileWriter);
        fileWriter.flush();x
        fileWriter.close();*/

    }
}
