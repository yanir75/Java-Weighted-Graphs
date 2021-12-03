package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Algorithms implements DirectedWeightedGraphAlgorithms {
    private MyGraph graph;
    private double minWeight = Double.MAX_VALUE;
    private List<NodeData> path;
    private int curr_src = -1;
    private int curr_dest = -1;

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
    /**
     *
     */
    public boolean isConnected() {
        return isStronglyConnected();
    }

    /**
     *
     * @param key
     * @param visited
     * @param gra
     */
    private void DFS(int key, HashMap<Integer, Boolean> visited, DirectedWeightedGraph gra) {
        // mark current node as visited
        visited.replace(key, true);
        Iterator<EdgeData> iter = gra.edgeIter(key);
        // do for every edge (v, u)
        while (iter.hasNext()) {
            int dest = iter.next().getDest();
            if (!visited.get(dest)) {
                DFS(dest, visited, gra);
            }
        }
    }

    /**
     *
     * @return
     */
    private DirectedWeightedGraph reverse() {
        HashMap<Integer, Node> Nodes = new HashMap<Integer, Node>();
        HashMap<Double, Edge> Edges = new HashMap<Double, Edge>();
        Iterator<NodeData> iterNodes = graph.nodeIter();
        while (iterNodes.hasNext()) {
            NodeData n = iterNodes.next();
            Nodes.put(n.getKey(), new Node(n.getLocation().x(), n.getLocation().y(), n.getKey()));
        }
        Iterator<EdgeData> iterEdges = graph.edgeIter();
        while (iterEdges.hasNext()) {

            EdgeData e = iterEdges.next();
            Edge en = new Edge(e.getDest(), e.getSrc(), e.getWeight());
            // check if  ()
            double key = e.getDest() + e.getSrc() * MyGraph.BIGNUMBER;
            Nodes.get(e.getDest()).getEdges().put(key, en);
            Edges.put(key, en);
        }
        return new MyGraph(Nodes, Edges);

    }

    /**
     *
     * @return
     */
    private boolean isStronglyConnected() {
        HashMap<Integer, Boolean> visited = new HashMap<Integer, Boolean>();

        int key = -1;
        Iterator<NodeData> iter = graph.nodeIter();
        while (iter.hasNext()) {
            key = iter.next().getKey();
            visited.put(key, false);
        }
        if (key == -1)
            return false;

        // run a DFS starting at `v`
        this.DFS(key, visited, graph);

        // If DFS traversal doesn't visit all vertices,
        // then the graph is not strongly connected
        for (int i : visited.keySet()) {
            if (!visited.get(i))
                return false;
            else
                visited.replace(i, false);
        }


        // Reverse the direction of all edges in the directed graph
        DirectedWeightedGraph g = this.reverse();

        // create a graph from reversed edges

        // Again run a DFS starting at `v`
        DFS(key, visited, g);

        // If DFS traversal doesn't visit all vertices,
        // then the graph is not strongly connected
        for (int i : visited.keySet()) {
            if (!visited.get(i))
                return false;
        }
        // if a graph "passes" both DFSs, it is strongly connected
        return true;
    }


    @Override
    public double shortestPathDist(int src, int dest) {
        if(this.curr_src == src && this.curr_dest == dest){
            return this.minWeight;
        }
        findShortestPath(src, dest);
        return this.minWeight;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        if(this.curr_src == src && this.curr_dest == dest){
            return this.path;
        }
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
        this.curr_dest = dest;
        this.curr_src = src;
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
        this.path = new ArrayList<>();
        this.path.add(this.graph.getNode(dest));
        while(this.path.get(0).getKey() != src){
            NodeData currNode = this.path.get(0);
            this.path.add(0, this.graph.getNode(currNode.getTag()));
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
