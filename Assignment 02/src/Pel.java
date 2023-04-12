// Pel represents an object contained within the game window
public class Pel {
    
    private Location locus;
    private int color;

    public Pel(Location p, int color) {
        this.locus = p;
        this.color = color;
    }

    public Location getLocus() {
        return this.locus;
    }

    public int getColor() {
        return this.color;
    }

}
