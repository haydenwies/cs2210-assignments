// BinarySearchTree represents the data structure used in MyObject to contain (and organize) all child objects
public class BinarySearchTree implements BinarySearchTreeADT {
    
    private BNode root;

    public BinarySearchTree() {
        this.root = new BNode();
    }

    public Pel get(BNode r, Location key) {
        // If leaf, key not at current location and cannot progress further
        if (r.isLeaf()) {
            return null;
        } else {
            // Compare key with root
            int result = r.getData().getLocus().compareTo(key);
            if (result == 0) {
                // If root equals key return data of root
                return r.getData();
            } else if (result == -1) {
                // If root < key go to left child
                return this.get(r.rightChild(), key);
            } else if (result == 1) {
                // If root > key go to right child
                return this.get(r.leftChild(), key);
            } else {
                return null;
            }
        }
    }

    public void put(BNode r, Pel newData) throws DuplicatedKeyException {
        // If node is leaf place data and add new leaves
        if (r.isLeaf()) {
            this.updateNode(r, newData, new BNode(), new BNode());
            r.leftChild().setParent(r);
            r.rightChild().setParent(r);
        } else {
            // Compare key with root
            int result = r.getData().getLocus().compareTo(newData.getLocus());
            if (result == 0) {
                // If key equals root throw error
                throw new DuplicatedKeyException("Key already exists in BinarySearchTree");
            } else if (result == -1) {
                // If root < key go to right child
                this.put(r.rightChild(), newData);
            } else if (result == 1) {
                // If root > key go to left child
                this.put(r.leftChild(), newData);
            }
        }
    }

    public void remove(BNode r, Location key) throws InexistentKeyException {
        if (r.isLeaf()) {
            // Made it to bottom of tree, key does not exist
            throw new InexistentKeyException("Key being removed does not exist in BinarySearchTree.");
        } else {
            int result = r.getData().getLocus().compareTo(key);
            if (result == -1) {
                // If root < key go to right child
                remove(r.rightChild(), key);
            } else if (result == 1) {
                // If root > key go to left child
                remove(r.leftChild(), key);
            } else if (result == 0) {
                // Correct node found
                if (r.leftChild().isLeaf() && r.rightChild().isLeaf()) {
                    // Case 01: no child
                    this.updateNode(r, null, null, null);
                } else if (r.leftChild().isLeaf()) {
                    // Case 02: one child (right only)
                    BNode tmp = r;
                    this.updateNode(r, tmp.rightChild().getData(), tmp.rightChild().leftChild(), tmp.rightChild().rightChild());
                } else if (r.rightChild().isLeaf()) {
                    // Case 02: one child (left only)
                    BNode tmp = r;
                    this.updateNode(r, tmp.leftChild().getData(), tmp.leftChild().leftChild(), tmp.leftChild().rightChild());
                } else {
                    // Case 03: two children
                    BNode tmp = this.smallestNode(r.rightChild());
                    r.setContent(tmp.getData());
                    this.remove(r.rightChild(), tmp.getData().getLocus());
                }
            }
        }
    }

    public Pel successor(BNode r, Location key) {
        if (r.isLeaf()) {
            // The successor exists one or more levels above the target node
            BNode p = r.parent();
            // Traverse to successor
            while (p != null && p.getData().getLocus().compareTo(key) == -1) {
                p = p.parent();
            }
            return p.getData();
        } else { 
            int result = r.getData().getLocus().compareTo(key);
            if (result == -1) {
                // r is less than key, go right
                return this.successor(r.rightChild(), key);
            } else if (result == 1) {
                // r is more than key, go left
                return this.successor(r.leftChild(), key);
            } else {
                // Found node
                if (!r.rightChild().isLeaf()) {
                    // Return smallest value of right subtree
                    return this.smallest(r.rightChild());
                } else {
                    // The successor exists one or more levels above the target node
                    BNode p = r.parent();
                    // Traverse to successor
                    while (p != null && p.getData().getLocus().compareTo(key) == -1) {
                        p = p.parent();
                    }
                    return p!= null ? p.getData() : null;
                }
            }
        }
    }

    public Pel predecessor(BNode r, Location key) {
        int result = r.getData().getLocus().compareTo(key);
        if (r.isLeaf()) {
            // The predecessor exists one or more levels above the target node
            BNode p = r.parent();
            // Traverse to predecessor
            while (p != null && p.getData().getLocus().compareTo(key) == 1) {
                p = p.parent();
            }
            return p.getData();
        } else if (result == -1) {
            // r is less than key, go right
            return this.predecessor(r.rightChild(), key);
        } else if (result == 1) {
            // r is more than key, go left
            return this.predecessor(r.leftChild(), key);
        } else {
            // Found node
            if (!r.leftChild().isLeaf()) {
                // Return largest value of left subtree
                return this.largest(r.leftChild());
            } else {
                // The predecessor exists one or more levels above the target node
                BNode p = r.parent();
                // Traverse to predecessor
                while (p != null && p.getData().getLocus().compareTo(key) == 1) {
                    p = p.parent();
                }
                return p!=null ? p.getData() : null;
            }
        }
    }

    public Pel smallest(BNode r) {
        return this.smallestNode(r).getData();
    }

    public Pel largest(BNode r) {
        return this.largestNode(r).getData();
    }

    public BNode getRoot() {
        return this.root;
    }

    // ---------- PRIVATE METHODS ---------- //

    private void updateNode(BNode node, Pel c, BNode l, BNode r) {
        // Update all values of a node in one function call
        node.setContent(c);
        node.setLeftChild(l);
        node.setRightChild(r);
    }

    private BNode smallestNode(BNode r) {
        // Traverse to the leftmost node in tree and return
        if (r.isLeaf()) {
            return r.parent();
        } else {
            return this.smallestNode(r.leftChild());
        }
    }

    private BNode largestNode(BNode r) {
        // Traverse to the rightmost node in the tree and return
        if (r.isLeaf()) {
            return r.parent();
        } else {
            return this.largestNode(r.rightChild());
        }
    }

}
