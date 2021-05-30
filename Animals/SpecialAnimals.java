package Animals;

public class SpecialAnimals {
    public int x;
    public int y;
    public int buyPrice;
    public int sellPrice;
    public int size;

    public SpecialAnimals() {
    }

    public SpecialAnimals(int x, int y, int buyPrice) {
        this.x = x;
        this.y = y;
        this.buyPrice = buyPrice;

    }

    public static class Dog extends SpecialAnimals{
        public Dog(int x, int y) {
            super(x, y, 100);
        }

        public Dog() {
        }
    }

    public static class Cat extends SpecialAnimals{
        public Cat() {
        }

        public Cat(int x, int y) {
            super(x, y, 150);
        }
    }
}