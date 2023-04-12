public class Node {

    private Node next;
    private Record element;

    public Node(Record element) {
    
        this.element = element;

    }

    public Node getNext() {

        return this.next;

    }

    public void setNext(Node next) {

        this.next = next;

    }

    public Record getElement() {

        return this.element;

    }

    public void setElement(Record element) {

        this.element = element;

    }

}
