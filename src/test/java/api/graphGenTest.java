package api;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class graphGenTest {
    @Test
    public void mk_graph(){
        graphGen g = new graphGen();
        MyGraph g1 = g.generate_graph(10,20);
        assertEquals(g1.nodeSize(),10);
        assertEquals(g1.edgeSize(),20);
        g1 = g.generate_large_graph(1000000,20000000);
        assertEquals(g1.nodeSize(),1000000);
        assertEquals(g1.edgeSize(),20000000);
        Iterator<NodeData> iter = g1.nodeIter();
        int count=0;
        while (iter.hasNext())
        {
            iter.next();
            count++;
        }
        assertEquals(count,1000000);

        count=0;
        Iterator<EdgeData> iter1 = g1.edgeIter();
        while (iter1.hasNext())
        {
            iter1.next();
            count++;
        }
        assertEquals(count,20000000);
    }

}