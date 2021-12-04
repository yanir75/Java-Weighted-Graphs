package api;

import java.util.HashMap;

public class Distance {
    HashMap<Integer,Double> dist = new HashMap<>();
    public Distance(int starting_node,int node_id,double weight){
        dist.put(node_id,weight);
        dist.put(starting_node,0.0);

    }
    public double getMax(){
        double max = 0;
        for (double value:dist.values()) {
            if(max<value)
            max=value;
        }
        return max;
    }

    public void setDist(int node_key,double distance) {
        dist.put(node_key,distance);
    }

    public double getDist(int node_key) {
        return dist.get(node_key);
    }
}
