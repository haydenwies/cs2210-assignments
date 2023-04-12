public class StackObj {
    
    private Node node;
    private Edge edge;

    public StackObj(Node node, Edge edge) {
        this.node = node;
        this.edge = edge;
    }

    public Node getNode() {
        return this.node;
    }

    public Edge getEdge() {
        return this.edge;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

}
