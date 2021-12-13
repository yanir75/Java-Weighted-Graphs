package api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Algorithms implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph graph;
    private int connected =-1;//-1 unknown, 0 no, 1 yes
    private int mc;

    /**
     * Choose the graph you will perform your algorithms on
     */
    @Override
    public void init(DirectedWeightedGraph g) {
        if(g != null) {
            graph = g;
            mc = graph.getMC();
        }

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
        if(mc==graph.getMC() && connected!=-1)
        {if(connected==1)
            return true;
        else
            return false;
        }
        mc= graph.getMC();
        boolean con = isStronglyConnected();
        if(con)
            connected = 1;
        else
            connected = 0;
        return isStronglyConnected();
    }

    /**
     * This is a simple DFS algorithm on the graph
     * @param visited hashmap of the visitor vertexes
     * @param gra on which graph to perform it
     */
    private void DFS(Queue<Integer> keys, HashSet<Integer> visited, DirectedWeightedGraph gra) {
        // mark current node as visited
        while(!keys.isEmpty() && visited.size()!=gra.nodeSize())
        {
            visit(keys,keys.poll(),visited,gra);
        }
    }
    private void visit(Queue keys,int key, HashSet<Integer> visited, DirectedWeightedGraph gra){
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
     * @return
     */
    private DirectedWeightedGraph reverse() {
        HashMap<Integer, Node> Nodes = new HashMap<>();
        Iterator<NodeData> iterNodes = graph.nodeIter();
        while (iterNodes.hasNext()) {
            NodeData n = iterNodes.next();
            Nodes.put(n.getKey(), new Node(n.getLocation().x(), n.getLocation().y(), n.getKey()));
        }
        Iterator<EdgeData> iterEdges = graph.edgeIter();
        int count=0;
        while (iterEdges.hasNext()) {
            count++;
            EdgeData e = iterEdges.next();
            Edge en = new Edge(e.getDest(), e.getSrc(), e.getWeight());
            Node n = Nodes.get(en.getSrc());
            n.getEdges().put(en.getDest(), en);
        }
        return new MyGraph(Nodes, count);

    }

    /** Checks if there is a route between every pair of vertexes.
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
        if(visited.size()!=graph.nodeSize())
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
        if(visited.size()!=graph.nodeSize())
            return false;
        // if a graph "passes" both DFSs, it is strongly connected
        return true;
    }


    @Override
    public double shortestPathDist(int src, int dest) {
        return djikstra(src,dest);
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        HashMap<Integer,father> s = djikstra_path(src,dest);
        if(s==null)
            return null;
        List<NodeData>  path = new LinkedList<>();
        path.add(graph.getNode(dest));
        NodeData n = graph.getNode(src);
        int key = s.get(dest).prev;
        while (path.get(0)!=n){
            path.add(0,graph.getNode(key));
            key = s.get(key).prev;
        }
        return path;
    }


    @Override
    public NodeData center() {
        Iterator<NodeData> iter = graph.nodeIter();
        double max = Double.MAX_VALUE;
        int id = 0;
        while (iter.hasNext())
        {
            int key = iter.next().getKey();
            double weight = djikstra(key);
            if(weight<max) {
                max = weight;
                id=key;
            }
        }
        return graph.getNode(id);

    }
    public class trio {
        int from;
        int to;
        double weight;

        public trio(int f,int t, double w){
            from=f;
            to=t;
            weight=w;
        }

    }
    public class father {
        int prev;
        double weight;

        public father(int t, double w){
            prev=t;
            weight=w;
        }

    }

    public double djikstra(int src){
        HashSet<Integer> g =new HashSet<>();
        PriorityQueue<father> prio = new PriorityQueue<>((o1, o2) -> {
            if (o1.weight == o2.weight)
                return 0;
            else if (o1.weight > o2.weight)
                return 1;
            else
                return -1;
        });
        prio.add(new father(src,0));
        double max =0;
        while (g.size()<graph.nodeSize() && !prio.isEmpty()) {
            father f = prio.poll();
            int dest = f.prev;
            if(!g.contains(dest)){
                Iterator<EdgeData> iter = graph.edgeIter(dest);
                g.add(dest);
                if(f.weight> max){
                    max=f.weight;
                }
                while (iter.hasNext()) {
                    EdgeData e = iter.next();
                    double weight =f.weight+ e.getWeight();
                    prio.add(new father(e.getDest(),weight));
                }
            }
        }
        return max;
    }


    public double djikstra(int src,int des) {
        HashMap<Integer, father> s = new HashMap<>();
//        HashSet<Integer> s = new HashSet<>();
        PriorityQueue<trio> prio = new PriorityQueue<>((o1, o2) -> {
            if (o1.weight == o2.weight)
                return 0;
            else if (o1.weight > o2.weight)
                return 1;
            else
                return -1;
        });
        prio.add(new trio(src, src, 0));
        while (s.size() < graph.nodeSize() && !prio.isEmpty()) {
            trio t = prio.poll();
            int dest = t.to;
            if (!s.containsKey(dest)) {
                Iterator<EdgeData> iter = graph.edgeIter(dest);
                s.put(dest, new father(t.from, t.weight));
                if (s.containsKey(des)) {
                    return t.weight;
                }
                while (iter.hasNext()) {
                    EdgeData e = iter.next();
                    double weight = t.weight+ e.getWeight();
                    prio.add(new trio(e.getSrc(), e.getDest(), weight));
                }
            }
        }
        return -1;
    }


    public HashMap<Integer,father> djikstra_path(int src,int des) {
        HashMap<Integer, father> s = new HashMap<>();
        PriorityQueue<trio> prio = new PriorityQueue<>((o1, o2) -> {
            if (o1.weight == o2.weight)
                return 0;
            else if (o1.weight > o2.weight)
                return 1;
            else
                return -1;
        });
        prio.add(new trio(src, src, 0));
        while (s.size() < graph.nodeSize() && !prio.isEmpty()) {
            trio t = prio.poll();
            int dest = t.to;
            if (!s.containsKey(dest)) {
                Iterator<EdgeData> iter = graph.edgeIter(dest);
                s.put(dest, new father(t.from, t.weight));
                if (s.containsKey(des)) {
                    return s;
                }
                double weight = t.weight;
                while (iter.hasNext()) {
                    EdgeData e = iter.next();
                    weight += e.getWeight();
                    prio.add(new trio(e.getSrc(), e.getDest(), weight));
                }
            }
        }
        return null;
    }


    public List<NodeData> findRoute(List<NodeData> cities) {
        if(cities==null || cities.size()==0)
            return null;
        if(cities.size()==1){
            return cities;
        }
        List<NodeData> TSPath = new LinkedList<>();
        HashSet<Integer> route = new HashSet<>();
        int last = cities.get(0).getKey();
        while (cities.size()>1){
            cities.remove(0);
            NodeData n1 = cities.get(0);
            if(!route.contains(n1.getKey())) {
                List<NodeData> part = shortestPath(last, n1.getKey());
                while (part.size() > 0) {
                    NodeData g = part.remove(0);
                    TSPath.add(g);
                    route.add(g.getKey());
                }
                last=TSPath.get(TSPath.size()-1).getKey();
            }
        }
        return TSPath;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {

     return findRoute(cities);
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
            myWriter.write(graph.toString());
            myWriter.flush();
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            return true;
        } catch (IOException e) {
            System.err.println("An error occurred.");
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean load(String file) {
        boolean hasLoaded = false;
        try{
            ParseToGraph ptg = new ParseToGraph(file);
            this.graph = new MyGraph(ptg.getNodes(), ptg.size);
            hasLoaded = true;
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println("\nJson file wasn't found!");
        }
        return hasLoaded;
    }

    public static void main(String[]args){
        ParseToGraph pd = new ParseToGraph();
        try {
            pd = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\weighted-graphs\\data\\G1.json");
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println();
        }
        DirectedWeightedGraph g = new MyGraph(pd.getNodes(), pd.size);
        DirectedWeightedGraphAlgorithms algo = new Algorithms();
        algo.init(g);
        System.out.println(algo.isConnected());
////        g = new graphGen().generate_connected_graph(2);
//        algo.init(g);
//        DirectedWeightedGraph g1 = algo.copy();
//        long b = System.currentTimeMillis();
//        System.out.println(algo.center());
//        System.out.println(System.currentTimeMillis()-b);
//        algo.save("wow");
//        long b = System.currentTimeMillis();
//        System.out.println(algo.center());
//        System.out.println(System.currentTimeMillis()-b);
//        String f = g.toString();
//        algo.save("test");
//        algo.load("test");
//        String f1 = g.toString();
//        System.out.println(f.equals(f1));
//        long a = System.currentTimeMillis();
//        System.out.println(algo.center());
//        System.out.println(System.currentTimeMillis()-a);
////        algo.init(g);
////        System.out.println(g.toStringEdges());
//       // System.out.println(algo.center().toString());
////        System.out.println(g.toStringNodes());
////        System.out.println(algo.shortestPathDist(1,7));
////        System.out.println(algo.isConnected());
//        long a = System.currentTimeMillis();
//        System.out.println(algo.isConnected());
//        long b = System.currentTimeMillis();
//        System.out.println(b-a);
//        algo.getGraph().addNode(new Node(500,500,500));
//        a=System.currentTimeMillis();
//        System.out.println(algo.isConnected());
//        b=System.currentTimeMillis();
//        System.out.println(b-a);
    }
}
