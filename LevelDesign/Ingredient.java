package LevelDesign;

public class Ingredient {
    public String name;
    public int price;
    public int size;
    public int x;
    public int y;
    public int expire;

    public Ingredient() {
    }

    public Ingredient(String name, int price, int size, int x, int y, int expire) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.expire = expire;
    }

    public static class Egg extends Ingredient{
        public Egg( int x, int y) {
            this.name = "egg";
            this.price = 15;
            this.size = 1;
            this.expire = 4;
        }
    }

    public static class Feather extends Ingredient{
        public Feather( int x, int y) {
            this.name = "feather";
            this.price = 20;
            this.size = 1;
            this.expire = 4;
        }
    }

    public static class Milk extends Ingredient{
        public Milk( int x, int y) {
            this.name = "milk";
            this.price = 25;
            this.size = 1;
            this.expire = 4;
        }
    }

    public static class Flour extends Ingredient{
        public Flour( int x, int y) {
            this.name = "flour";
            this.price = 40;
            this.size = 2;
            this.expire = 5;
        }
    }

    public static class Weave extends Ingredient{
        public Weave( int x, int y) {
            this.name = "weave";
            this.price = 50;
            this.size = 2;
            this.expire = 5;
        }
    }

    public static class CMilk extends Ingredient{
        public CMilk( int x, int y) {
            this.name = "cmilk";
            this.price = 60;
            this.size = 2;
            this.expire = 5;
        }
    }

    public static class Bread extends Ingredient{
        public Bread( int x, int y) {
            this.name = "bread";
            this.price = 80;
            this.size = 4;
            this.expire = 6;
        }
    }

    public static class Cloth extends Ingredient{
        public Cloth( int x, int y) {
            this.name = "cloth";
            this.price = 100;
            this.size = 4;
            this.expire = 6;
        }
    }

    public static class IceCream extends Ingredient{
        public IceCream( int x, int y) {
            this.name = "icecream";
            this.price = 120;
            this.size = 4;
            this.expire = 6;
        }
    }
}
