package GUI;

import api.MyGraph;
import javax.swing.*;


public class MyGraph_GUI extends JFrame {
//    private JFrame frame;


    public MyGraph_GUI()throws Exception{
//        this.add(new MyGraph(graph));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("My Directed Weighted Graph");
        this.setResizable(false);
        this.setSize(600, 600);
        this.setVisible(true);


    }

    public static void main(String[] args) throws Exception {
        MyGraph_GUI g = new MyGraph_GUI();

    }
}
