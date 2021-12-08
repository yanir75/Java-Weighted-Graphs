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


    public MyGraph(){
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
        this.MC = 0;
    }

    public MyGraph(MyGraph g){
        this.nodes = g.getNodes();
        this.edges = g.getEdges();
        this.MC = 0;
    }

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
            private Iterator<Node> it = nodes.values().iterator();
            private int m = MC;
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
                MC++;
                m=MC;
            }
        };
        return iter;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        Iterator<EdgeData> iter= new Iterator<EdgeData>() {
            private Iterator<Edge> it = edges.values().iterator();
            private int m = MC;
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
                MC++;
                m=MC;
            }
        };
        return iter;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        Node x = nodes.get(node_id);
        HashMap<String, Edge> E = x.getEdges();
        Iterator<EdgeData> iter= new Iterator<EdgeData>() {

            private Iterator<Edge> it = E.values().iterator();
            private int m = MC;

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
                MC++;
                m=MC;
            }
        };
        return iter;
    }



    //MC++
    @Override
    public NodeData removeNode(int key) {
        if(!nodes.containsKey(key))
            throw new RuntimeException("Node does not exist");
        MC++;
        removeRelatedEdges(this.nodes.get(key));
        return this.nodes.remove(key);
    }

    private void removeRelatedEdges(Node n) {
        for (String i : n.getEdges().keySet()) {
            edges.remove(i);
        }
        for(int i :n.inEdges()){
            edges.remove(i+"-"+n.getKey());
        }
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        if(!edges.containsKey(src+"-"+dest))
            throw new RuntimeException("edge does not exist");
        String key = src +"-"+ dest;
        this.nodes.get(src).removeEdge(key);
        this.nodes.get(dest).inEdges().remove(src);
        MC++;
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
        Node nCopy = new Node(n, new Iterator<EdgeData>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public EdgeData next() {
                return null;
            }
        });
        MC++;
        nodes.put(n.getKey(),nCopy);
    }

    public HashMap<Integer, Node> getNodes() {
        return this.nodes;
    }

    public HashMap<String, Edge> getEdges() {
        return this.edges;
    }


    public String toStringEdges(){
        String output = '"'+"Edges"+'"'+": [\n";
        Iterator<EdgeData> iter= this.edgeIter();
        while (iter.hasNext()){
            EdgeData e = iter.next();
            output += "" + "{\n"+'"'+"src"+'"'+": "+e.getSrc()+",\n"+'"'+"w"+'"'+": "+e.getWeight()+",\n"+'"'+"dest"+'"'+": "+e.getDest()+"\n}" + ",\n";
        }
        return output.substring(0, output.length() -2) + "\n]";
    }

    public String toStringNodes(){
        String output = '"'+"Nodes"+'"'+": [\n";
        Iterator<NodeData> iter= this.nodeIter();
        while (iter.hasNext()){
            NodeData n = iter.next();
            output += "" + "{\n"+'"'+"pos"+'"'+": " +'"'+n.getLocation().x()+","+n.getLocation().y()+'"'+",\n"+'"'+"id"+'"'+": "+n.getKey()+"\n}" + ",\n";
        }
        return output.substring(0, output.length() -2) + "\n]";
    }
    @Override
    public String toString(){
        return "{\n"+toStringEdges()+",\n\n"+toStringNodes()+"\n}";
    }
}