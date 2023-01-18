import java.util.ArrayList;
import java.util.PriorityQueue;

public class MSTGraph {
    private ArrayList<MSTNode> VGraph = new ArrayList<MSTNode>();
    private PriorityQueue<MSTEdge> edges = new PriorityQueue<MSTEdge>(new MSTEdgeComparator());
    int totalCost = 0;
    
    public PriorityQueue<MSTEdge> getEdges() {
        return edges;
    }
    public ArrayList<MSTNode> getVGraph() {
        return VGraph;
    }
    public void addVGraph(MSTNode vGraphNode) {
        VGraph.add(vGraphNode);
    }


    
}
