package GUI;
import api.*;




public class MyGraph_GUI{
    private MyFrame frame;
    private MyPanel panel;
    private MyGraph graph;



    public MyGraph_GUI(DirectedWeightedGraph g) throws Exception{
        graph = (MyGraph) g;
        frame = new MyFrame(graph);
        panel = new MyPanel(graph);
        frame.initGUI();
    }
    public MyGraph_GUI() throws Exception{

    }

    public static void drawGUI() throws Exception {
        new MyFrame(null);
    }



    public static void main(String[] args) throws Exception {
        ParseToGraph pd = new ParseToGraph("data/G1.json");
        MyGraph mg = new MyGraph(pd.getNodes());
        new MyGraph_GUI(mg);
//        drawGUI();

    }
}
