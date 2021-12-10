package GUI;

import api.MyGraph;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MyFrame extends JFrame implements ActionListener {
    private MyPanel panel;



    public MyFrame(MyGraph g){
        panel = new MyPanel(g);
        this.add(panel);
        this.pack();
        initGUI();


    }

    public void initGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("My Directed Weighted Graph");
        this.setResizable(false);
        Dimension scale = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)scale.width;
        int height = (int)scale.height;
        this.setPreferredSize(new Dimension(width, height));
        createMenus();
        this.setVisible(true);
    }

    private void createMenus(){
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        menuBar.add(menu);
        this.setMenuBar(menuBar);

        MenuItem item1 = new MenuItem("Load Graph");
        item1.addActionListener(this);

        MenuItem item2 = new MenuItem("Clear");
        item2.addActionListener(this);

        menu.add(item1);
        menu.add(item2);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
