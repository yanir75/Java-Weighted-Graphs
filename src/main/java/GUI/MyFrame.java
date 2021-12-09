package GUI;

import api.MyGraph;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private MyPanel panel;


    public MyFrame(MyGraph g){
        panel = new MyPanel(g);
        this.add(panel);
        this.pack();
        initGUI();


    }

    void initGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("My Directed Weighted Graph");
        this.setResizable(false);
        this.setPreferredSize(new Dimension(600, 600));
        this.setVisible(true);
    }
}
