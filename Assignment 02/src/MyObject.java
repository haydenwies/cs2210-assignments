// MyObject represents an object on the gameboard
public class MyObject implements MyObjectADT {
    
    private int id;
    private int width;
    private int height;
    private String type;
    private Location locus;
    private BinarySearchTree store;

    public MyObject(int id, int width, int height, String type, Location pos) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.type = type;
        this.locus = pos;
        this.store = new BinarySearchTree();
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public String getType() {
        return this.type;
    }

    public int getId() {
        return this.id;
    }

    public Location getLocus() {
        return this.locus;
    }

    public void setLocus(Location value) {
        this.locus = value;
    }

    public void addPel(Pel pix) throws DuplicatedKeyException {
        store.put(store.getRoot(), pix);
    }

    public boolean intersects(MyObject otherObject) {
        
        // Start at leftmost node in the tree
        Location item = this.store.smallest(this.store.getRoot()).getLocus();

        // Traverse through binary search tree
        while (this.store.successor(this.store.getRoot(), item) != null) {

            // Create new Location object with calculated values
            Location key = new Location((item.getx() + this.locus.getx() - otherObject.getLocus().getx()), (item.gety() + this.locus.gety() - otherObject.getLocus().gety()));

            // Check if otherObject has this new Location in its BST
            if (otherObject.store.get(otherObject.store.getRoot(), key) != null) {
                return true;
            }

            // Proceed to next node
            item = this.store.successor(this.store.getRoot(), item).getLocus();

        }

        // No nodes match in BST, return false
        return false;

    }

}
