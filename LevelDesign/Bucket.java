package LevelDesign;

public class Bucket {
    public int capacity;
    public int duration;
    public int maxDuration = 5;
    public Boolean full = false;
    public Bucket(int capacity, int duration) {
        this.capacity = capacity;
        this.duration = duration;
        this.maxDuration = 5;
        this.full = false;
    }
}
