import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class project3main {
    public static void main(String[] args) throws Exception {
        // Since I used nextline and trimmed it, if there is no nextline I couldn't create the object
        // Instead of try-catch I added a new line to end of to the given input 
        File input = new File(args[0]);
        FileWriter nextLineFileWriter = new FileWriter(input,true);
        nextLineFileWriter.append("\n");
        nextLineFileWriter.close();

        Scanner scan = new Scanner(Paths.get(args[0]));
        BufferedWriter write = Files.newBufferedWriter(Paths.get(args[1]));
        int timeLimit = scan.nextInt();
        int numberOfCities = scan.nextInt();
        String nameOfMecnunsNode = scan.next();
        String nameOfLeylasNode = scan.next();
        boolean didMecnunReachedLeyla = false;
        // Creating dijkstra graph
        DijkstraGraph graphForDijkstra = new DijkstraGraph();
        // Creating MST graph
        MSTGraph graphForMST = new MSTGraph();
        // Hashmap of nodes with their names as keys
        HashMap<String,DijkstraNode> allDijkstraNodes = new HashMap<String,DijkstraNode>();
        HashMap<String,MSTNode> allMSTNodes = new HashMap<String,MSTNode>();
        // Creating the nodes and in order to create edges between them I first created all nodes, then I put the edges
        for (int i = 0; i < numberOfCities; i++) {
            String nodeName = scan.next();
            String[] nodeInfo = scan.nextLine().trim().split(" ");
            if(nodeName.charAt(0)=='c'){
                // If node is a C node we create it as Dijkstra node and put it to hashmap with name and putting it to dijkstra graph
                DijkstraNode node = new DijkstraNode(nodeName, nodeInfo);
                allDijkstraNodes.put(nodeName, node);
                graphForDijkstra.addNodeToHashMap(node);
            }
            if(nodeName.equals(nameOfLeylasNode)){
                // If node is the leyla node, we put it to MST graph also, because it's not a D node but it has to be in MST
                MSTNode node = new MSTNode(nodeName, nodeInfo);
                allMSTNodes.put(nodeName, node);
                graphForMST.addVGraph(node);

            }
            if(nodeName.charAt(0)=='d'){
                // If node is a D node we create it as MST node and put it to hashmap with name and putting it to V graph of MST
                MSTNode node = new MSTNode(nodeName, nodeInfo);
                allMSTNodes.put(nodeName, node);
                graphForMST.addVGraph(node);
            }
        }
        createNodeInteractionsDijkstra(graphForDijkstra, allDijkstraNodes ,nameOfLeylasNode);
        graphForDijkstra = Dijkstra.shortestPath(graphForDijkstra, allDijkstraNodes.get(nameOfMecnunsNode));
        DijkstraNode nodeOfLeyla = graphForDijkstra.getNodeHashMap().get(nameOfLeylasNode);
        DijkstraNode nodeOfMecnun = graphForDijkstra.getNodeHashMap().get(nameOfMecnunsNode);
        int timeOfTravel = nodeOfLeyla.getDistance();
        didMecnunReachedLeyla = DijkstraEnd(write, timeLimit, didMecnunReachedLeyla, nodeOfLeyla, timeOfTravel,nodeOfMecnun);

        
        createNodeInteractionsMST(graphForMST, allMSTNodes,nameOfLeylasNode);
        graphForMST = MST.createGraph(graphForMST);

        write.newLine();
        if(didMecnunReachedLeyla==true){
            write.write(String.valueOf(graphForMST.totalCost * 2));
        }
        else{
            write.write(String.valueOf(-1));
        }
        scan.close();
        write.close();
    }
    // Writing process is handled here
    private static boolean DijkstraEnd(BufferedWriter write, int timeLimit, boolean didMecnunReachedLeyla,
            DijkstraNode nodeOfLeyla, int timeOfTravel,DijkstraNode nodeMecnun) throws IOException {
        if (timeOfTravel <= timeLimit) {
            didMecnunReachedLeyla = true;
        }
        if (timeOfTravel == Integer.MAX_VALUE) {
            write.write(String.valueOf(-1));
        } else if (timeOfTravel < Integer.MAX_VALUE) {
            write.write(nodeOfLeyla.parentFinder(nodeMecnun, nodeOfLeyla));
        }
        return didMecnunReachedLeyla;
    }

    // After creating all the nodes, we have to create edges-relations between them
    public static void createNodeInteractionsDijkstra(DijkstraGraph graph,HashMap<String,DijkstraNode> allNodes,String nodeLeyla){
        // Iterating all the dijkstra nodes
        for (Map.Entry<String,DijkstraNode> nodeWithName : graph.getNodeHashMap().entrySet()) {
            // If the node we process is Leyla we don't create edges since they go to the D nodes
            if(nodeWithName.getKey().equals(nodeLeyla)){
                continue;
            }
            else{
                // Getting the node and according to it's info create the edges
                DijkstraNode node = nodeWithName.getValue();
                int numberOfEdges = node.getAdjacentNodeInfo().length/2;
                for (int i = 0; i < numberOfEdges; i++) {
                    DijkstraNode adjacentNode = allNodes.get(node.getAdjacentNodeInfo()[2*i]);
                    int distance = Integer.parseInt(node.getAdjacentNodeInfo()[2*i+1]);
                    // Putting the node-cost pair into the adjacent node hashmap
                    node.addAdjacentNode(adjacentNode, distance);
                }
                // In order to use memory efficiently after we done with the information, we set it to null
                // Otherwise I get outofmemoryexception
                node.setAdjacentNodeInfo(null);
            }
        }

    }
    // Creating the edges between leyla's node and D nodes
    public static void createNodeInteractionsMST(MSTGraph graph,HashMap<String,MSTNode> allNodes,String leylaNode){
        Iterator<MSTNode> iterator = graph.getVGraph().iterator();
        while(iterator.hasNext()){
            MSTNode node = iterator.next();
            int numberOfEdges = node.getAdjacentNodeInfo().length/2;
            for (int i = 0; i < numberOfEdges; i++) {
                if(node.getName().equals(leylaNode)){
                    if(node.getAdjacentNodeInfo()[2*i].charAt(0)=='c'){
                        // If the node is leyla's node and since we don't need dijkstra nodes while creating MST we ignore that edges
                        continue;
                    }
                }
                // In order to create undirected graph, after creating an edge from node1 to node2
                // I create another edge which has the same length but it's from node2 to node1 
                MSTNode secondNode = allNodes.get(node.getAdjacentNodeInfo()[2*i]);
                int cost = Integer.parseInt(node.getAdjacentNodeInfo()[2*i+1]);
                MSTEdge edge = new MSTEdge(node, secondNode, cost);
                node.addAdjacentNodes(secondNode, edge);
                MSTEdge returnEdge = new MSTEdge(secondNode, node, cost);
                secondNode.addAdjacentNodes(node, returnEdge);
            }
            // After using the information we need, we set it to null
            node.setAdjacentNodeInfo(null);
        }
    }
}
