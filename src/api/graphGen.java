package api;

import java.util.HashMap;
import java.util.Random;

public class graphGen {

    public graphGen(){}

    public MyGraph generate_connected_graph(int numOfNodes){
        HashMap<Integer, Node> nodes = new HashMap<>();
        HashMap<String, Edge> edges = new HashMap<>();
        Random rand = new Random();
        for(int i=0;i<numOfNodes;i++){
            nodes.put(i,new Node(rand.nextDouble()*100,rand.nextDouble()*100,i));
        }
        for(int i=0;i<=numOfNodes;i++){
            Edge e = new Edge(i,i+1,rand.nextDouble()*10);
            edges.put(i+"-"+(i+1),e);
        }
        return new MyGraph(nodes,edges);
    }
    public MyGraph generate_graph(int numOfNodes,int numberOfEdges){
        HashMap<Integer, Node> nodes = new HashMap<>();
        HashMap<String, Edge> edges = new HashMap<>();
        Random rand = new Random();
        for(int i=0;i<numOfNodes;i++){
            nodes.put(i,new Node(rand.nextDouble()*100,rand.nextDouble()*100,i));
        }
        while (edges.size()<numberOfEdges)
        {   int x = rand.nextInt(numOfNodes);
            int y = rand.nextInt(numOfNodes);
            while (x==y){
                y = rand.nextInt(numOfNodes);
            }
            Edge e = new Edge(x,y,rand.nextDouble()*10);
            edges.put(x+"-"+y,e);
        }
        return new MyGraph(nodes,edges);
    }
    public MyGraph generate_large_graph(int numOfNodes,int numberOfEdges){
        HashMap<Integer, Node> nodes = new HashMap<>();
        HashMap<String, Edge> edges = new HashMap<>();
        Random rand = new Random();
        for(int i=0;i<numOfNodes;i++){
            nodes.put(i,new Node(rand.nextDouble()*100,rand.nextDouble()*100,i));
        }
        for(int i=0;i<numOfNodes;i++)
        {
            for(int j=i;j<numOfNodes;j++){
                Edge e = new Edge(i,j,rand.nextDouble()*10);
                edges.put(i+"-"+j,e);
            }
            if(edges.size()==numberOfEdges)
                return new MyGraph(nodes,edges);
        }
        for(int i=0;i<numOfNodes;i++)
        {
            for(int j=i;j<numOfNodes;j++){
                Edge e = new Edge(j,i,rand.nextDouble()*10);
                edges.put(j+"-"+i,e);
            }
            if(edges.size()==numberOfEdges)
                return new MyGraph(nodes,edges);
        }
        return new MyGraph(nodes,edges);
    }

}
