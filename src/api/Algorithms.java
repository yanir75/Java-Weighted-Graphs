package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.NodeList;

import java.util.*;

public class Algorithms implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph graph;
    // https://he.wikipedia.org/wiki/%D7%90%D7%9C%D7%92%D7%95%D7%A8%D7%99%D7%AA%D7%9D_%D7%97%D7%99%D7%A4%D7%95%D7%A9_A*
    // https://stackabuse.com/graphs-in-java-a-star-algorithm/
    // https://he.wikipedia.org/wiki/%D7%90%D7%9C%D7%92%D7%95%D7%A8%D7%99%D7%AA%D7%9D_%D7%93%D7%99%D7%99%D7%A7%D7%A1%D7%98%D7%A8%D7%94

    @Override
    public void init(DirectedWeightedGraph g) {
        graph = g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return graph;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return  new MyGraph(graph);
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    public double findShortestPath(int src, int dest){
        PriorityQueue<NodeData> nodes = new PriorityQueue<>((a,b)-> (int) (a.getWeight() - b.getWeight()));
        Iterator<NodeData> iter = graph.nodeIter();
        while (iter.hasNext()) {
            NodeData node = iter.next();
            if(node.getKey() == src){
                node.setWeight(0);
            }
            nodes.add(node);
        }
        nodes.comparator();
        return 0;
    }
    public void setFather(NodeData n,int father){
        n.setTag(father);
    }
    public void setBlack(NodeData n,boolean b){
        n.setInfo(""+b);
    }

    private void updateNodes(NodeData parent){

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
