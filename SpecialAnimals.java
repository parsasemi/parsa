public class SpecialAnimals {
    int x;
    int y;
    int buyPrice;
    int sellPrice;

    public SpecialAnimals() {
    }

    public SpecialAnimals(int x, int y, int buyPrice, int sellPrice) {
        this.x = x;
        this.y = y;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public static class Dog extends SpecialAnimals{
    }

    public static class Cat extends SpecialAnimals{
    }
}