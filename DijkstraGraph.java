import java.util.HashMap;

public class DijkstraGraph {

    // Hash-map of the nodes, key is the name of the node, value is the node object itself
    private HashMap<String,DijkstraNode> nodeHashMap = new HashMap<String,DijkstraNode>();

    // Puts a new String-DijkstraNode pair into nodesHashMap
    public void addNodeToHashMap(DijkstraNode node) {
        nodeHashMap.put(node.getName(), node);
    }

    // Returns the nodesHashmap
    public HashMap<String,DijkstraNode> getNodeHashMap() {
        return nodeHashMap;
    }
    
}
