import java.util.HashMap;

public class MSTNode {
    
    private String name;
    private boolean isNodeIncluded = false;
    private String[] adjacentNodeInfo;
    private HashMap<MSTNode,MSTEdge> adjacentNodes = new HashMap<MSTNode,MSTEdge>();
    
    // Information about the adjacent nodes of node as string array
    public MSTNode(String name, String[] adjacentNodeInfo) {
        this.name = name;
        this.adjacentNodeInfo = adjacentNodeInfo;
    }
    
    // Since the graph is undirected we will take the minimum of the edges between two nodes if there are more than one
    // If there is no edge, we put it but if there is we check the minimum cost and add that edge and delete the other
    public void addAdjacentNodes(MSTNode node ,MSTEdge edge) {
        adjacentNodes.putIfAbsent(node,edge);
        MSTEdge oldEdge = adjacentNodes.get(node);
        if(edge.getCost()<oldEdge.getCost()){
            adjacentNodes.put(node, edge);
        }
        
    }
    public String getName() {
        return name;
    }

    public boolean isNodeIncluded() {
        return isNodeIncluded;
    }

    public void setAdjacentNodeInfo(String[] adjacentNodeInfo) {
        this.adjacentNodeInfo = adjacentNodeInfo;
    }

    public void setNodeIncluded(boolean isNodeIncluded) {
        this.isNodeIncluded = isNodeIncluded;
    }

    public String[] getAdjacentNodeInfo() {
        return adjacentNodeInfo;
    }

    public HashMap<MSTNode, MSTEdge> getAdjacentNodes() {
        return adjacentNodes;
    }

    
    
}
