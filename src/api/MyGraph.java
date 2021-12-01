package api;

import java.util.HashMap;
import java.util.Iterator;

public class MyGraph implements DirectedWeightedGraph{
    @Override
    public NodeData getNode(int key) {
        return null;
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return edges.get(src + dest * BIGNUMBER);
    }

    @Override
    public void connect(int src, int dest, double w) {
        double key = src + dest * BIGNUMBER;
        if (!this.edges.containsKey(key)) {
            this.edges.put(key, new Edge(src, dest, w));
            this.MC++;
        }
    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return null;
    }

    private void removeRelatedEdges(Node n) {
        for (double i : n.getEdges().keySet()) {
            edges.remove(i);
        }
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        double key = src + dest * BIGNUMBER;
        this.nodes.get(src).removeEdge(key);
        return this.edges.remove(key);
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }

    @Override
    public void addNode(NodeData n) {
    }
}
