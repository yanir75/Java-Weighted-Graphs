package GUI;

import api.MyGraph;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;


public class MyFrame extends JFrame implements ActionListener {
    private MyPanel panel;

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu editMenu;
    JMenu algorithmsMenu;
    JMenu helpMenu;

    // File Menu
    JMenuItem loadItem;
    JMenuItem saveItem;
    JMenuItem clearItem;
    JMenuItem exitItem;

    // Edit menu
    JMenuItem removeNodeItem;
    JMenuItem removeEdgeItem;
    JMenuItem addNodeItem;
    JMenuItem addEdgeItem;
    JMenuItem connectNodesItem;

    // Algorithms menu
    JMenuItem isConnectedItem;
    JMenuItem shortestPathWItem;
    JMenuItem shortestPathPItem;
    JMenuItem centerItem;
    JMenuItem TSPItem;

    // Help menu



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
        createKeyShortCuts();
        this.setVisible(true);
    }


    private void createMenus(){
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        algorithmsMenu = new JMenu("Algorithms");
        helpMenu = new JMenu("Help");


        // File Menu
        loadItem = new JMenuItem("Load       (Alt+F+L)");
        saveItem = new JMenuItem("Save       (Alt+F+S)");
        clearItem = new JMenuItem("Clear      (Alt+F+C)");
        exitItem = new JMenuItem("Exit         (Alt+F+E)");

        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        clearItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(clearItem);
        fileMenu.add(exitItem);


        // Edit menu
        removeNodeItem = new JMenuItem("Remove Node     (Alt+E+R)");
        removeEdgeItem = new JMenuItem("Remove Edge     (Alt+E+R)");
        addNodeItem = new JMenuItem("Add Node             (Alt+E+A)");
        addEdgeItem = new JMenuItem("Add Edge             (Alt+E+A)");
        connectNodesItem = new JMenuItem("Connect               (Alt+E+C)");

        removeNodeItem.addActionListener(this);
        removeEdgeItem.addActionListener(this);
        addNodeItem.addActionListener(this);
        addEdgeItem.addActionListener(this);
        connectNodesItem.addActionListener(this);

        editMenu.add(removeNodeItem);
        editMenu.add(removeEdgeItem);
        editMenu.add(addNodeItem);
        editMenu.add(addEdgeItem);
        editMenu.add(connectNodesItem);


        // Algorithms menu
        isConnectedItem = new JMenuItem("isConnected                                    (Alt+A+I)");
        shortestPathWItem = new JMenuItem("Shortest Path(returns Weight)     (Alt+A+W)");
        shortestPathPItem = new JMenuItem("Shortest Path(returns Path)         (Alt+A+P)");
        centerItem = new JMenuItem("Center                                             (Alt+A+C)");
        TSPItem = new JMenuItem("TSP                                                  (Alt+A+T)");

        isConnectedItem.addActionListener(this);
        shortestPathWItem.addActionListener(this);
        shortestPathPItem.addActionListener(this);
        centerItem.addActionListener(this);
        TSPItem.addActionListener(this);

        algorithmsMenu.add(isConnectedItem);
        algorithmsMenu.add(shortestPathWItem);
        algorithmsMenu.add(shortestPathPItem);
        algorithmsMenu.add(centerItem);
        algorithmsMenu.add(TSPItem);


        // Help menu




        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(algorithmsMenu);
        menuBar.add(helpMenu);
        this.setJMenuBar(menuBar);
    }


    private void createKeyShortCuts(){
        // Shortcuts for fileMenu.
        fileMenu.setMnemonic(KeyEvent.VK_F); // Alt + f
        loadItem.setMnemonic(KeyEvent.VK_L); // l
        saveItem.setMnemonic(KeyEvent.VK_S); // s
        clearItem.setMnemonic(KeyEvent.VK_C); // c
        exitItem.setMnemonic(KeyEvent.VK_E); // e

        // Shortcuts for editMenu.
        editMenu.setMnemonic(KeyEvent.VK_E); // Alt + e
        addEdgeItem.setMnemonic(KeyEvent.VK_A); // a
//        addNodeItem.setMnemonic(KeyEvent.VK_A); // s
        removeEdgeItem.setMnemonic(KeyEvent.VK_R); // r
//        removeNodeItem.setMnemonic(KeyEvent.VK_R); // e
        connectNodesItem.setMnemonic(KeyEvent.VK_C); // c

        // Shortcuts for algorithmsMenu.
        algorithmsMenu.setMnemonic(KeyEvent.VK_A); // Alt + a
        isConnectedItem.setMnemonic(KeyEvent.VK_I); // i
        shortestPathWItem.setMnemonic(KeyEvent.VK_W); // w
        shortestPathPItem.setMnemonic(KeyEvent.VK_P); // p
        centerItem.setMnemonic(KeyEvent.VK_C); // c
        TSPItem.setMnemonic(KeyEvent.VK_T); // t

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (loadItem.equals(source)) {
            System.out.println("Loaded");
            JFileChooser loadFile = new JFileChooser();
            loadFile.setCurrentDirectory(new File("."));
            int approved = loadFile.showOpenDialog(null);
            if(approved == JFileChooser.APPROVE_OPTION){
                File file = new File(loadFile.getSelectedFile().getAbsolutePath());
                System.out.println(file);
            }
        } else if (saveItem.equals(source)) {
            System.out.println("Saved");
        } else if (clearItem.equals(source)) {
            System.out.println("Cleared");
        } else if (exitItem.equals(source)) {
            System.out.println("Exiting");
            System.exit(0);
        } else if (addEdgeItem.equals(source)) {
            System.out.println("Edge added");
        } else if (addNodeItem.equals(source)) {
            System.out.println("Node added");
        } else if (removeEdgeItem.equals(source)) {
            System.out.println("Edge removed");
        } else if (removeNodeItem.equals(source)) {
            System.out.println("Node removed");
        } else if (connectNodesItem.equals(source)) {
            System.out.println("Nodes are now connected");
        } else if (isConnectedItem.equals(source)) {
            System.out.println("isConnected activated");
        } else if (shortestPathWItem.equals(source)) {
            System.out.println("shortestPathW activated");
        } else if (shortestPathPItem.equals(source)) {
            System.out.println("shortestPathP activated");
        } else if (centerItem.equals(source)) {
            System.out.println("Center activated");
        } else if (TSPItem.equals(source)) {
            System.out.println("TSP activated");
        }
    }


}
