package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Algorithms implements DirectedWeightedGraphAlgorithms {
    private MyGraph graph;
    private double minWeight = Double.MAX_VALUE;
    private List<NodeData> path;
    private int curr_src = -1;
    private int curr_dest = -1;

    /**
     * Choose the graph you will perform your algorithms on
     */
    @Override
    public void init(DirectedWeightedGraph g) {
        graph = (MyGraph) g;
    }

    /**
     * Returns the graph you are working on
     * @return
     */
    @Override
    public DirectedWeightedGraph getGraph() {
        return graph;
    }

    /**
     * Returns a deep copy of the current graph you are working on
     * @return
     */
    @Override
    public DirectedWeightedGraph copy() {
        return new MyGraph(graph);
    }

    @Override
    /** Checks if there is a route between every pair of vertexes
     * source https://www.techiedelight.com/check-given-graph-strongly-connected-not/
     * @return true if so false otherwise.
     */
    public boolean isConnected() {
        return isStronglyConnected();
    }

    /**
     * This is a simple DFS algorithm on the graph
     * @param key Starting vertex
     * @param visited hashmap of the visiter vertexes
     * @param gra on which graph to perform it
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
     * This function switch the source and destination of all the edges and returns a new graph.
     * @return
     */
    private DirectedWeightedGraph reverse() {
        HashMap<Integer, Node> Nodes = new HashMap<Integer, Node>();
        HashMap<String, Edge> Edges = new HashMap<String, Edge>();
        Iterator<NodeData> iterNodes = graph.nodeIter();
        while (iterNodes.hasNext()) {
            NodeData n = iterNodes.next();
            Nodes.put(n.getKey(), new Node(n.getLocation().x(), n.getLocation().y(), n.getKey()));
        }
        Iterator<EdgeData> iterEdges = graph.edgeIter();
        while (iterEdges.hasNext()) {

            EdgeData e = iterEdges.next();
            Edge en = new Edge(e.getDest(), e.getSrc(), e.getWeight());
            String key = e.getDest() + "-" + e.getSrc();
            Nodes.get(e.getDest()).getEdges().put(key, en);
            Edges.put(key, en);
        }
        return new MyGraph(Nodes, Edges);

    }

    /** Checks if there is a route between every pair of vertexes.
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
     * This method finds the shortest path between @src and @dest.<p>
     * The shortest path is the path that has the minimum total weight from all other paths if there are.<p>
     * The idea of this method is based on <b>Dijkstra Algorithm</b> (which uses BFS Algorithm).<p>
     * The method is implemented with a PriorityQueue and the <b>key of the priority</b> is the weights of the
     * Nodes by ascending order.<p>
     * <b>Steps of the Algorithm:</b><ul>
     * 1) initialize the PriorityQueue and the priority.<p>
     * 2) add the @src Node to the queue.<p>
     * 3) while the queue is not empty:<p>
     * 4) poll() - remove the first Node and keep it in a temporary Node @currNode.<p>
     * 5) if @currNode isn't null and @currNode has children and @currNode is "WHITE":<p>
     * 5.1) call the updateNodes(nodes, currNode, children) method.
     *      <li> @nodes = the Priority queue
     *      <li> @currNode = the Node that is the father.
     *      <li> @an array of all the children that @currNode has (as a keys of the Edges HashMap of @currNode).<p>
     * 5.2) set the color of @currNode to be black -> so we won't add him again to the queue.<p>
     * 6) go back to step <b>3</b>.<p>
     * 7) check if we found a path.<p>
     * 8) if found, load it to @this.path and set @this.minWeight to be the desired weight.<p>
     * 9) call resetWeightsInfoFather() to resets weights/Info/Father.<p>
     * @param src - the source Node that we start from.
     * @param dest - the destination Node that we want to reach.
     * @see   <a href="https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm">Dijkstra Algorithm</a>
     * @see   <a href="https://en.wikipedia.org/wiki/Breadth-first_search">Breadth-first search(BFS) Algorithm</a>
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
                String[] children = currNode.getEdges().keySet().toArray(new String[0]);
                // 4 -> update their info and the weight if needed.
                updateNodes(nodes, currNode, children);
                // 5 -> set node to be black
                currNode.setInfo("BLACK");
            }
            // 6 -> go overt to 1.
        }
        // check if we found a path.
        if(this.graph.getNode(dest) == null || this.graph.getNode(dest).getWeight() == Double.MAX_VALUE){
            this.minWeight = -1;
        }
        else { // load the shortest path.
            this.path = new ArrayList<>();
            this.path.add(this.graph.getNode(dest));
            while (this.path.get(0).getKey() != src) {
                NodeData currNode = this.path.get(0);
                this.path.add(0, this.graph.getNode(currNode.getTag()));
            }
            this.minWeight = this.graph.getNode(dest).getWeight();
        }
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
    private void updateNodes(PriorityQueue<NodeData> nodes, Node parent, String[] keys){
        double parentWeight = parent.getWeight();
        for(String k: keys){
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


    /**
     * I don't want to brag or anything. But I copied from my own github
     * https://github.com/yanir75/Assignment-C-1/blob/main/my_mat.c
     * This function uses floyed algorithm to find the center.
     * Center vertex is defined as the vertex with the shortest max distance from all the vertexes
     * Shortest max distance means take all the distances of the vertex take its maximum then the vertex whose maximum is the lowest is the center.
     * @return
     */
    @Override
    public NodeData center() {
        HashMap<Integer,Distance> cen = new HashMap<>();
        Iterator<EdgeData> iter = graph.edgeIter();

        while (iter.hasNext()){
            EdgeData e = iter.next();
            cen.put(e.getSrc(),new Distance(e.getSrc(),e.getDest(),e.getWeight()));
        }
        Iterator<NodeData> iter1 = graph.nodeIter();
        Iterator<NodeData> iter2 = graph.nodeIter();
        Iterator<NodeData> iter3 = graph.nodeIter();

        while (iter1.hasNext())
        {
            while (iter2.hasNext())
            {
                while (iter3.hasNext())
                {       NodeData n1=iter1.next();
                        NodeData n2=iter2.next();
                        NodeData n3=iter3.next();

                        setDist(n2,n3,cen,min(n2,n3,n1,cen));
                }
                iter3 = graph.nodeIter();
            }
            iter2 = graph.nodeIter();
        }
        iter1 = graph.nodeIter();
        iter2 = graph.nodeIter();
        double max = Double.MAX_VALUE;
        NodeData bestNode;
        if(iter1.hasNext())
        bestNode= iter1.next();
        else
            return null;
            while (iter2.hasNext())
            {
                NodeData n =iter2.next();
                double maxDist = cen.get(n.getKey()).getMax();
                if(maxDist==0)
                {
                    return n;
                }
                else if(max>maxDist){
                    max=maxDist;
                    bestNode=n;
                }
        }
        return bestNode;
    }
    public double getDist(NodeData n,NodeData n1, HashMap<Integer,Distance>dist){
        return dist.get(n.getKey()).getDist(n1.getKey());
    }
    public void setDist(NodeData n,NodeData n1, HashMap<Integer,Distance>dist,double distance){
        dist.get(n.getKey()).setDist(n1.getKey(),distance);
    }

    /**
     * first time i can say it copied from myself guys I am proud of it.
     * (Netanel don't delete this)
     * https://github.com/yanir75/Assignment-C-1/blob/main/my_mat.c
     * @param i
     * @param j
     * @param k
     * @param dist
     * @return
     */
    private double min(NodeData i, NodeData j, NodeData k,HashMap<Integer,Distance> dist) {
        if(i==j)
            return 0.0;
        if(getDist(i,j,dist)==0 &&(getDist(i,k,dist)==0 || getDist(k,j,dist)==0))
        {
            return 0.0;
        }
        if(getDist(i,k,dist)==0|| getDist(k,j,dist)==0)
            return getDist(i,j,dist);

        if(getDist(i,j,dist) == 0)
            return getDist(i,k,dist)+getDist(k,j,dist);

        if(getDist(i,k,dist)+getDist(k,j,dist) < getDist(i,j,dist))
            return getDist(i,k,dist)+getDist(k,j,dist);

        return getDist(i,j,dist);


    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    /**
     * Save the current graph to a file
     * Used https://www.w3schools.com/java/java_files_create.asp
     * @param file - the file name (may include a relative path).
     * @return
     */
    @Override
    public boolean save(String file) {
        try {
            File myObj = new File(file);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(graph.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            return true;
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean load(String file) {
        return false;
    }

    public static void main(String[]args){
        ParseData pd = new ParseData("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\G1.json");
        MyGraph g = new MyGraph(pd.getNodes(), pd.getEdges());
        DirectedWeightedGraphAlgorithms algo = new Algorithms();
        algo.init(g);
//        System.out.println(g.toStringEdges());
       // System.out.println(algo.center().toString());
//        System.out.println(g.toStringNodes());
//        System.out.println(algo.shortestPathDist(1,7));
//        System.out.println(algo.shortestPath(1,7));
//        System.out.println(algo.isConnected());
        algo.save("test.json");
    }
}
