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
//        this.frame.initGUI();
    }
    public MyGraph_GUI(DirectedWeightedGraph g, String log) {
        this.graph = g;
        this.frame = new MyFrame(this.graph);
        this.panel = new MyPanel(this.graph);
//        this.frame.initGUI();
        this.frame.setOutputText(log);
    }

    public MyFrame getFrame() {
        return frame;
    }

    public MyGraph_GUI() throws Exception{

    }

    public static void drawGUI() throws Exception {
        new MyFrame(null);
    }



    public static void main(String[] args) throws Exception {
        ParseToGraph pd = new ParseToGraph("data/G1.json");
        MyGraph mg = new MyGraph(pd.getNodes(),pd.size);
        new MyGraph_GUI(mg);
//        drawGUI();

    }
}
