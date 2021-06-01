package LevelDesign;

public class Factory {
    public int buildPrice;
    public int duration;
    public int maxDuration;
    public String ingredient;
    public String product;
    public int level;
    public int ingredientC;
    public int upgradePrice;
    public boolean existence  =false;

    public Factory() {
    }


    public static class WeaveFactory extends Factory {
    }

    public static class MillFactory extends Factory{
    }

    public static class MilkFactory extends Factory{
    }

    public static class Bakery extends Factory{
    }

    public static class SewingFactory extends Factory{
    }

    public static class IceFactory extends Factory{
    }


}
