package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Algorithms implements DirectedWeightedGraphAlgorithms {
    private MyGraph graph;
    private double minWeight = Double.MAX_VALUE;
    private List<NodeData> path;

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
        findShortestPath(src, dest);
        return this.minWeight;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        findShortestPath(src, dest);
        return this.path;
    }

    /**
     *
     * @param src
     * @param dest
     * @return
     */
    private void findShortestPath(int src, int dest){
        this.path = new ArrayList<>();
        this.minWeight = Double.MAX_VALUE;
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
        // load the shortest path.
        List<NodeData> shortestPath = new ArrayList<>();
        shortestPath.add(this.graph.getNode(dest));
        while(shortestPath.get(0).getKey() != src){
            NodeData currNode = shortestPath.get(0);
            shortestPath.add(0, this.graph.getNode(currNode.getTag()));
        }
        this.minWeight = this.graph.getNode(dest).getWeight();
        resetWeightsInfoFather();
    }



    /**
     * This method is being called by findShortestPath each time findShortestPath pops out a Node.<p>
     * The purpose of this method is to update the weights of all the children Nodes and to update their father pointer.<p>
     * It iterates through all the @keys(children) and updates only the Nodes that the update of the weight will
     * be smaller than the original weight.<p>
     * It updates the weight and the father of a Node.
     * @param nodes - the Priority Queue that contains all the nodes that needs to be checked.
     * @param parent - the Node that is a father of all the nodes in @keys.
     * @param keys - an array of keys(Double) represents all the children of the @parent.
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


    /**
     * This method recevies NodaData @node and int @father and sets the Tag of @node to be @father.
     * @param node - The NodeData that's need to be set.
     * @param father - the id of the NodeData that represents the father.
     */
    private void setFather(NodeData node, int father){
        node.setTag(father);
    }


    /**
     * This method is activated after the ShortestPath method is finished.<p>
     * It iterates through all the nodes of the graph and resets the data that ShortestPath changed.<p>
     * The method resets:
     * <li>The Weight of each node to -> very big number.
     * <li>The Info of each node to -> "".
     * <li>The father of each node to -> -1.
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
