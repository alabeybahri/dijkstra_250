public class MST {

    // After choosing a node from VGraph adding the nodes' all edges into priority queue, which is sorted by their weights
    public static void putEdgesOnPQ(MSTGraph graph,MSTNode node){
        graph.getEdges().addAll(node.getAdjacentNodes().values());
    }

    public static MSTGraph createGraph(MSTGraph graph){
        // Getting the size of the graph because V graph is an arraylist so I have to arrange remove method faster than O(N)
        // In order to do that I get, and remove the last element of the arraylist, so it doesn't have to rearrange the indexes
        int size = graph.getVGraph().size();
        // First getting a random node from original graph
        MSTNode node = graph.getVGraph().get(size-1);
        // Removing the chosen node from original graph
        graph.getVGraph().remove(size-1);
        // Arranging the node as included, which implies we put the node into an imaginary other graph S
        node.setNodeIncluded(true);
        // Since the S and V are only connected by edges of our node (initially only the first node, but later there will be more edges)
        // We put all the edges onto priority queue
        putEdgesOnPQ(graph, node);
        while(!graph.getEdges().isEmpty()){
            // Since priorityqueue of edges are sorted by their weights we get the min weight one
            MSTEdge edge = graph.getEdges().poll();
            if(edge.isEdgeGoesStoV()==1){
                // After checking that the edge is not creating a loop 
                // Which means in my implementation that the edge is not going from S to S it goes from S to V
                // Incrementing the total cost by weight
                graph.totalCost +=edge.getCost();
                // Getting the destination node from the V graph
                MSTNode destinationNode = edge.getSecondNode();
                // Removing that destination graph
                graph.getVGraph().remove(destinationNode);
                // Putting all edges of newly removed nodes' to priority queue
                putEdgesOnPQ(graph, destinationNode);
                // Since we removed the new node from V, we have to put it to S (which means we set it as included)
                destinationNode.setNodeIncluded(true);
            }

        }
        // After eliminating all the edges between S and V, if there are still any node left on V that means we couldn't create MST 
        if(graph.getVGraph().size()>0){
            graph.totalCost = -1;
        }
        return graph;
    }


}
