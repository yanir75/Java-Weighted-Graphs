package GUI;

import api.Algorithms;
import api.MyGraph;
import api.ParseToGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;


public class MyFrame extends JFrame implements ActionListener {
    private MyPanel mainPanel;
    private JPanel buttonsPanel, outputPanel;
    private MyGraph graph;
    private Algorithms algo;

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
    JMenuItem shortestPath;
    JMenuItem centerItem;
    JMenuItem TSPItem;

    // Help menu


    JButton SP;
    JComboBox cmb;
    JTextField srcNode;
    JTextField destNode;
    JButton IC;
    JButton TSP;
    JButton CE;
    JButton RN;
    JButton RE;
    JButton AN;
    JButton AE;
    JButton CLR;
    JButton LOAD;
    JButton SAVE;

    public MyFrame(MyGraph g){
        graph = g;
        mainPanel = new MyPanel(g);
        buttonsPanel = new JPanel();
        outputPanel = new JPanel(new BorderLayout());
        algo = mainPanel.graph;
        this.add(mainPanel);
        this.pack();
        initGUI();
        addButtonsAndText();


    }

    public void initGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLayout(new BorderLayout());
        this.setTitle("My Directed Weighted Graph");
        this.setResizable(false);
        Dimension scale = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)scale.width;
        int height = (int)scale.height;
        this.setPreferredSize(new Dimension(width, height));
        buttonsPanel.setPreferredSize(new Dimension(width / 25, height / 15));
//        buttonsPanel.setBounds(scale.width / 2 - 150, 0, 150, scale.width / 2);
        outputPanel.setPreferredSize(new Dimension(width / 10, height / 10));
        buttonsPanel.setBackground(Color.GRAY);
        outputPanel.setBackground(Color.BLACK);
        this.add(buttonsPanel, BorderLayout.NORTH);
        this.add(outputPanel, BorderLayout.SOUTH);
        createMenus();
        createKeyShortCuts();
//        addButtons();
//        this.pack();
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
        shortestPath = new JMenuItem("Shortest Path(returns Weight)     (Alt+A+S)");
        centerItem = new JMenuItem("Center                                             (Alt+A+C)");
        TSPItem = new JMenuItem("TSP                                                  (Alt+A+T)");

        isConnectedItem.addActionListener(this);
        shortestPath.addActionListener(this);
        centerItem.addActionListener(this);
        TSPItem.addActionListener(this);

        algorithmsMenu.add(isConnectedItem);
        algorithmsMenu.add(shortestPath);
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
        shortestPath.setMnemonic(KeyEvent.VK_S); // w
        centerItem.setMnemonic(KeyEvent.VK_C); // c
        TSPItem.setMnemonic(KeyEvent.VK_T); // t

    }

    private void addButtonsAndText(){

        IC = new JButton("isConnected");
        IC.setFocusable(false);
        IC.addActionListener(this);
        IC.setBackground(Color.ORANGE);


        TSP = new JButton("TSP");
        TSP.setFocusable(false);
        TSP.addActionListener(this);
        TSP.setBackground(Color.ORANGE);


        CE = new JButton("Center");
        CE.setFocusable(false);
        CE.addActionListener(this);
        CE.setBackground(Color.ORANGE);

        SP = new JButton("Shortest Path");
        SP.setFocusable(false);
        SP.addActionListener(this);
        SP.setBackground(Color.ORANGE);

        srcNode = new JTextField("Source");
        srcNode.setPreferredSize(new Dimension(70,25));
        srcNode.setToolTipText("Enter the source node");


        destNode = new JTextField("Destination");
        destNode.setPreferredSize(new Dimension(70,25));
        destNode.setToolTipText("Enter the destination node");


        RN = new JButton("Remove Node");
        RN.setFocusable(false);
        RN.addActionListener(this);
        RN.setBackground(new Color(243,76,76));


        RE = new JButton("Remove Edge");
        RE.setFocusable(false);
        RE.addActionListener(this);
        RE.setBackground(new Color(243,76,76));


        AN = new JButton("Add Node");
        AN.setFocusable(false);
        AN.addActionListener(this);
        AN.setBackground(new Color(243,76,76));


        AE = new JButton("Add Edge");
        AE.setFocusable(false);
        AE.addActionListener(this);
        AE.setBackground(new Color(243,76,76));


        CLR = new JButton("Clear");
        CLR.setFocusable(false);
        CLR.addActionListener(this);
        CLR.setBackground(new Color(51,153,255));

        SAVE = new JButton("Save");
        SAVE.setFocusable(false);
        SAVE.addActionListener(this);
        SAVE.setBackground(new Color(51,153,255));

        LOAD = new JButton("Load");
        LOAD.setFocusable(false);
        LOAD.addActionListener(this);
        LOAD.setBackground(new Color(51,153,255));


        buttonsPanel.add(IC);
        buttonsPanel.add(TSP);
        buttonsPanel.add(CE);
        buttonsPanel.add(SP);
        buttonsPanel.add(srcNode);
        buttonsPanel.add(destNode);
        buttonsPanel.add(RN);
        buttonsPanel.add(RE);
        buttonsPanel.add(AN);
        buttonsPanel.add(AE);
        buttonsPanel.add(LOAD);
        buttonsPanel.add(SAVE);
        buttonsPanel.add(CLR);

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object event = e.getSource();


        if (loadItem.equals(event) || LOAD.equals(event)) {
            JFileChooser loadFile = new JFileChooser();
            loadFile.setCurrentDirectory(new File("."));
            int approved = loadFile.showOpenDialog(null);
            if(approved == JFileChooser.APPROVE_OPTION){
                if(loadFile.getSelectedFile().getAbsolutePath().endsWith(".json")) {
                    ParseToGraph pd = null;
                    try {
                        pd = new ParseToGraph(loadFile.getSelectedFile().getAbsolutePath());
                        MyGraph g = new MyGraph(pd.getNodes());
                        setVisible(false);
                        dispose();
                        repaint();
                        new MyGraph_GUI(g);
                        System.out.println("The file: " + loadFile.getSelectedFile().getName() + ", was loaded successfully");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else{
                    System.out.println("This Program supports only Json files.");
                }
            }


        }
        else if (saveItem.equals(event) || SAVE.equals(event)) {
            System.out.println("Saved");


        }
        else if (clearItem.equals(event) || CLR.equals(event)) {
            try {
                MyGraph g = new MyGraph();
                setVisible(false);
                dispose();
                repaint();
                new MyGraph_GUI(g);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Cleared");


        }
        else if (exitItem.equals(event)) {
            System.out.println("Exiting");
            System.exit(0);
        }
        else if (addEdgeItem.equals(event) || AE.equals(event)) {
            JTextArea textArea= new JTextArea();
            textArea.setPreferredSize(new Dimension(100,100));
            this.add(textArea);
            repaint();
//            this.graph.connect();
            System.out.println("Edge added");
        }
        else if (addNodeItem.equals(event) || AN.equals(event)) {
            System.out.println("Node added");
        }
        else if (removeEdgeItem.equals(event) || RE.equals(event)) {
            System.out.println("Edge removed");
        }
        else if (removeNodeItem.equals(event) || RN.equals(event)) {
            System.out.println("Node removed");
        }
        else if (connectNodesItem.equals(event)) {
            System.out.println("Nodes are now connected");
        }
        else if (isConnectedItem.equals(event) || IC.equals(event)) {
            System.out.println(algo.isConnected());
        }
        else if (shortestPath.equals(event) || SP.equals(event)) {
            int src = -1;
            int dest = -1;
            try {
                src = Integer.parseInt(srcNode.getText());
                dest = Integer.parseInt(destNode.getText());
                if(graph.getNodes().containsKey(src)){
                    if(graph.getNodes().containsKey(dest)){
                        double sp = algo.shortestPathDist(src, dest);
                        System.out.println(sp);
                    }
                    else{
                        JOptionPane.showOptionDialog(null,
                                "Wrong input!\n The destination entered does not appear in the Graph.",
                                "DESTINATION INPUT ERROR!",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.WARNING_MESSAGE,
                                null,
                                null,
                                null);
                    }
                }
                else{
                    JOptionPane.showOptionDialog(null,
                            "Wrong input!\n The source entered does not appear in the Graph.",
                            "SOURCE INPUT ERROR!",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            null,
                            null,
                            null);
                }
            }
            catch (NumberFormatException ex){
                JOptionPane.showOptionDialog(null,
                        "Wrong input!\n Please enter only numeric values.",
                        "INPUT ERROR!",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        null,
                        null);
            }
        }
        else if (centerItem.equals(event) || CE.equals(event)) {
            System.out.println(algo.center());
        }
        else if (TSPItem.equals(event) || TSP.equals(event)) {
            System.out.println("TSP activated");
        }
    }


}
