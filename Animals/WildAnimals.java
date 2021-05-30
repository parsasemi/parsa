package Animals;

public class WildAnimals {
    public int sellPrice;
    public int x;
    public int y;
    public int cageP;
    public int capacity;
    public int speed;

    public WildAnimals() {
    }

    public WildAnimals(int sellPrice, int x, int y, int cageP, int capacity, int speed) {
        this.sellPrice = sellPrice;
        this.x = x;
        this.y = y;
        this.cageP = cageP;
        this.capacity = capacity;
        this.speed = speed;
    }

    public static class Bear extends WildAnimals {
        public Bear() {
        }

        public Bear(int sellPrice, int x, int y, int health, int capacity, int speed, int size) {
            super(400, x, y, 4, 15, 1);
        }
    }

    public static class Tiger extends WildAnimals {
        public Tiger() {
        }

        public Tiger(int sellPrice, int x, int y, int health, int capacity, int speed, int size) {
            super(500, x, y, 4, 15, 2);
        }
    }

    public static class Lion extends WildAnimals {
        public Lion() {
        }

        public Lion(int sellPrice, int x, int y, int health, int capacity, int speed, int size) {
            super(300, x, y, 3, 15, 1);
        }
    }
}
