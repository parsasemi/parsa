public class  DomesticAnimals {
    int SellPrice;
    int BuyPrice;
    int x;
    int y;
    int health;
    int speed;
    int capacity;
    String product;
    int size;

    public DomesticAnimals() {
    }

    public DomesticAnimals(int sellPrice, int buyPrice, int x, int y, int health, int speed, int capacity, String product, int size) {
        SellPrice = sellPrice;
        BuyPrice = buyPrice;
        this.x = x;
        this.y = y;
        this.health = health;
        this.speed = speed;
        this.capacity = capacity;
        this.product = product;
        this.size = size;
    }

    public static class Buffalo extends DomesticAnimals {

    }

    public static class Chicken extends DomesticAnimals{

    }

    public static class Turkey extends DomesticAnimals{
    }
}
