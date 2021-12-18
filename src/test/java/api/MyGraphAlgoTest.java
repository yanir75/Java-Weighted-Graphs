package api;

import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MyGraphAlgoTest {

    @Test
    void init() {
        DirectedWeightedGraph g = new GraphGen().generate_connected_graph(2);
        DirectedWeightedGraphAlgorithms algo = new MyGraphAlgo();
        algo.init(g);
        assertNotNull(algo.getGraph());
    }

    @Test
    void getGraph() {
        DirectedWeightedGraph g = new GraphGen().generate_connected_graph(2);
        DirectedWeightedGraphAlgorithms algo = new MyGraphAlgo();
        assertNull(algo.getGraph());
        algo.init(g);
        assertNotNull(algo.getGraph());

    }

    @Test
    void copy() {
        DirectedWeightedGraph g = new GraphGen().generate_connected_graph(2);
        DirectedWeightedGraphAlgorithms algo = new MyGraphAlgo();
        algo.init(g);
        DirectedWeightedGraph g1 = algo.copy();
        assertNotEquals(g1,g);
        Iterator<NodeData> iter= g1.nodeIter();
        while (iter.hasNext()){
            NodeData n = iter.next();
            assertNotEquals(g.getNode(n.getKey()),n);
        }

        Iterator<EdgeData> iter1= g1.edgeIter();
        while (iter1.hasNext()){
            EdgeData e = iter1.next();
            assertNotEquals(g.getEdge(e.getSrc(),e.getDest()),e);
        }

    }

    @Test
    void isConnected() {
        DirectedWeightedGraph g = new GraphGen().generate_connected_graph(1000000);
        DirectedWeightedGraphAlgorithms algo = new MyGraphAlgo();
        algo.init(g);
        assertTrue(algo.isConnected());
    }

    @Test
    void shortestPathDist() {
        DirectedWeightedGraph g = new GraphGen().generate_connected_graph(10000);
        DirectedWeightedGraphAlgorithms algo = new MyGraphAlgo();
        algo.init(g);
        for(int i=0;i<10;i++) {
            int number = (int) (Math.random() * 1000);
            int number1 = (int) (Math.random() * (10000 - 1000) + 1000);
            List<NodeData> l = algo.shortestPath(number,number1);
            for(int j=0;j<l.size()-1;j++)
            {
                assertEquals(l.get(j).getKey()+1,l.get(j+1).getKey());
            }
        }



    }

    @Test
    void shortestPath() {
        DirectedWeightedGraph g = new GraphGen().generate_connected_graph(10000);
        DirectedWeightedGraphAlgorithms algo = new MyGraphAlgo();
        algo.init(g);
        for(int i=0;i<10;i++) {
            int number = (int) (Math.random() * 1000);
            int number1 = (int) (Math.random() * (10000 - 1000) + 1000);
            List<NodeData> l = algo.shortestPath(number,number1);
            double x = algo.shortestPathDist(number,number1);
            double y = 0;
            for(int j=0;j<l.size()-1;j++)
            {
                EdgeData e = g.getEdge(l.get(j).getKey(),l.get(j+1).getKey());
                y = y+e.getWeight();
            }
            assertEquals(x,y);
        }
    }

    @Test
    void center() {
        DirectedWeightedGraph g = new GraphGen().generate_connected_graph(1000);
        DirectedWeightedGraphAlgorithms algo = new MyGraphAlgo();
        algo.init(g);
        Node n = new Node(0,0,-2);
        g.addNode(n);
        assertEquals(algo.center().toString(),n.toString());
        File f = new File("");
        String path = f.getAbsolutePath();
        algo.load(path+"\\data\\G1.json");
        assertEquals(algo.center().getKey(),8);
    }

    @Test
    void tsp() {
        MyGraphAlgo g = new MyGraphAlgo();
        g.load("data/G1.json");
        List<NodeData> l = new LinkedList<>();
        l.add(g.getGraph().getNode(5));
        l.add(g.getGraph().getNode(3));
        l.add(g.getGraph().getNode(1));
        l.add(g.getGraph().getNode(2));
        l=g.tsp(l);
        for(int i =5;i<=1;i--){
            assertEquals(l.remove(0).getKey(),i);
        }
    }

    static DirectedWeightedGraph g = new GraphGen().generate_connected_graph(2);
    static DirectedWeightedGraphAlgorithms algo = new MyGraphAlgo();
    @Test
    @Order(1)
    void save() {
        algo.init(g);
        algo.save("MyWeirdName");
    }
    @Test
    @Order(2)
    void load() {
        DirectedWeightedGraphAlgorithms algo1 = new MyGraphAlgo();
        algo1.load("MyWeirdName");
        assertEquals(algo1.getGraph().toString(),algo.getGraph().toString());
    }
    @AfterAll
    public static void delete() {
        File f = new File("MyWeirdName");
        if(f.delete()){
            System.out.println("deleted the file");
        }
        else {
            System.err.println("The file was not deleted");
        }
    }


}