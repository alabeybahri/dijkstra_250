import java.util.HashMap;


public class DijkstraNode {


    // Finding the parents of nodes recursively and creating the path and returning all path as string
    public String parentFinder(DijkstraNode mecnunDijkstraNode, DijkstraNode currentNode){    
        if(currentNode.equals(mecnunDijkstraNode)){
            return currentNode.getName();
        }
        DijkstraNode parent = currentNode.getParent();
        return (parentFinder(mecnunDijkstraNode, parent) + " " + currentNode.getName());
    }

    // After using the adjacent node info, we have to set it as null or we get out of memory exception
    // This set method only used for setting it to null, because this class has this field in it's constructor
    public void setAdjacentNodeInfo(String[] adjacentNodeInfo) {
        this.adjacentNodeInfo = adjacentNodeInfo;
    }


    // If the node is settled (Initially it's false because no node has been settled at first)
    private boolean isSettled = false;
    // Name of the node 
    private String name;
    // Distance of the node from starting node(Initially it's infinity because we don't know if we can reach that node)
    private int distance = Integer.MAX_VALUE;
    // Adjacent nodes of the node with their edge cost as integer
    private HashMap<DijkstraNode,Integer> adjacentNodes = new HashMap<DijkstraNode,Integer>();
    // Information of adjacent nodes as string array
    private String[] adjacentNodeInfo;
    // Parent of the node
    private DijkstraNode parent;

    // Creates an new dijkstraNode with the name and the information about adjacent node as string array
    public DijkstraNode(String name, String[] adjacentNodeInfo) {
        this.name = name;
        this.adjacentNodeInfo = adjacentNodeInfo;
    }

    // Puts a node integer pair into adjacent node hashmap, cost is the edge weight
    public void addAdjacentNode(DijkstraNode node,Integer cost){
        adjacentNodes.put(node, cost);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public HashMap<DijkstraNode, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    public String[] getAdjacentNodeInfo() {
        return adjacentNodeInfo;
    }

    public DijkstraNode getParent() {
        
        return parent;
    }

    public void setParent(DijkstraNode parent) {
        this.parent = parent;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public void setSettled(boolean isSettled) {
        this.isSettled = isSettled;
    }

    
    
}
