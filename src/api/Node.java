package api;

import java.util.HashMap;

public class Node implements  NodeData{
    // edgeData or edges we will decide
    private HashMap<Double, EdgeData> Edges = new HashMap<>();
    private int id;
    private GeoLocation location;
    private double weight;
    private String info;
    private int tag;


    public Node(String coordinates, int id) {
        this.id = id;
        //this.location = new Location();
    }

    @Override
    public int getKey() {
        return this.id;
    }

    @Override
    public GeoLocation getLocation() {
        return null;
    }

    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double w) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {
        return 0;
    }


    @Override
    public void setTag(int t) {

    }

    public void removeEdge(double key){
        this.Edges.remove(key);
    }

    @Override
    public void setLocation(GeoLocation p) {

    }

    public HashMap<Double, EdgeData> getEdges(){
//        return this.Edges.keySet().toArray(new Double[0]);
        return this.Edges;
    }
}
