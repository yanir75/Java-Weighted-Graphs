package api;

import java.util.Iterator;

public class test {
    public static void main(String[] args) {
        ParseToGraph x = new ParseToGraph("C:\\Users\\yanir\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\G1.json");
        MyGraph  y = new MyGraph(x.getNodes(),x.getEdges());
        Iterator<NodeData> z =y.nodeIter();
        boolean f = true;
        while (z.hasNext())
            if(f){
                z.next();
                z.remove();
                f=false;
            }
            else
            System.out.println(z.next());

    }
}
