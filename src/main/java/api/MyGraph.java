package api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

public class MyGraph implements DirectedWeightedGraph {
    private final HashMap<Integer, Node> nodes;
//    private final HashMap<String, Edge> edges;
    private int size=0;
    private int MC;


    public MyGraph(){
        this.nodes = new HashMap<>();
//        this.edges = new HashMap<>();
        this.MC = 0;
    }



    public MyGraph(HashMap<Integer, Node> Nodes, int size) {
        this.nodes = Nodes;
        this.size=size;
        this.MC = 0;
    }

    public MyGraph(HashMap<Integer, Node> Nodes){
        this.nodes = Nodes;
        this. size = nodes.size();
        this.MC = 0;
    }

    public MyGraph(DirectedWeightedGraph g){
        Iterator<NodeData>iter =g.nodeIter();
//        Iterator<EdgeData>e = g.edgeIter();
//        edges = new HashMap<>();
        nodes = new HashMap<>();
        while (iter.hasNext()){
            NodeData n= iter.next();
            Node x = new Node(n);
            nodes.put(n.getKey(),x);
        }
        Iterator<EdgeData>iter1 =g.edgeIter();
        int count=0;
        while (iter1.hasNext()){
            EdgeData e = iter1.next();
            Node n =nodes.get(e.getDest());
            n.addInEdge(e.getSrc());
            Node n1 =nodes.get(e.getSrc());
            Edge e1 = new Edge(e);
            n1.addEdge(e1);
            count++;
        }
        size=count;
        MC = g.getMC();
//        while (e.hasNext()){
//
//            Edge ed = new Edge(e.next());
//            String key = ed.getSrc() + "-" + ed.getDest();
////            edges.put(key,ed);
//        }
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
        return nodes.get(src).getEdges().get(dest);
    }

    @Override
    public void connect(int src, int dest, double w) {
        // check if needed ()
        if (!this.nodes.get(src).getEdges().containsKey(dest)) {
            this.nodes.get(src).getEdges().put(dest, new Edge(src, dest, w));
            this.MC++;
            size++;
        }
    }

    @Override
    public Iterator<NodeData> nodeIter() {

        Iterator<NodeData> iter= new Iterator<>() {
            private Iterator<Node> it = nodes.values().iterator();
            private int m = MC;
            private Node last;

            @Override
            public boolean hasNext() {
                if (m != MC)
                    throw new RuntimeException("MC counter was changed");
                return it.hasNext();
            }

            @Override
            public NodeData next() {
                if (m != MC)
                    throw new RuntimeException("MC counter was changed");
                last = it.next();
                return last;
            }

            @Override
            public void remove() {
                if (m != MC)
                    throw new RuntimeException("MC counter was changed");
                it.remove();
                removeRelatedEdges(last);
                MC++;
                m = MC;
            }
        };
        return iter;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        Iterator<EdgeData> it = new Iterator<>() {
            Iterator<Node> iter = nodes.values().iterator();

            Iterator<Edge> ed;
            private int m = MC;
            private Edge last;

            @Override
            public boolean hasNext() {
                if(iter.hasNext() && ed==null){
                    ed = iter.next().getEdges().values().iterator();
                }
                if(ed==null)
                    return false;
                if (m != MC)
                    throw new RuntimeException("MC counter was changed");
                if (ed.hasNext()) {
                    return ed.hasNext();
                }
                while (iter.hasNext() && !ed.hasNext()){
                    ed=iter.next().getEdges().values().iterator();
                }
                return ed.hasNext();
            }

            @Override
            public EdgeData next() {
                if (m != MC)
                    throw new RuntimeException("MC counter was changed");
                last=ed.next();
                return last;
            }

            @Override
            public void remove() {
                if (m != MC)
                    throw new RuntimeException("MC counter was changed");
                nodes.get(last.getDest()).inEdges().remove(last.getSrc());
                ed.remove();
                size--;
                MC++;
                m=MC;
            }

            @Override
            public void forEachRemaining(Consumer<? super EdgeData> action) {
                Iterator.super.forEachRemaining(action);
            }
        };
        return it;

    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        Node x = nodes.get(node_id);
        HashMap<Integer, Edge> E = x.getEdges();
        Iterator<EdgeData> iter= new Iterator<EdgeData>() {

            private Iterator<Edge> it = E.values().iterator();
            private int m = MC;
            Edge last;


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
                last=it.next();
                return last;
            }

            @Override
            public void remove() {
                if(m!=MC)
                    throw new RuntimeException("MC counter was changed");
                it.remove();
                nodes.get(last.getDest()).inEdges().remove(last.getSrc());
                //edges.remove(last.getSrc()+"-"+last.getDest());
                size--;
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
//        for (String i : n.getEdges().keySet()) {
//            edges.remove(i);
//        }
        for(int i :n.inEdges()){
            Node n1 = nodes.get(i);
            if(n1!=null){
            n1.getEdges().remove(n.getKey());}
        }
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        if(!nodes.get(src).getEdges().containsKey(dest))
            throw new RuntimeException("edge does not exist");
//        String key = src +"-"+ dest;
        this.nodes.get(dest).inEdges().remove(src);
        size--;
        MC++;
        return this.nodes.get(src).removeEdge(dest);
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return size;
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

//    public HashMap<String, Edge> getEdges() {
//        return this.edges;
//    }


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
