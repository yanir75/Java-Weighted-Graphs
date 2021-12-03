package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class Algorithms implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph gr;
    @Override
    public void init(DirectedWeightedGraph g) {
        gr  = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return gr;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return  new MyGraph(gr);
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        GsonBuilder b = new GsonBuilder();
        b.setPrettyPrinting();
        Gson g = b.create();
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
