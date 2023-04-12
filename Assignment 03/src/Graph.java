import java.util.ArrayList;
import java.util.Iterator;

public class Graph implements GraphADT {
    
    private Node[] v;
    private Edge[][] e;

    public Graph(int n) {
        // Make vertices array and populate with nodes
        this.v = new Node[n];
        for (int i=0; i<n; i++) {
            this.v[i] = new Node(i);
        }
        // Make matrix to store edges (all elements set to null initially)
        this.e = new Edge[n][n];
    }

    public Node getNode(int id) throws GraphException {
        // Check if node exists
        if (this.hasNode(id)) {
            return this.v[id];
        } else {
            throw new GraphException("getNode - ID out of range.");
        }
    }

    public void addEdge(Node u, Node v, String edgeType) throws GraphException {
        // Check if nodes exist first
        if (this.hasNode(u.getId()) && this.hasNode(v.getId())) {
            // Check if node already exist
            if (this.hasEdge(u, v)) {
                throw new GraphException("addEdge - Edge already exists.");
            } else {
                // Add first order
                this.e[u.getId()][v.getId()] = new Edge(u, v, edgeType);
                // Add second order
                this.e[v.getId()][u.getId()] = new Edge(u, v, edgeType);
            }
        } else {
            throw new GraphException("addEdge - One or more nodes does not exist.");
        }
    }

    public Iterator<Edge> incidentEdges(Node u) throws GraphException {
        // Check if node exists
        if (this.hasNode(u.getId())) {
            // Loop through all edges with node u
            ArrayList<Edge> edges = new ArrayList<Edge>();
            for (int i=0; i<this.e.length; i++) {
                if (this.e[u.getId()][i] != null) {
                    edges.add(this.e[u.getId()][i]);
                }
            }
            // If more than one edge exists return them as iterator
            if (edges.size() > 0) {
                return edges.iterator();
            } else {
                return null;
            }
            // If edge exists add to list
        } else {
            throw new GraphException("incidentIterator - Node u is out of range.");
        }
    }

    public Edge getEdge(Node u, Node v) throws GraphException {
        // Check if edge exists
        if (this.hasEdge(u, v)) {
            return this.e[u.getId()][v.getId()];
        } else {
            throw new GraphException("getEdge - Edge does not exist because one or more nodes do not exist or edge has not been set.");
        }
    }

    public boolean areAdjacent(Node u, Node v) throws GraphException {
        // Check if nodes exsist
        if (this.hasNode(u.getId()) && this.hasNode(v.getId())) {
            // If nodes are connected return true, otherwise return false
            if (this.hasEdge(u, v)) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new GraphException("areAdjacent - One or more nodes are out of range.");
        }
    }

    // ---------- PRIVATE METHODS ---------- //

    private boolean hasNode(int id) {
        // Node cannot exist if its id it larger than array that stores nodes
        if (id >= this.v.length) {
            return false;
        } else {
            return true;
        }
    }

    private boolean hasEdge(Node u, Node v) {
        // Check if nodes exist
        if (this.hasNode(u.getId()) && this.hasNode(v.getId())) {
            // Edge does not exist if either edge(u,v) or edge(v,u) don't exist
            if (this.e[u.getId()][v.getId()] == null || this.e[v.getId()][u.getId()] == null) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

}
