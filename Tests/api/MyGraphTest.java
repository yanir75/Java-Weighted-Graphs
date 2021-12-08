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
            ptg = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\Test1.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    DirectedWeightedGraph g4 = new MyGraph(ptg.getNodes(),ptg.getEdges());
    @Test
    void getNode() {

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
                ptg = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\Test1.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        DirectedWeightedGraph g4 = new MyGraph(ptg.getNodes(),ptg.getEdges());
        getN(g,g2,g3,g4);
    }
    void getN(DirectedWeightedGraph ... graphes) {
        for (DirectedWeightedGraph g: graphes) {
            checkN(g);
        }
    }
    void checkN(DirectedWeightedGraph g){
        int max =maxNodeId(g);
        for(int i=0;i<=max;i++){
            NodeData n = g.getNode(i);
            assertEquals(n.getKey(),i);
        }
    }

    @Test
    void getEdge() {

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
                ptg = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\Test1.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        DirectedWeightedGraph g4 = new MyGraph(ptg.getNodes(),ptg.getEdges());
        getE(g,g2,g3,g4);
    }
    void getE(DirectedWeightedGraph ... graphes) {
        for (DirectedWeightedGraph g: graphes) {
            checkE(g);
        }
    }
    void checkE(DirectedWeightedGraph g){
        int max =maxNodeId(g);
        for(int i=0;i<=max;i++){
            for(int j=0;j<=max;j++) {
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
                ptg = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\Test1.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        DirectedWeightedGraph g4 = new MyGraph(ptg.getNodes(),ptg.getEdges());
        getCon(g,g2,g3,g4);

    }
    void getCon(DirectedWeightedGraph ... graphes) {
        for (DirectedWeightedGraph g: graphes) {
            checkCon(g);
        }
    }
    void checkCon(DirectedWeightedGraph g){
        int max =maxNodeId(g);
        for(int i=0;i<=max;i++){
            for(int j=0;j<=max;j++) {
                g.connect(i,j,1);

            }
        }
        for(int i=0;i<=max;i++){
            for(int j=0;j<=max;j++) {
                EdgeData e = g.getEdge(i,j);
                    assertEquals(e.getSrc(), i);
                    assertEquals(e.getDest(), j);

            }
        }
    }

    @Test
    void nodeIter() {

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
                ptg = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\Test1.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        DirectedWeightedGraph g4 = new MyGraph(ptg.getNodes(),ptg.getEdges());
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
                ptg = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\Test1.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        DirectedWeightedGraph g4 = new MyGraph(ptg.getNodes(),ptg.getEdges());
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
                ptg = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\Test1.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        DirectedWeightedGraph g4 = new MyGraph(ptg.getNodes(),ptg.getEdges());
        edge_nodeIT(g,g2,g3,g4);
    }

    void edge_nodeIT(DirectedWeightedGraph ... graphes) {
        for (DirectedWeightedGraph g: graphes) {
            checkEdge_nodeIT(g);
        }
    }
    public static void checkEdge_nodeIT(DirectedWeightedGraph g){
        LinkedList<EdgeData> e = new LinkedList<>();
            Iterator<NodeData> n = g.nodeIter();
            while (n.hasNext())
            {
                Iterator<EdgeData> edges = g.edgeIter(n.next().getKey());
                while (edges.hasNext()){
                    e.add(edges.next());
                }
            }
        Iterator<EdgeData> edges = g.edgeIter();
            while (edges.hasNext()){
                assertTrue(e.contains(edges.next()));
            }
            assertTrue(e.size()==g.edgeSize());

    }
    @Test
    void removeNode() {

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
                ptg = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\Test1.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        DirectedWeightedGraph g4 = new MyGraph(ptg.getNodes(),ptg.getEdges());
        remove(g,g2,g3,g4);
    }
    void remove(DirectedWeightedGraph ... graphes) {
        for (DirectedWeightedGraph g: graphes) {
            remover(g);
        }
    }
    void remover(DirectedWeightedGraph g) {
        int max = maxNodeId(g);
        for(int i=0;i<=max;i++){
            if(g.getNode(i)!=null){
                g.removeNode(i);
                assertTrue(g.getNode(i)==null);
                Iterator<EdgeData> e = g.edgeIter();
                while (e.hasNext()) {
                    EdgeData k = e.next();
                    assertFalse(k.getSrc() == i || k.getDest()==i,k.toString()+i);
                }
            }
        }
    }

    @Test
    void removeEdge() {

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
                ptg = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\Test1.json");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        DirectedWeightedGraph g4 = new MyGraph(ptg.getNodes(),ptg.getEdges());
        removeE(g);
    }
    void removeE(DirectedWeightedGraph ... graphes) {
        for (DirectedWeightedGraph g: graphes) {
            removerE(g);
        }
    }
    void removerE(DirectedWeightedGraph g) {

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

}