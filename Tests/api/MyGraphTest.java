package api;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class MyGraphTest {
    ParseToGraph ptg;

    {
        try {
            ptg = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\G1.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    DirectedWeightedGraph g = new MyGraph(ptg.getNodes(),ptg.getEdges());
    {
        try {
            ptg = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\G2.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    DirectedWeightedGraph g2 = new MyGraph(ptg.getNodes(),ptg.getEdges());
    {
        try {
            ptg = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\G3.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    DirectedWeightedGraph g3 = new MyGraph(ptg.getNodes(),ptg.getEdges());
    {
        try {
            ptg = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\10000Nodes.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    DirectedWeightedGraph g4 = new MyGraph(ptg.getNodes(),ptg.getEdges());
    @Test
    void getNode() {
        for(int i=0;i<17;i++){
            NodeData n = g.getNode(i);
            assertEquals(n.getKey(),i);
        }
    }

    @Test
    void getEdge() {
        for(int i=0;i<17;i++){
            for(int j=0;j<17;j++) {
                EdgeData e = g.getEdge(i,j);
                if(e!=null) {
                    assertEquals(e.getSrc(), i);
                    assertEquals(e.getDest(), j);
                }
            }
        }
    }


    @Test
    void connect() {
        for(int i=0;i<17;i++){
            for(int j=0;j<17;j++) {
                g.connect(i,j,1);

                }
            }
        for(int i=0;i<17;i++){
            for(int j=0;j<17;j++) {
                EdgeData e = g.getEdge(i,j);
                    assertEquals(e.getSrc(), i);
                    assertEquals(e.getDest(), j);

            }
        }
    }

    @Test
    void nodeIter() {
        nodeIt(g,g2,g3,g4);
    }
    void nodeIt(DirectedWeightedGraph ... graphes) {
        for (DirectedWeightedGraph g: graphes) {
            checkNodeIt(g);
        }
    }
    public static void checkNodeIt(DirectedWeightedGraph g){
        Iterator<NodeData> it =g.nodeIter();
        LinkedList<NodeData> n = new LinkedList<>();
        while (it.hasNext())
            n.add(it.next());
        int count=0;
        for(int i=0;i<g.nodeSize()*100;i++){
                NodeData n1 = g.getNode(i);
                if(n1!=null) {
                    assertTrue(n.contains(n1));
                    count++;
                }

        }
        assertEquals(count,n.size());
    }

    @Test
    void edgeIter() {
        edgeIt(g,g2,g3,g4);
    }

    void edgeIt(DirectedWeightedGraph ... graphes) {
        for (DirectedWeightedGraph g: graphes) {
            checkEdge(g);
        }
    }
    public static void checkEdge(DirectedWeightedGraph g){
        Iterator<EdgeData> it =g.edgeIter();
        LinkedList<EdgeData> e = new LinkedList<>();

        while (it.hasNext())
            e.add(it.next());
        int count=0;
        int max =maxNodeId(g);
        for(int i=0;i<=max;i++){
            for(int j=0;j<=max;j++) {
                EdgeData e1 = g.getEdge(i,j);
                if(e1!=null) {
                    assertTrue(e.contains(e1));
                    count++;
                }
            }
        }
        assertEquals(count,e.size());
    }
    public static int maxNodeId(DirectedWeightedGraph g){
        Iterator<NodeData> n = g.nodeIter();
        int max=0;
        while (n.hasNext()){
            int key = n.next().getKey();
            if(key>max)
                max=key;
        }
        return max;
    }

    @Test
    void testEdgeIter() {
    }

    @Test
    void removeNode() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getMC() {
    }

    @Test
    void addNode() {
    }

    @Test
    void getNodes() {
    }

    @Test
    void getEdges() {
    }

    @Test
    void toStringEdges() {
    }

    @Test
    void toStringNodes() {
    }

    @Test
    void testToString() {
    }
}