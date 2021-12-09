package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {

    @Test
    void copy() {
        Edge e = new Edge(0,0,2);
        Edge e1 = e.copy();
        assertFalse(e==e1);
        assertEquals(e.toString(),e1.toString());
    }

    @Test
    void getSrc() {
        Edge e = new Edge(0,1,2);
        assertTrue(e.getSrc()==0);
    }

    @Test
    void getDest() {
        Edge e = new Edge(0,1,2);
        assertTrue(e.getDest()==1);
    }

    @Test
    void getWeight() {
        Edge e = new Edge(0,1,2);
        assertTrue(e.getWeight()==2);
    }

    @Test
    void get_set_Info() {
        Edge e = new Edge(0,1,2);
        e.setInfo("hey");
        assertEquals(e.getInfo(),"hey");
    }


    @Test
    void get_set_Tag() {
        Edge e = new Edge(0,1,2);
        e.setTag(1);
        assertEquals(e.getTag(),1);
    }


    @Test
    void testToString() {
        Edge e = new Edge(0,1,2);
        assertEquals(e.toString(),"{\n" +
                "\"src\": 0,\n" +
                "\"w\": 2.0,\n" +
                "\"dest\": 1\n" +
                "}");

    }
    @Test
    void except(){
        assertThrows(RuntimeException.class,()->{
            new Edge(0,0,-2);
        });
    }
}