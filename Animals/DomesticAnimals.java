package Animals;

public class  DomesticAnimals {
    public int SellPrice;
    public int BuyPrice;
    public int x;
    public int y;
    public int health;
    public int speed;
    public int capacity;
    public String product;

    public DomesticAnimals() {
    }

    public DomesticAnimals(int sellPrice, int buyPrice, int x, int y, int health, int speed, int capacity, String product) {
        SellPrice = sellPrice;
        BuyPrice = buyPrice;
        this.x = x;
        this.y = y;
        this.health = health;
        this.speed = speed;
        this.capacity = capacity;
        this.product = product;
    }

    public static class Buffalo extends DomesticAnimals {
        public Buffalo() {
        }

        public Buffalo( int x, int y) {
            super(200, 400, x, y, 100, 1, 0, "milk");
        }
    }

    public static class Chicken extends DomesticAnimals{
        public Chicken() {
        }

        public Chicken(int x, int y) {
            super(50, 100, x, y, 100, 1, 0, "egg");
        }
    }

    public static class Turkey extends DomesticAnimals{
        public Turkey() {
        }

        public Turkey(int x, int y) {
            super(100, 200, x, y, 100, 1, 0, "feather");
        }
    }
}
