package api;

import javax.imageio.ImageTranscoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyGraph implements DirectedWeightedGraph {
    public static double BIGNUMBER = (double) Integer.MAX_VALUE * Integer.MAX_VALUE + 1.0;
    private final HashMap<Integer, Node> nodes;
    private final HashMap<String, Edge> edges;
    private int MC;

    public MyGraph(HashMap<Integer, Node> Nodes, HashMap<String, Edge> Edges) {
        this.nodes = Nodes;
        this.edges = Edges;
        this.MC = 0;
    }
    public MyGraph(DirectedWeightedGraph g){
        Iterator<NodeData>iter =g.nodeIter();
        Iterator<EdgeData>e = g.edgeIter();
        edges = new HashMap<String,Edge>();
        nodes = new HashMap<Integer,Node>();
        while (iter.hasNext()){
            NodeData n= iter.next();
            Node x = new Node(n,g.edgeIter(n.getKey()));
            nodes.put(n.getKey(),x);
        }
        while (e.hasNext()){

            Edge ed = new Edge(e.next());
            String key = ed.getSrc() + "-" + ed.getDest();
            edges.put(key,ed);
        }
    }


    @Override
    public NodeData getNode(int key) {
        return nodes.get(key);
    }

    // src+dest*bignumber
    // src = (src+dest*bignumber)%x
    // dest = (src+dest*bignumber)/x
    @Override
    public EdgeData getEdge(int src, int dest) {
        return edges.get(src+"-"+dest);
    }

    @Override
    public void connect(int src, int dest, double w) {
        // check if needed ()
        String key = src +"-" +dest;
        if (!this.edges.containsKey(key)) {
            this.edges.put(key, new Edge(src, dest, w));
            this.MC++;
        }
    }

    @Override
    public Iterator<NodeData> nodeIter() {

        Iterator<NodeData> iter= new Iterator<NodeData>() {
            Iterator<Node> it = nodes.values().iterator();
            int m = MC;
            @Override
            public boolean hasNext() {
                if(m!=MC)
                    throw new RuntimeException("MC counter was changed");
                return it.hasNext();
            }

            @Override
            public NodeData next() {
                if(m!=MC)
                    throw new RuntimeException("MC counter was changed");
                return it.next();
            }

            @Override
            public void remove() {
                if(m!=MC)
                    throw new RuntimeException("MC counter was changed");
                it.remove();
            }
        };
        return iter;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        Iterator<EdgeData> iter= new Iterator<EdgeData>() {
            Iterator<Edge> it = edges.values().iterator();
            int m = MC;
            @Override
            public boolean hasNext() {
                if(m!=MC)
                    throw new RuntimeException("MC counter was changed");
                return it.hasNext();
            }

            @Override
            public EdgeData next() {
                if(m!=MC)
                    throw new RuntimeException("MC counter was changed");
                return it.next();
            }

            @Override
            public void remove() {
                if(m!=MC)
                    throw new RuntimeException("MC counter was changed");
                it.remove();
            }
        };
        return iter;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        Node x = nodes.get(node_id);
        HashMap<String, Edge> E = x.getEdges();
        Iterator<EdgeData> iter= new Iterator<EdgeData>() {

            Iterator<Edge> it = E.values().iterator();
            int m = MC;

            @Override
            public boolean hasNext() {
                if(m!=MC)
                    throw new RuntimeException("MC counter was changed");
                return it.hasNext();
            }

            @Override
            public EdgeData next() {
                if(m!=MC)
                    throw new RuntimeException("MC counter was changed");
                return it.next();
            }

            @Override
            public void remove() {
                if(m!=MC)
                    throw new RuntimeException("MC counter was changed");
                it.remove();
            }
        };
        return iter;
    }



    //MC++
    @Override
    public NodeData removeNode(int key) {
        removeRelatedEdges(this.nodes.get(key));
        return this.nodes.remove(key);
    }

    private void removeRelatedEdges(Node n) {
        for (String i : n.getEdges().keySet()) {
            edges.remove(i);
        }
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        String key = src +"-"+ dest;
        this.nodes.get(src).removeEdge(key);
        return this.edges.remove(key);
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return edges.size();
    }

    @Override
    public int getMC() {
        return this.MC;
    }

    @Override
    public void addNode(NodeData n) {
    }

    public HashMap<Integer, Node> getNodes() {
        return this.nodes;
    }

    public HashMap<String, Edge> getEdges() {
        return this.edges;
    }


    public String toStringEdges(){
        String[] keys = this.edges.keySet().toArray(new String[0]);
        String output = "[";
        for(String k: keys){
            output += "" + this.edges.get(k) + ",";
        }
        return output.substring(0, output.length() -1) + "\n]";
    }

    public String toStringNodes(){
        return this.nodes.values().toString();
    }
}