import java.util.Comparator;

public class MSTEdgeComparator implements Comparator<MSTEdge> {
    
    @Override
    public int compare(MSTEdge o1, MSTEdge o2) {
    
        if(o1.getCost()<o2.getCost()){
            return -1;
        }
        else if(o1.getCost()>o2.getCost()){
            return 1;
        }
        else{
            return 0;
        }
    }
}
