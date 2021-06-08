package LevelDesign;

public class Factory {
    public int buildPrice;
    public int maxDuration;
    public String ingredient;
    public Ingredient product;
    public int level;
    public int upgradePrice;
    public boolean ingredientExistence = false;
    public int productTime=0;
    public boolean existence  =false;
    public int duration  = -1;
    public Factory() {
    }


    public static class WeaveFactory extends Factory {
        public WeaveFactory() {
            this.buildPrice = 250;
            this.maxDuration = 5;
            this.ingredient = "feather";
            this.product = new Ingredient.Feather(1,1);
            this.upgradePrice = 0;
        }
    }


    public static class MillFactory extends Factory{
        public MillFactory() {
            this.buildPrice = 150;
            this.maxDuration = 4;
            this.ingredient = "flour";
            this.product = new Ingredient.Flour(1,1);
            this.upgradePrice = 0;
        }
    }

    public static class MilkFactory extends Factory {
        public MilkFactory() {
            this.buildPrice = 400;
            this.maxDuration = 6;
            this.ingredient = "milk";
            this.product = new Ingredient.Milk(1,1);
            this.upgradePrice = 0;
        }
    }

    public static class Bakery extends Factory{
        public Bakery() {
            this.buildPrice = 250;
            this.maxDuration = 5;
            this.ingredient = "bread";
            this.product = new Ingredient.Bread(1,1);
            this.upgradePrice = 0;
        }
    }

    public static class SewingFactory extends Factory{
        public SewingFactory() {
            this.buildPrice = 400;
            this.maxDuration = 6;
            this.ingredient = "cloth";
            this.product = new Ingredient.Cloth(1,1);
            this.upgradePrice = 0;
        }
    }

    public static class IceFactory extends Factory{
        public IceFactory() {
            this.buildPrice = 550;
            this.maxDuration = 7;
            this.ingredient = "icecream";
            this.product = new Ingredient.IceCream(1,1);
            this.upgradePrice = 0;
        }
    }


}
