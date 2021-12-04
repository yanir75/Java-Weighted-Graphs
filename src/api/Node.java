package api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Node implements NodeData {
    // edgeData or edges we will decide
    private final HashMap<String, Edge> Edges = new HashMap<>();
    private int id = -1;
    private GeoLocation location;
    private double weight;
    //    private NodeData father = null;
    private String info;
    private int tag;
//    private boolean black = false;


    public Node(double x, double y, int id) {
        this.id = id;
        this.location = new Location(x, y, 0);
    }

    public Node(NodeData n, Iterator<EdgeData> iter) {
        this.id = n.getKey();
        this.weight = n.getWeight();
        this.location = new Location(n.getLocation().x(), n.getLocation().y(), n.getLocation().z());
        this.info = n.getInfo();
        this.tag = n.getTag();
        while (iter.hasNext()) {
            EdgeData e = iter.next();
            String key = e.getSrc() +"-"+ e.getDest();
            this.Edges.put(key, new Edge(e));
        }

    }

    public Node copy() {
        Node c = new Node(location.x(), location.y(), id);
        c.weight = weight;
        c.info = info;
        c.tag = tag;
        HashMap<String, Edge> y = c.getEdges();
        Set<String> x = Edges.keySet();
        for (String i : x) {
            y.put(i, Edges.get(i));
        }
        return c;
    }

    @Override
    public int getKey() {
        if (this.id != -1) {
            return this.id;
        } else {
            throw new NullPointerException();
        }
    }

//    public NodeData getFather() {
//        return father;
//    }

    public int getId() {
        return id;
    }

//    public boolean isBlack() {
//        return black;
//    }

//    public void setBlack() {
//        this.black = "BLACK";
//    }

//    public void setFather(NodeData father) {
//        this.father = father;
//    }

    @Override
    public GeoLocation getLocation() {
        return location;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double w) {
        weight = w;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        info = s;
    }

    @Override
    public int getTag() {
        return tag;
    }


    @Override
    public void setTag(int t) {
        tag = t;
    }

    /**
     * This method adds a new Edge @e to the HashMap of the current Edges of this Node.
     *
     * @param e - new Edge to add to this Node.
     */
    public void addEdge(Edge e) {
        String key = e.getSrc() +"-"+ e.getDest();
        if (!this.Edges.containsKey(key)) {
            this.Edges.put(key, e);
        }
    }

    public void removeEdge(String key) {
        this.Edges.remove(key);
    }

    @Override
    public void setLocation(GeoLocation p) {
        location = p;
    }

    public HashMap<String, Edge> getEdges() {
//        return this.Edges.keySet().toArray(new Double[0]);
        return this.Edges;
    }

//    @Override
//    public String toString() {
//        return "\n<Index = " + id + ",\nlocation =" + location + ",\nEdges =" + Edges.values() + ">\n";
//    }
@Override
public String toString() {
    return "{\n"+'"'+"pos"+'"'+": " +'"'+location.x()+","+location.x()+'"'+",\n"+'"'+"id"+'"'+": "+id+"\n}";
}
}
