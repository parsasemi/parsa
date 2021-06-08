package LevelDesign;

import Animals.DomesticAnimals;
import Animals.SpecialAnimals;
import Animals.WildAnimals;

import java.util.ArrayList;

public class Level {
    public String playerName;
    public int levelNumber;
    public int coin;
    public int time;
    public ArrayList<Ingredient> ingredients = new ArrayList<>();
    public ArrayList<DomesticAnimals.Chicken> chickens = new ArrayList<>();
    public ArrayList<DomesticAnimals.Buffalo> buffalos = new ArrayList<>();
    public ArrayList<DomesticAnimals.Turkey> turkies = new ArrayList<>();
    public ArrayList<WildAnimals.Bear> bears = new ArrayList<>();
    public ArrayList<WildAnimals.Lion> lions = new ArrayList<>();
    public ArrayList<WildAnimals.Tiger> tigers = new ArrayList<>();
    public ArrayList<SpecialAnimals.Cat> cats = new ArrayList<>();
    public ArrayList<SpecialAnimals.Dog> dogs = new ArrayList<>();
    public Map map;
    public Bucket bucket;
    public Factory.WeaveFactory weaveFactory;
    public Factory.MillFactory millFactory;
    public Factory.MilkFactory milkFactory;
    public Factory.Bakery bakery;
    public Factory.SewingFactory sewingFactory;
    public Factory.IceFactory iceFactory;
    public Storage storage;
    public MotorCycle motorCycle;

}
