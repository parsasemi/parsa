package LevelDesign;

public class Map {
    int length;
    int height;
    Cell[][] map = new Cell[height][length];

    public Map(int length, int height) {
        this.length = length;
        this.height = height;
    }
}
