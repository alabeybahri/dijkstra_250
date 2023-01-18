import java.util.HashSet;
import java.util.Map;

public class Dijkstra {
    
    public static DijkstraGraph shortestPath(DijkstraGraph graph, DijkstraNode source) {
        // Since the source has 0 cost to go from source, we change it from infinity to 0 
        source.setDistance(0);
        // Creating a hashset for unsettled nodes, later we will use to check if the node has been processed or not
        HashSet<DijkstraNode> unsettledNodes = new HashSet<>();
        // We didn't settle the source node yet, so we're adding it to unsettledNodes HashSet
        unsettledNodes.add(source);
        // If there are still nodes that are unsettled, we settle them by increasing order of their distance from source node
        while (unsettledNodes.size()>0) {
            // Getting one node from HashSet according to it's distance from source node (we get minimum distance)
            DijkstraNode currentNode = getNodeToProcess(unsettledNodes);
            // Removing the node from unsettled
            unsettledNodes.remove(currentNode);
            // Iterating over the all adjacent nodes of current node
            for (Map.Entry<DijkstraNode, Integer> adjacentNodeWithEdge: currentNode.getAdjacentNodes().entrySet()) {
                // Getting the adjacent node with it's cost
                DijkstraNode adjacentNode = adjacentNodeWithEdge.getKey();
                Integer edgeCost = adjacentNodeWithEdge.getValue();
                // Checking if we settled this adjacent node before
                if (!adjacentNode.isSettled()) {
                    // If the new cost of going to the adjacent node from current node is smaller than before, change it   
                    calculateMinimumDistance(adjacentNode, edgeCost, currentNode);
                    // Adding it to unsettled nodes hashset to settle later on
                    unsettledNodes.add(adjacentNode);
                }
            }
            // Since we visited all adjacent (which is not in the settled hashset) nodes of current node we can set it as settled 
            currentNode.setSettled(true);
        }
        // If there are no unsettled node left, we can return the updated graph
        return graph;
    }

    
    private static DijkstraNode getNodeToProcess(HashSet <DijkstraNode> unsettledNodes) {
        // Gets the node with the minimum distance from the source node
        // Since we calling this method if unsettledNodes is not empty, we are sure that we won't get exception
        DijkstraNode nodeToReturn = null;
        int minDistance = Integer.MAX_VALUE;
        // Iterating over the nodes and finding the node with the minumum distance value 
        // If distance values are equal, returns the first appearance (It doesn't matter since we check other node too)
        for (DijkstraNode node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < minDistance) {
                minDistance = nodeDistance;
                nodeToReturn = node;
            }
        }
        return nodeToReturn;
    }
    
    // Going from sourcenode to newNode, we check if the current distance of the newNode is bigger than new cost
    // If so, we update the distance of the newNode as distanceOfSourceNode + edgeCost
    private static void calculateMinimumDistance(DijkstraNode newNode, Integer edgeCost, DijkstraNode sourceNode) {
    Integer sourceDistance = sourceNode.getDistance();
    if (sourceDistance + edgeCost < newNode.getDistance()) {
        // Update the value of distance of newNode
        newNode.setDistance(sourceDistance + edgeCost);
        // Updating the parent node
        newNode.setParent(sourceNode);
    }
}
}
