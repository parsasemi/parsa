public class Factory {
    int buildPrice;
    int duration;
    String ingredient;
    String product;
    int level;
    int ingredientC;
    int upgradePrice;
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
