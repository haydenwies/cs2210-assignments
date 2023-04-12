public class Pointer {
    
    private Node next;

    public Pointer() {

        this.next = null;

    }

    public Node getValue() {

        return this.next;
        
    }

    public void setValue(Node node) {

        this.next = node;

    }

}
