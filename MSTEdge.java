public class MSTEdge{
    private MSTNode firstNode;
    private MSTNode secondNode;
    private int cost;

    // Creating a new edge with two nodes and cost
    public MSTEdge(MSTNode firstNode, MSTNode secondNode, int cost) {
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.cost = cost;
    }

    // Checking if the edge is creating a loop (if it's going one node which included to another one also included that means it's a loop) 
    public int isEdgeGoesStoV(){
        if(firstNode.isNodeIncluded()&&secondNode.isNodeIncluded()){
            return -1;
        }
        return 1;
    }
    
    public MSTNode getFirstNode() {
        return firstNode;
    }
    public MSTNode getSecondNode() {
        return secondNode;
    }
    public int getCost() {
        return cost;
    }
    
    
}
