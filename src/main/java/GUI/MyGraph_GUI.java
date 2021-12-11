package GUI;
import api.*;




public class MyGraph_GUI{
    private MyFrame frame;
    private MyPanel panel;
    private MyGraph graph;


    public MyGraph_GUI(MyGraph g) throws Exception{
        graph = g;
        frame = new MyFrame(graph);
        panel = new MyPanel(graph);
        frame.initGUI();
    }





    public static void main(String[] args) throws Exception {
        ParseToGraph pd = new ParseToGraph("data/G1.json");
        MyGraph mg = new MyGraph(pd.getNodes());
        MyGraph_GUI g2 = new MyGraph_GUI(mg);

    }
}
