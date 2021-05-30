public class WildAnimals {
    int sellPrice;
    int x;
    int y;
    int health;
    int capacity;
    int speed;
    int size;

    public WildAnimals() {
    }

    public WildAnimals(int sellPrice, int x, int y, int health, int capacity, int speed, int size) {
        this.sellPrice = sellPrice;
        this.x = x;
        this.y = y;
        this.health = health;
        this.capacity = capacity;
        this.speed = speed;
        this.size = size;
    }

    public static class Bear extends WildAnimals {
    }

    public static class Tiger extends WildAnimals {
    }

    public static class Lion extends WildAnimals {
    }
}
