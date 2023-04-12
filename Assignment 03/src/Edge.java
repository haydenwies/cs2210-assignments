public class Edge {
    
    private Node first;
    private Node second;
    private String type;

    public Edge(Node u, Node v, String type) {
        this.first = u;
        this.second = v;
        this.type = type;
    }

    public Node firstNode() {
        return this.first;
    }

    public Node secondNode() {
        return this.second;
    }

    public String getType() {
        return this.type;
    }

}
