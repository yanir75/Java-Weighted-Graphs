import api.MyGraph;
import api.NodeData;
import api.ParseToGraph;

import java.io.FileNotFoundException;
import java.util.Iterator;

public class TestParser {
    public static void main(String[] args) {
        try {
            ParseToGraph ptg = new ParseToGraph("C:\\Users\\netan\\IdeaProjects\\Weighted_Graph_Algorithms\\data\\G1.json");
            MyGraph y = new MyGraph(ptg.getNodes(), ptg.getEdges());
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
        catch(FileNotFoundException e){
            e.printStackTrace();
            System.out.println();
        }
    }
}
