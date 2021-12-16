package GUI;
import api.*;




public class MyGraph_GUI{
    private MyFrame frame;
    private MyPanel panel;
    private DirectedWeightedGraph graph;



    public MyGraph_GUI(DirectedWeightedGraph g) {
        this.graph = g;
        this.frame = new MyFrame(this.graph);
        this.panel = new MyPanel(this.graph);
    }
    public MyGraph_GUI(DirectedWeightedGraph g, String log) {
        this.graph = g;
        this.frame = new MyFrame(this.graph);
        this.panel = new MyPanel(this.graph);
        this.frame.setOutputText(log);
    }

    public static void main(String[] args) {
        ParseToGraph pd = new ParseToGraph("data/G1.json");
        MyGraph mg = new MyGraph(pd.getNodes(),pd.size);
        new MyGraph_GUI(mg);
    }
}
