import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Arrays;

public class MyMap {

    // Data pulled from file
    private int scale;
    private int startPoint;
    private int destinationPoint;
    private int width;
    private int length;
    private int maxPrivate;
    private int maxConstruction;

    private Graph graph;

    public MyMap(String inputFile) throws MapException {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            // Read first lines of data and save values
            this.scale = Integer.parseInt(reader.readLine());
            this.startPoint = Integer.parseInt(reader.readLine());
            this.destinationPoint = Integer.parseInt(reader.readLine());
            this.width = Integer.parseInt(reader.readLine());
            this.length = Integer.parseInt(reader.readLine());
            this.maxPrivate = Integer.parseInt(reader.readLine());
            this.maxConstruction = Integer.parseInt(reader.readLine());

            this.graph = new Graph(this.width*this.length);
            
            this.buildMatrix(reader);

            reader.close();
        } catch (FileNotFoundException e) {
            // If file doesn't exist throw error
            throw new MapException("MyMap - File" + inputFile + " was not found.");
        } catch (IOException e) {
            System.out.println(e);
        } 
        
    }

    public Graph getGraph() {
        return this.graph;
    }

    public int getStartingNode() {
        return this.startPoint;
    }

    public int getDestinationNode() {
        return this.destinationPoint;
    }

    public int maxPrivateRoads() {
        return this.maxPrivate;
    }

    public int maxConstructionRoads() {
        return this.maxConstruction;
    }

    public Iterator<Node> findPath(int start, int destination, int maxPrivate, int maxConstruction) {

        Stack path = new Stack(this.width*this.length);
        
        int privateCount = 0;
        int constructionCount = 0;

        try {
            // push start node
            path.push(new StackObj(this.graph.getNode(start), null));

            // While path.top is not destination and path.top is not null
            boolean destinationFound = false;
            while (!destinationFound) {

                // Somewhere on the board that isn't at destination
                Node currentNode = path.peek().getNode();

                // Mark node as traveled
                currentNode.markNode(true);

                // Get next node to travel to
                StackObj next = null;
                Iterator<Edge> allEdges = this.graph.incidentEdges(currentNode);

                while (next == null) {
                    if (allEdges.hasNext()) {
                        Edge checkEdge = allEdges.next();
                        StackObj checkPair;
                        // Find other node in edge object
                        if (checkEdge.firstNode() == currentNode) {
                            // Other node is second
                            checkPair = new StackObj(checkEdge.secondNode(), checkEdge);
                        } else {
                            // Other node is first
                            checkPair = new StackObj(checkEdge.firstNode(), checkEdge);
                        }
                        // Node isn't travelled
                        if (checkPair.getNode().getMark() == false) {
                            // Edge doesn't break any counters
                            if (checkEdge.getType() == "private") {
                                if (privateCount+1 <= maxPrivate) {
                                    next = checkPair;
                                }
                            } else if (checkEdge.getType() == "construction") {
                                if (constructionCount+1 <= maxConstruction) {
                                    next = checkPair;
                                }
                            } else {
                                next = checkPair;
                            }
                        }
                    } else {
                        break;
                    }
                }
                    
                // If next node is not null
                if (next != null) {
                    // Update counters if necessary
                    if (next.getEdge().getType() == "private") {
                        privateCount++;
                    } else if (next.getEdge().getType() == "construction") {
                        constructionCount++;
                    }
                    // Push next node to stack
                    path.push(next);
                    if (next.getNode().getId() == destination) {
                        destinationFound = true;
                    }
                } else {
                    // If next node is null
                    // Update counters if necessary
                    if (path.peek().getEdge() != null) {
                        if (path.peek().getEdge().getType() == "private") {
                            privateCount--;
                        } else if (path.peek().getEdge().getType() == "construction") {
                            constructionCount--;
                        }
                    } else {
                        destinationFound = true;
                    }
                    // Pop current node from stack
                    path.pop();
                }

            }            

        } catch (GraphException e) {
            System.out.println(e);
        }

        if (path.peek() != null) {
            Node[] reversePath = new Node[path.size()];
            while (path.peek() != null) {
                reversePath[path.size()-1] = path.pop().getNode();
            }
            return Arrays.asList(reversePath).iterator();
        } else {
            return null;
        }

        

    }

    // ---------- PRIVATE METHODS ---------- //

    private void buildMatrix(BufferedReader reader) {
        char[][] matrix = new char[(2*this.length)-1][(2*this.width)-1];
        String line = null;

        try {
            int lineNum = 0;
            while ((line = reader.readLine()) != null) {
                for (int i=0; i<line.length(); i++) {
                    matrix[lineNum][i] = line.charAt(i);
                }
                lineNum++;
            }

            this.buildMap(matrix);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void buildMap(char[][] matrix) {
        for (int i=0; i<matrix.length; i++) {
            for (int j=0; j<matrix[i].length; j++) {
                
                if (i%2==0) {
                    // On even row
                    if (j%2!=0 && matrix[i][j]!='B') {
                        // Edge exists at this location 
                        try {
                            this.graph.addEdge(
                                this.graph.getNode(this.getWest(i, j)), 
                                this.graph.getNode(this.getEast(i, j)), 
                                matrix[i][j]=='P' ? "public" : matrix[i][j]=='V' ? "private" : matrix[i][j]=='C' ? "construction" : ""
                            );
                        } catch (GraphException e) {
                            System.out.println(e);
                        }
                        
                    }
                } else {
                    // On odd row
                    if (j%2==0 && matrix[i][j]!='B') {
                        try {
                            this.graph.addEdge(
                                this.graph.getNode(this.getNorth(i, j)), 
                                this.graph.getNode(this.getSouth(i, j)), 
                                matrix[i][j]=='P' ? "public" : matrix[i][j]=='V' ? "private" : matrix[i][j]=='C' ? "construction" : ""
                            );
                        } catch (GraphException e) {
                            System.out.println(e);
                        }
                    }
                }

            }
        }
    }

    private int getWest(int i, int j) {
        return (int)((Math.floor((float)i/2)*this.width)+Math.floor((float)j/2));
    }

    private int getEast(int i, int j) {
        return (int)((Math.floor((float)i/2)*this.width)+Math.ceil((float)j/2));
    }
       
    private int getNorth(int i, int j) {
        return (int)((Math.floor((float)(i-1)/2)*this.width)+Math.floor((float)j/2));
    }

    private int getSouth(int i, int j) {
        return (int)((Math.floor((float)(i+1)/2)*this.width)+Math.floor((float)j/2));
    }

    

}