package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Algorithms implements DirectedWeightedGraphAlgorithms {
    private MyGraph graph;
    // https://he.wikipedia.org/wiki/%D7%90%D7%9C%D7%92%D7%95%D7%A8%D7%99%D7%AA%D7%9D_%D7%97%D7%99%D7%A4%D7%95%D7%A9_A*
    // https://stackabuse.com/graphs-in-java-a-star-algorithm/
    // https://he.wikipedia.org/wiki/%D7%90%D7%9C%D7%92%D7%95%D7%A8%D7%99%D7%AA%D7%9D_%D7%93%D7%99%D7%99%D7%A7%D7%A1%D7%98%D7%A8%D7%94

    @Override
    public void init(DirectedWeightedGraph g) {
        graph = (MyGraph) g;
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
        return findShortestPath(src, dest).keySet().toArray(new Double[0])[0];
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        HashMap<Double, List<NodeData>> path =  findShortestPath(src, dest);
        return path.get(path.keySet().toArray(new Double[0])[0]);
    }

    /**
     *
     * @param src
     * @param dest
     * @return
     */
    private HashMap<Double, List<NodeData>> findShortestPath(int src, int dest){
        HashMap<Double, List<NodeData>> path = new HashMap<>();
        PriorityQueue<NodeData> nodes = new PriorityQueue<>((a,b)-> (int) (a.getWeight() - b.getWeight()));
        Iterator<NodeData> iter = graph.nodeIter();
        while (iter.hasNext()) {
            NodeData node = iter.next();
            if(node.getKey() != src){
                node.setWeight(Double.MAX_VALUE);
            }
            else{
                node.setWeight(0);
                nodes.add(node);
            }
            node.setInfo("WHITE");
        }
        //STEPS:
        // 1 -> while is not empty
        while(!nodes.isEmpty()){
            // 2 -> go to the first node, the one with the least weight
            Node currNode = (Node) nodes.poll();
            // 3 -> go over all his children(through Edges)
            if(currNode != null && currNode.getEdges() != null && currNode.getInfo().equals("WHITE")) {
                Double[] children = currNode.getEdges().keySet().toArray(new Double[0]);
                // 4 -> update their info and the weight if needed.
                updateNodes(nodes, currNode, children);
                // 5 -> set node to be black
                currNode.setInfo("BLACK");
            }
            // 6 -> go overt to 1.
        }
        List<NodeData> shortestPath = new ArrayList<>();
        shortestPath.add(this.graph.getNode(dest));
        while(shortestPath.get(0).getKey() != src){
            NodeData currNode = shortestPath.get(0);
            shortestPath.add(0, this.graph.getNode(currNode.getTag()));
        }
        double shortestWeight = this.graph.getNode(dest).getWeight();
        path.put(shortestWeight, shortestPath);
        resetWeightsInfoFather();
        return path;
    }


    /**
     *
     * @param n
     * @param father
     */
    private void setFather(NodeData n,int father){
        n.setTag(father);
    }


    /**
     *
     */
    private void resetWeightsInfoFather(){
        Iterator<NodeData> iter = this.graph.nodeIter();
        while (iter.hasNext()) {
            NodeData node = iter.next();
            node.setWeight(Double.MAX_VALUE);
            node.setInfo("");
            setFather(node, -1);
        }
    }

    /**
     *
     * @param nodes
     * @param parent
     * @param keys
     */
    private void updateNodes(PriorityQueue<NodeData> nodes, Node parent, Double[] keys){
        double parentWeight = parent.getWeight();
        for(Double k: keys){
            EdgeData currEdge = parent.getEdges().get(k);
            double edgeWeight = currEdge.getWeight();
            int dest = currEdge.getDest();
            NodeData child = this.graph.getNode(dest);
            double childWeight = child.getWeight();
            if(parentWeight + edgeWeight < childWeight){
                child.setWeight(parentWeight + edgeWeight);
                setFather(child, parent.getId());
            }
            nodes.add(child);
        }
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

    public static void main(String[]args){
        ParseData pd = new ParseData("C:\\Users\\netan\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\G1.json");
//        System.out.println(pd.getEdges().values());
//        System.out.println();
//        System.out.println(pd.getNodes().values());
        DirectedWeightedGraph g = new MyGraph(pd.getNodes(), pd.getEdges());
        DirectedWeightedGraphAlgorithms algo = new Algorithms();
        algo.init(g);
        System.out.println(algo.shortestPathDist(1,7));
        System.out.println(algo.shortestPath(1,7));
    }
}
