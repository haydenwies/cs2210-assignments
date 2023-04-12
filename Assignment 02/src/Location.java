/**
 * Location stores the x and y coordinated of an object relative to its parent
 */
public class Location {
    
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getx() {
        return this.x;
    }

    public int gety() {
        return this.y;
    }

    public int compareTo(Location p) {
        // Compare x and y values of this location (1) object to location p (2) (passed from parameter)
        if (this.gety() > p.gety() || (this.gety() == p.gety() && this.getx() > p.getx())) {
            // If y1 > y2 or (y1 = y2 and x1 > x2)
            return 1;
        } else if (this.getx() == p.getx() && this.gety() == p.gety()) {
            // If x1 = x2 and y1 = y2
            return 0;
        } else if (this.gety() < p.gety() || (this.gety() == p.gety() && this.getx() < p.getx())) {
            // If y1 < y2 or (y1 = y2 and x1 < x2)
            return -1;
        } else {
            // I think this line is impossible to reach but I put it here just in case
            throw new Error("Compare values did not meet specified conditions. See Location.java -> compareTo.");
        }
    }

}
