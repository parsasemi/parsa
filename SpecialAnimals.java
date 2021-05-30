public class SpecialAnimals {
    int x;
    int y;
    int buyPrice;
    int sellPrice;
    int size;

    public SpecialAnimals() {
    }

    public SpecialAnimals(int x, int y, int buyPrice, int sellPrice, int size) {
        this.x = x;
        this.y = y;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.size = size;

    }

    public static class Dog extends SpecialAnimals{
    }

    public static class Cat extends SpecialAnimals{
    }
}