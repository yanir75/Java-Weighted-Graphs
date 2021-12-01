package api;

import java.util.HashMap;

public class Node implements  NodeData{
    // edgeData or edges we will decide
    private final HashMap<Double, EdgeData> Edges = new HashMap<>();
    private final int id;
    private GeoLocation location;
    private double weight;
    private String info;
    private int tag;


    public Node(double x, double y, int id) {
        this.id = id;
        this.location = new Location(x, y, 0);
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

    /**
     * This method adds a new Edge @e to the HashMap of the current Edges of this Node.
     * @param e - new Edge to add to this Node.
     */
    public void addEdge(Edge e){
        double key = e.getSrc() + e.getDest() * MyGraph.BIGNUMBER;
        if(!this.Edges.containsKey(key)) {
            this.Edges.put(key, e);
        }
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

    @Override
    public String toString() {
        return "\n{Index = " + id + " ,location =" + location+ ",\nEdges =" + Edges.values() + "}\n";
    }
}
