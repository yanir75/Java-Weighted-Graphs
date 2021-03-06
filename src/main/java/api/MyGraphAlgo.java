package api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MyGraphAlgo implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph graph;
    private int connected = -1;//-1 unknown, 0 no, 1 yes
    private int mc;

    /**
     * Choose the graph you will perform your algorithms on.
     */
    @Override
    public void init(DirectedWeightedGraph g) {
        if (g != null) {
            this.graph = g;
            this.mc = this.graph.getMC();
        }

    }

    /**
     * Returns the graph you are currently performing algorithms on.
     *
     * @return
     */
    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    /**
     * Returns a deep copy of the current graph you are working on.
     *
     * @return
     */
    @Override
    public DirectedWeightedGraph copy() {
        return new MyGraph(this.graph);
    }

    @Override
    /** Checks if there is a two-sided route between every pair of vertexes.
     * source https://www.techiedelight.com/check-given-graph-strongly-connected-not/
     * @return true if so false otherwise.
     */
    public boolean isConnected() {
        if (this.mc == this.graph.getMC() && this.connected != -1) {
            if (this.connected == 1)
                return true;
            else
                return false;
        }
        this.mc = this.graph.getMC();
        boolean isConnected = isStronglyConnected();
        if (isConnected)
            this.connected = 1;
        else
            this.connected = 0;
        return isStronglyConnected();
    }

    /**
     * This is a simple DFS algorithm on the graph
     * The DFS will change every node it visits to true.
     *
     * @param visited hashmap of the visitor vertexes
     * @param gra     on which graph to perform it
     */
    private void DFS(Queue<Integer> keys, HashSet<Integer> visited, DirectedWeightedGraph gra) {
        // mark current node as visited
        while (!keys.isEmpty() && visited.size() != gra.nodeSize()) {
            visit(keys, keys.poll(), visited, gra);
        }
    }

    private void visit(Queue keys, int key, HashSet<Integer> visited, DirectedWeightedGraph gra) {
        visited.add(key);
        Iterator<EdgeData> iter = gra.edgeIter(key);
        // do for every edge (v, u)
        while (iter.hasNext()) {
            int dest = iter.next().getDest();
            if (!visited.contains(dest)) {
                keys.add(dest);
            }
        }
    }

    /**
     * This function switch the source and destination of all the edges and returns a new graph.
     *
     * @return
     */
    private DirectedWeightedGraph reverse() {
        HashMap<Integer, Node> Nodes = new HashMap<>();
        Iterator<NodeData> iterNodes = this.graph.nodeIter();
        while (iterNodes.hasNext()) {
            NodeData n = iterNodes.next();
            Nodes.put(n.getKey(), new Node(n.getLocation().x(), n.getLocation().y(), n.getKey()));
        }
        Iterator<EdgeData> iterEdges = this.graph.edgeIter();
        int count = 0;
        while (iterEdges.hasNext()) {
            count++;
            EdgeData e = iterEdges.next();
            Edge en = new Edge(e.getDest(), e.getSrc(), e.getWeight());
            Node n = Nodes.get(en.getSrc());
            n.getEdges().put(en.getDest(), en);
        }
        return new MyGraph(Nodes, count);

    }

    /**
     * Checks if there is a two sided route between every pair of vertexes.
     *
     * @return
     */
    private boolean isStronglyConnected() {
        HashSet<Integer> visited = new HashSet<>();
        Queue<Integer> keys = new LinkedList<Integer>();
        int key = -1;
        Iterator<NodeData> iter = graph.nodeIter();
        if (iter.hasNext()) {
            key = iter.next().getKey();
        }
        if (key == -1)
            return false;
        keys.add(key);
        // run a DFS starting at `v`
        this.DFS(keys, visited, graph);

        // If DFS traversal doesn't visit all vertices,
        // then the graph is not strongly connected
        if (visited.size() != graph.nodeSize())
            return false;
        visited = new HashSet<>();

        // Reverse the direction of all edges in the directed graph
        DirectedWeightedGraph g = this.reverse();
        // create a graph from reversed edges

        // Again run a DFS starting at `v`
        keys = new LinkedList<Integer>();
        keys.add(key);
        DFS(keys, visited, g);

        // If DFS traversal doesn't visit all vertices,
        // then the graph is not strongly connected
        if (visited.size() != this.graph.nodeSize())
            return false;
        // if a graph "passes" both DFSs, it is strongly connected
        return true;
    }

    /**
     * This function will return the shortest path distance (sum of all the edges in the route) between 2 edges.
     * This algorithm will be utilizing djikstra algorithm for finding the shortest route between two nodes.
     * Here is a video to illustrate https://www.youtube.com/watch?v=CerlT7tTZfY
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        return djikstra(src, dest);
    }

    /**
     * This function will return the shortest path list (list of all the nodes in the route) between 2 edges.
     * This algorithm will be utilizing djikstra algorithm for finding the shortest route between two nodes.
     * Here is a video to illustrate https://www.youtube.com/watch?v=CerlT7tTZfY
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        HashMap<Integer, father> s = djikstra_path(src, dest);
        if (s == null)
            return null;
        List<NodeData> path = new LinkedList<>();
        path.add(this.graph.getNode(dest));
        NodeData n = this.graph.getNode(src);
        int key = s.get(dest).prev;
        while (path.get(0) != n) {
            path.add(0, this.graph.getNode(key));
            key = s.get(key).prev;
        }
        return path;
    }

    /**
     * This algorithm will find the center of the graph,center of a graph is the node with the min(max(distance(u,v))).
     * Meaning the longest distance of a node is shorter than the longest distance of all the other nodes.
     * This algorithm will be utilized by performing djistra algorithm on all the nodes and will return the one with the shortest path.
     *
     * @return
     */
    @Override
    public NodeData center() {
        Iterator<NodeData> iter = this.graph.nodeIter();
        double max = Double.MAX_VALUE;
        int id = 0;
        while (iter.hasNext()) {
            int key = iter.next().getKey();
            double weight = djikstra(key);
            if (weight < max) {
                max = weight;
                id = key;
            }
        }
        return this.graph.getNode(id);

    }

    public class trio {
        int from;
        int to;
        double weight;

        public trio(int f, int t, double w) {
            from = f;
            to = t;
            weight = w;
        }

    }

    public class father {
        int prev;
        double weight;

        public father(int t, double w) {
            prev = t;
            weight = w;
        }

    }

    /**
     * Djikstra algorithm for finding the max cost path between 2 nodes using priority queue.
     * https://www.youtube.com/watch?v=CerlT7tTZfY
     *
     * @param src
     * @return
     */
    public double djikstra(int src) {
        HashSet<Integer> g = new HashSet<>();
        PriorityQueue<father> prio = new PriorityQueue<>((o1, o2) -> {
            if (o1.weight == o2.weight)
                return 0;
            else if (o1.weight > o2.weight)
                return 1;
            else
                return -1;
        });
        prio.add(new father(src, 0));
        double max = 0;
        while (g.size() < this.graph.nodeSize() && !prio.isEmpty()) {
            father f = prio.poll();
            int dest = f.prev;
            if (!g.contains(dest)) {
                Iterator<EdgeData> iter = this.graph.edgeIter(dest);
                g.add(dest);
                if (f.weight > max) {
                    max = f.weight;
                }
                while (iter.hasNext()) {
                    EdgeData e = iter.next();
                    double weight = f.weight + e.getWeight();
                    prio.add(new father(e.getDest(), weight));
                }
            }
        }
        return max;
    }

    /**
     * Djistra algorithm for finding the cost of the shortest path between two nodes.
     * Using priority queue
     * https://www.youtube.com/watch?v=CerlT7tTZfY
     *
     * @param src
     * @param des
     * @return
     */
    public double djikstra(int src, int des) {
        HashMap<Integer, father> s = new HashMap<>();
        PriorityQueue<trio> PQ = new PriorityQueue<>((o1, o2) -> {
            if (o1.weight == o2.weight)
                return 0;
            else if (o1.weight > o2.weight)
                return 1;
            else
                return -1;
        });
        PQ.add(new trio(src, src, 0));
        while (s.size() < graph.nodeSize() && !PQ.isEmpty()) {
            trio t = PQ.poll();
            int dest = t.to;
            if (!s.containsKey(dest)) {
                Iterator<EdgeData> iter = this.graph.edgeIter(dest);
                s.put(dest, new father(t.from, t.weight));
                if (s.containsKey(des)) {
                    return t.weight;
                }
                while (iter.hasNext()) {
                    EdgeData e = iter.next();
                    double weight = t.weight + e.getWeight();
                    PQ.add(new trio(e.getSrc(), e.getDest(), weight));
                }
            }
        }
        return -1;
    }

    /**
     * Djikstra's algorithm for finding the shortest path between two nodes.
     * https://www.youtube.com/watch?v=CerlT7tTZfY
     *
     * @param src
     * @param des
     * @return
     */
    public HashMap<Integer, father> djikstra_path(int src, int des) {
        HashMap<Integer, father> s = new HashMap<>();
        PriorityQueue<trio> PQ = new PriorityQueue<>((o1, o2) -> {
            if (o1.weight == o2.weight)
                return 0;
            else if (o1.weight > o2.weight)
                return 1;
            else
                return -1;
        });
        PQ.add(new trio(src, src, 0));
        while (s.size() < graph.nodeSize() && !PQ.isEmpty()) {
            trio t = PQ.poll();
            int dest = t.to;
            if (!s.containsKey(dest)) {
                Iterator<EdgeData> iter = this.graph.edgeIter(dest);
                s.put(dest, new father(t.from, t.weight));
                if (s.containsKey(des)) {
                    return s;
                }
                double weight = t.weight;
                while (iter.hasNext()) {
                    EdgeData e = iter.next();
                    weight += e.getWeight();
                    PQ.add(new trio(e.getSrc(), e.getDest(), weight));
                }
            }
        }
        return null;
    }

    /**
     * Greedy algorithm to find the shortest path between all the nodes in a given city.
     *
     * @param cities
     * @return
     */
    public List<NodeData> findRoute(List<NodeData> cities) {
        if (cities == null || cities.size() == 0)
            return null;
        if (cities.size() == 1) {
            return cities;
        }
        List<NodeData> TSPath = new ArrayList<>();
        HashSet<Integer> route = new HashSet<>();
        List<NodeData> copy = copy(cities);
        int last = cities.get(0).getKey();
        while (cities.size() > 1) {
            cities.remove(0);
            NodeData n1 = cities.get(0);
            if (!route.contains(n1.getKey())) {
                List<NodeData> part = shortestPath(last, n1.getKey());
                while (part!=null && part.size() > 0) {
                    NodeData g = part.remove(0);
                    if (TSPath.size() == 0 || TSPath.get(TSPath.size() - 1) != g)
                        TSPath.add(g);
                    route.add(g.getKey());
                }
                if(TSPath.size() -1 <0)
                    return null;
                last = TSPath.get(TSPath.size() - 1).getKey();
            }
        }
        for (NodeData nodeData : copy) {
            int key = nodeData.getKey();
            if (!route.contains(key))
                return new ArrayList<>();
        }
        return TSPath;
    }

    /**
     * In this TSP we will find the shortest path between all the nodes in a given city.
     * This will be done by using greedy algorithm(finding the best way from cities[0] to cities[1] and cities[1] to cities[2] and so on.
     *
     * @param cities
     * @return
     */
    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        if (cities == null) {
            return null;
        }
        boolean f=true;
        double min = Double.MAX_VALUE;
        List<NodeData> fin = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            List<NodeData> copy = copy(cities);
            swap(0, i, copy);
            copy = findRoute(copy);
            if (copy !=null && copy.size() >= cities.size()) {
                double cost = 0;
                for (int j = 0; j < copy.size() - 1; j++) {
                    EdgeData e = this.graph.getEdge(copy.get(j).getKey(), copy.get(j + 1).getKey());
                    if(e==null){
                        f=false;
                        break;
                    }
                    else {
                        cost = cost + e.getWeight();
                    }
                }
                if (cost < min && f) {
                    min = cost;
                    fin = copy;
                }
                f=true;
            }
        }
        return fin;
    }

    public void swap(int i, int j, List<NodeData> l) {
        NodeData n = l.get(i);
        l.add(i, l.remove(j));
        l.add(j, n);
    }

    public List<NodeData> copy(List<NodeData> l) {
        List<NodeData> copy = new LinkedList<>();
        for (int i = 0; i < l.size(); i++) {
            copy.add(new Node(l.get(i)));
        }
        return copy;
    }

    /**
     * Save the current graph to a file
     * Used https://www.w3schools.com/java/java_files_create.asp
     *
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
                System.err.println("File already exists.");
                return false;
            }
        } catch (IOException e) {
            System.err.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
        try {
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(this.graph.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            return true;
        } catch (IOException e) {
            System.err.println("An error occurred.");
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Load will load and initiate a graph from a given json.
     * Load will return true if the file has successfully loaded and false otherwise.
     *
     * @param file - file name of JSON file
     * @return
     */
    @Override
    public boolean load(String file) {
        boolean hasLoaded = false;
        try {
            ParseToGraph ptg = new ParseToGraph(file);
            this.init(new MyGraph(ptg.getNodes(), ptg.size));
            hasLoaded = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("\nJson file wasn't found!");
        }
        return hasLoaded;
    }

//    public static void main(String[] args) {
//        DirectedWeightedGraphAlgorithms algorithms = new MyGraphAlgo();
//        algorithms.init(new graphGen().generate_connected_graph(20));
//        algorithms.getGraph().removeNode(9);
//        List<NodeData> c = new LinkedList<>();
//        Iterator<NodeData> i = algorithms.getGraph().nodeIter();
//        while (i.hasNext())
//            c.add(i.next());
//        c = algorithms.tsp(c);
//        System.out.println("h");
//    }
}
