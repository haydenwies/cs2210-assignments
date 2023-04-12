// BNode represents a single node used to create the BinarySearchTree object
public class BNode {
    
    private Pel value;
    private BNode left;
    private BNode right;
    private BNode parent;

    public BNode(Pel value, BNode left, BNode right, BNode parent) {
        // Will initialize new node with the provided values
        this.value = value;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public BNode() {
        // If no values are provided to the constructor, all values will be set to null
        this.value = null;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    public void setParent(BNode p) {
        this.parent = p;
    }

    public void setLeftChild(BNode p) {
        this.left = p;
    }

    public void setRightChild(BNode p) {
        this.right = p;
    }

    public void setContent(Pel value) {
        this.value = value;
    }

    public boolean isLeaf() {
        // Will return true if this node has no children, will return false otherwise
        if (this.left == null && this.right == null) {
            return true;
        } else {
            return false;
        }
    }

    public Pel getData() {
        return this.value;
    }

    public BNode parent() {
        return this.parent;
    }

    public BNode leftChild() {
        return this.left;
    }

    public BNode rightChild() {
        return this.right;
    }

}
