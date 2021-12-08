package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class graphGenTest {
    @Test
    public void mk_graph(){
        graphGen g = new graphGen();
        MyGraph g1 = g.generate_graph(10,20);
        assertEquals(g1.nodeSize(),10);
        assertEquals(g1.edgeSize(),20);
        g1 = g.generate_large_graph(10000,200000);
        assertEquals(g1.nodeSize(),10000);
        assertEquals(g1.edgeSize(),200000);
    }

}