import java.util.ArrayList;

public class Level {
    int coin;
    int time;
    ArrayList<DomesticAnimals.Chicken> chickens = new ArrayList<>();
    ArrayList<DomesticAnimals.Buffalo> buffalos = new ArrayList<>();
    ArrayList<DomesticAnimals.Turkey> turkies = new ArrayList<>();
    ArrayList<WildAnimals.Bear> bears = new ArrayList<>();
    ArrayList<WildAnimals.Lion> lions = new ArrayList<>();
    ArrayList<WildAnimals.Tiger> tigers = new ArrayList<>();
    ArrayList<SpecialAnimals.Cat> cats = new ArrayList<>();
    ArrayList<SpecialAnimals.Dog> dogs = new ArrayList<>();
    Map map;
    Bucket bucket;
    Factory.WeaveFactory weaveFactory;
    Factory.MillFactory millFactory;
    Factory.MilkFactory milkFactory;
    Factory.Bakery bakery;
    Factory.SewingFactory sewingFactory;
    Factory.IceFactory iceFactory;
    Storage storage;
    MotorCycle motorCycle;
}
