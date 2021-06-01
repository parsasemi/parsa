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
        public Egg(String name, int price, int size, int x, int y, int expire) {
            super("egg", 15, 1, x, y, 4);
        }
    }

    public static class Feather extends Ingredient{
        public Feather(String name, int price, int size, int x, int y, int expire) {
            super("feather", 20, 1, x, y, 4);
        }
    }

    public static class Milk extends Ingredient{
        public Milk(String name, int price, int size, int x, int y, int expire) {
            super("milk", 25, 1, x, y, 4);
        }
    }

    public static class Flour extends Ingredient{
        public Flour(String name, int price, int size, int x, int y, int expire) {
            super("flour", 40, 2, x, y, 5);
        }
    }

    public static class Weave extends Ingredient{
        public Weave(String name, int price, int size, int x, int y, int expire) {
            super("weave", 50, 2, x, y, 5);
        }
    }

    public static class CMilk extends Ingredient{
        public CMilk(String name, int price, int size, int x, int y, int expire) {
            super("cmilk", 60, 2, x, y, 5);
        }
    }

    public static class Bread extends Ingredient{
        public Bread(String name, int price, int size, int x, int y, int expire) {
            super("bread", 80, 4, x, y, 6);
        }
    }

    public static class Cloth extends Ingredient{
        public Cloth(String name, int price, int size, int x, int y, int expire) {
            super("cloth", 130, 4, x, y, 6);
        }
    }

    public static class IceCream extends Ingredient{
        public IceCream(String name, int price, int size, int x, int y, int expire) {
            super("icecream", 120, 4, x, y, 6);
        }
    }
}
