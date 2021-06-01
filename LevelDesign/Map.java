package LevelDesign;

public class Map {
    public int length;
    public int height;
    public Cell[][] map = new Cell[height][length];
    ////////// Tamam Khane ha 0 beshan.

    public Map(int length, int height) {
        this.length = length;
        this.height = height;

    }
}
