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
        removeE(g,g2,g3,g4);
    }
    void removeE(DirectedWeightedGraph ... graphes) {
        for (DirectedWeightedGraph g: graphes) {
            removerE(g);
        }
    }
    void removerE(DirectedWeightedGraph g) {
        Iterator<EdgeData> iter = g.edgeIter();
        while (iter.hasNext()){
            iter.next();
            iter.remove();
        }
        assertTrue(g.edgeSize()==0);
        Iterator<NodeData> iter1 = g.nodeIter();
        while (iter1.hasNext()){
            iter = g.edgeIter(iter1.next().getKey());
            assertFalse(iter.hasNext());
        }
    }

    @Test
    void nodeSize() {
        countN(g,g2,g3,g4);
    }
    void countN(DirectedWeightedGraph ... graphes) {
        for (DirectedWeightedGraph g: graphes) {
            count_nodes(g);
        }
    }
    void count_nodes(DirectedWeightedGraph g) {
        int count =0;
        Iterator<NodeData> iter = g.nodeIter();
        while (iter.hasNext()){
            iter.next();
            count++;
        }
        assertTrue(count==g.nodeSize());

    }

    @Test
    void edgeSize() {
        countE(g,g2,g3,g4);
    }
    void countE(DirectedWeightedGraph ... graphes) {
        for (DirectedWeightedGraph g: graphes) {
            count_edges(g);
        }
    }
    void count_edges(DirectedWeightedGraph g) {
        int count =0;
        Iterator<EdgeData> iter = g.edgeIter();
        while (iter.hasNext()){
            iter.next();
            count++;
        }
        assertTrue(count==g.edgeSize());

    }
    @Test
    void getMC() {
        MC_check(g,g2,g3,g4);
    }
    void MC_check(DirectedWeightedGraph ... graphes) {
        for (DirectedWeightedGraph g: graphes) {
            change_MC(g);
        }
    }
    void change_MC(DirectedWeightedGraph g) {
        int m = g.getMC();
        Iterator<EdgeData> iter = g.edgeIter();
        if(iter.hasNext()){
            iter.next();
            iter.remove();
        }
        assertTrue(g.getMC()==m+1);
        if(iter.hasNext()){
            g.removeNode(iter.next().getSrc());
        }
        assertTrue(g.getMC()==m+2);
        iter = g.edgeIter();
        if(iter.hasNext()){
            EdgeData e = iter.next();
            g.removeEdge(e.getSrc(),e.getDest());
        }
        assertTrue(g.getMC()==m+3);
        Iterator<NodeData> nodeIt = g.nodeIter();
        if(nodeIt.hasNext()){
            nodeIt.next();
            nodeIt.remove();
        }
        assertTrue(g.getMC()==m+4);
        g.addNode(new Node(-2,-2,-2));
        assertTrue(g.getMC()==m+5);
        g.connect(-2,0,4);
        assertTrue(g.getMC()==m+6);

    }

    @Test
    void addNode() {
       add_n(g,g2,g3,g4);
    }
    void add_n(DirectedWeightedGraph ... graphes) {
        for (DirectedWeightedGraph g: graphes) {
            add_Node(g);
        }
    }
    void add_Node(DirectedWeightedGraph g) {
        g.addNode(new Node(-2,-2,-2));
        NodeData n =g.getNode(-2);
        assertTrue(n!=null);

    }
    @Test
    void iterator_remnove() {
        it_remover(g,g2,g3,g4);
    }
    void it_remover(DirectedWeightedGraph ... graphes) {
        for (DirectedWeightedGraph g: graphes) {
            iter_remove(g);
        }
    }
    void iter_remove(DirectedWeightedGraph g) {

        assertThrows(RuntimeException.class, ()->{
            Iterator<EdgeData> iter =g.edgeIter();
            g.addNode(new Node(0,0,-2));
            iter.hasNext();
        });
        assertThrows(RuntimeException.class, ()->{
            Iterator<EdgeData> iter =g.edgeIter();
            g.addNode(new Node(0,0,-3));
            iter.remove();
        });
        assertThrows(RuntimeException.class, ()->{
            Iterator<EdgeData> iter =g.edgeIter();
            g.addNode(new Node(0,0,-4));
            iter.next();
        });


        assertThrows(RuntimeException.class, ()->{
            Iterator<NodeData> iter =g.nodeIter();
            g.addNode(new Node(0,0,-10));
            iter.hasNext();
        });
        assertThrows(RuntimeException.class, ()->{
            Iterator<NodeData> iter =g.nodeIter();
            g.addNode(new Node(0,0,-15));
            iter.remove();
        });
        assertThrows(RuntimeException.class, ()->{
            Iterator<NodeData> iter =g.nodeIter();
            g.addNode(new Node(0,0,-30));
            iter.next();
        });

        assertThrows(RuntimeException.class, ()->{
            Iterator<EdgeData> iter =g.edgeIter(0);
            g.addNode(new Node(0,0,-100));
            iter.hasNext();
        });
        assertThrows(RuntimeException.class, ()->{
            Iterator<EdgeData> iter =g.edgeIter(0);
            g.addNode(new Node(0,0,-333));
            iter.remove();
        });
        assertThrows(RuntimeException.class, ()->{
            Iterator<EdgeData> iter =g.edgeIter(0);
            g.addNode(new Node(0,0,-444));
            iter.next();
        });


    }


}