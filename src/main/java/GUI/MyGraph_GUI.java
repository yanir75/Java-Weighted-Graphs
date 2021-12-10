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
        MyGraph_GUI g = new MyGraph_GUI(new MyGraph());

    }
}
