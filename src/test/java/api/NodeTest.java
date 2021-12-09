package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    Node n = new Node(0,0,1);

    @Test
    void addInEdge() {
        n.addInEdge(1);
        assertTrue(n.inEdges().contains(1));
    }


    @Test
    void copy() {
        Node n1 = n.copy();
        assertFalse(n1==n);
        assertEquals(n1.toString(),n.toString());
    }

    @Test
    void getKey() {
        assertEquals(n.getKey(),1);
    }


    @Test
    void getLocation() {
        Location g= new Location(0,0,0);
        assertEquals(n.getLocation().toString(),g.toString());
    }

    @Test
    void get_set_Weight() {
        n.setWeight(0);
        assertEquals(n.getWeight(),0);
    }


    @Test
    void get_set_Info() {
        n.setInfo("test");
        assertEquals(n.getInfo(),"test");
    }


    @Test
    void get_set_Tag() {
        n.setTag(1);
        assertEquals(n.getTag(),1);
    }



    @Test
    void addEdge() {
        n.addEdge(new Edge(0,2,2));
        assertEquals(n.getEdges().size(),0);
        n.addEdge(new Edge(1,2,2));
        assertTrue(n.getEdges().containsKey(2));

    }

    @Test
    void removeEdge() {
        n.addEdge(new Edge(0,2,2));
        assertEquals(n.getEdges().size(),0);
        n.removeEdge(2);
        assertFalse(n.getEdges().containsKey("1-2"));
    }

    @Test
    void setLocation() {
        n.setLocation(new Location(3,3,3));
        Location g = new Location(3,3,3);
        assertEquals(n.getLocation().toString(),g.toString());
    }


    @Test
    void testToString() {
        assertEquals(n.toString(),"{\n" +
                "\"pos\": \"0.0,0.0\",\n" +
                "\"id\": 1\n" +
                "}");
    }
}