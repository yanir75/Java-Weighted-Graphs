package GUI;

import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.Locale;


public class MyFrame extends JFrame implements ActionListener {
    private MyPanel mainPanel;
    private JPanel buttonsPanel, outputPanel;
    private JScrollPane terminal;
    private JTextArea ta;
    private MyGraph graph;
    private Algorithms algo;
    private NodeData center;
    private String outputText;
    private int width, height;
    private boolean colored;

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenu editMenu;
    JMenu algorithmsMenu;
    JMenu viewMenu;
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

    // Algorithms menu
    JMenuItem isConnectedItem;
    JMenuItem shortestPath;
    JMenuItem centerItem;
    JMenuItem TSPItem;

    // View menu
    JMenuItem FullScreenItem;
    JMenuItem defaultItem;
    JMenuItem customScaleItem;

    // Help menu


    // Buttons
    JButton SP;
//    JTextField srcNode;
//    JTextField destNode;
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

    public MyFrame(DirectedWeightedGraph g){
        this.graph = (MyGraph) g;
        this.mainPanel = new MyPanel(this.graph);
        this.buttonsPanel = new JPanel();
        this.outputPanel = new JPanel(new BorderLayout());
        this.algo = this.mainPanel.getGraph();
        this.colored = false;
        this.center = null;
        this.outputText = "Welcome to My Directed Weighted Graph action log...";
        initGUI();
        addButtonsAndText();


    }

    public void initGUI(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setTitle("My Directed Weighted Graph");
        ImageIcon title = new ImageIcon("graph.jpg");
        this.setIconImage(title.getImage());

        Dimension scale = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = (int)scale.width;
        this.height = (int)scale.height;
        this.setSize(width / 4, height / 5);
        this.setResizable(false);

        this.ta = new JTextArea(outputText, 1,100);
        this.ta.setBackground(Color.black);
        this.ta.setForeground(Color.white);
        this.ta.setFont(new Font("ariel", Font.BOLD, 14));
        this.ta.setEditable(false);

        this.terminal = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.buttonsPanel.setPreferredSize(new Dimension(width / 25, height / 22));
        buttonsPanel.setBackground(Color.GRAY);

        this.outputPanel.setPreferredSize(new Dimension(width / 10, height / 5));
        this.outputPanel.add(this.terminal);

        this.add(buttonsPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(outputPanel, BorderLayout.SOUTH);

        createMenus();
        createKeyShortCuts();
        this.pack();
        this.setVisible(true);
    }


    private void createMenus(){
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        algorithmsMenu = new JMenu("Algorithms");
        viewMenu = new JMenu("View");
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

        removeNodeItem.addActionListener(this);
        removeEdgeItem.addActionListener(this);
        addNodeItem.addActionListener(this);
        addEdgeItem.addActionListener(this);

        editMenu.add(removeNodeItem);
        editMenu.add(removeEdgeItem);
        editMenu.add(addNodeItem);
        editMenu.add(addEdgeItem);


        // Algorithms menu
        isConnectedItem = new JMenuItem("isConnected        (Alt+A+I)");
        shortestPath = new JMenuItem("Shortest Path      (Alt+A+S)");
        centerItem = new JMenuItem("Center                  (Alt+A+C)");
        TSPItem = new JMenuItem("TSP                       (Alt+A+T)");

        isConnectedItem.addActionListener(this);
        shortestPath.addActionListener(this);
        centerItem.addActionListener(this);
        TSPItem.addActionListener(this);

        algorithmsMenu.add(isConnectedItem);
        algorithmsMenu.add(shortestPath);
        algorithmsMenu.add(centerItem);
        algorithmsMenu.add(TSPItem);


        // View menu
        FullScreenItem = new JMenuItem("Full Screen                    (Alt+V+F)");
        defaultItem = new JMenuItem("Default Screen              (Alt+V+D)");
        customScaleItem = new JMenuItem("Custom Screen              (Alt+V+C)");

        FullScreenItem.addActionListener(this);
        defaultItem.addActionListener(this);
        customScaleItem.addActionListener(this);

        viewMenu.add(FullScreenItem);
        viewMenu.add(defaultItem);
        viewMenu.add(customScaleItem);

        // Help menu




        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(algorithmsMenu);
        menuBar.add(viewMenu);
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

        // Shortcuts for algorithmsMenu.
        algorithmsMenu.setMnemonic(KeyEvent.VK_A); // Alt + a
        isConnectedItem.setMnemonic(KeyEvent.VK_I); // i
        shortestPath.setMnemonic(KeyEvent.VK_S); // w
        centerItem.setMnemonic(KeyEvent.VK_C); // c
        TSPItem.setMnemonic(KeyEvent.VK_T); // t

        // Shortcuts for viewMenu.
        viewMenu.setMnemonic(KeyEvent.VK_V); // Alt + v
        FullScreenItem.setMnemonic(KeyEvent.VK_F); // f
        defaultItem.setMnemonic(KeyEvent.VK_D); // d
        customScaleItem.setMnemonic(KeyEvent.VK_C); // c

    }

//    private void updateTerminal(String text){
//        this.setVisible(false);
//        this.remove(this.outputPanel);
//        this.outputPanel = new JPanel(new BorderLayout());
//        text = this.ta.getText() + "\n" + text;
//        this.ta = new TextArea(text);
//        this.ta.setBackground(Color.black);
//        this.ta.setForeground(Color.white);
//        this.ta.setFont(new Font("ariel", Font.BOLD, 14));
//        this.ta.setEditable(false);
//
//        this.terminal = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//
//        this.outputPanel.setPreferredSize(new Dimension(this.width / 10, this.height / 5));
//        this.outputPanel.add(this.terminal);
//
//        this.add(this.outputPanel);
//        this.pack();
//        repaint();
//        this.setVisible(true);
//    }


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

//        srcNode = new JTextField("Source");
//        srcNode.setPreferredSize(new Dimension(70,25));
//        srcNode.setToolTipText("Enter the source node");
//
//
//        destNode = new JTextField("Destination");
//        destNode.setPreferredSize(new Dimension(70,25));
//        destNode.setToolTipText("Enter the destination node");


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
//        buttonsPanel.add(srcNode);
//        buttonsPanel.add(destNode);
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
        UIManager.put("OptionPane.messageFont", new Font("ariel", Font.BOLD, 14));

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
                    JOptionPane.showOptionDialog(null,
                            "Wrong file!\nThis Program supports only Json files.",
                            "WRONG FILE FORMAT",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.ERROR_MESSAGE,
                            null,
                            null,
                            null);
                    System.out.println("This Program supports only Json files.");
                }
            }
        }

        else if (saveItem.equals(event) || SAVE.equals(event)) {
            JFileChooser saveFile = new JFileChooser();
            saveFile.setCurrentDirectory(new File("."));
            int approved = saveFile.showSaveDialog(null);
            if (approved == JFileChooser.APPROVE_OPTION) {
                if (saveFile.getSelectedFile().getAbsolutePath().endsWith(".json")) {
                    String path = saveFile.getSelectedFile().getAbsolutePath();
                    this.algo.save(path);
                    System.out.println("The file: " + saveFile.getSelectedFile().getName() + ", was saved successfully");
                } else {
                    JOptionPane.showOptionDialog(null,
                            "Wrong file!\nThis Program supports only Json files.",
                            "WRONG FILE FORMAT",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.ERROR_MESSAGE,
                            null,
                            null,
                            null);
                    System.out.println("This Program supports only Json files.");
                }
            }
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
            JTextField srcText = new JTextField("Source");
            JTextField destText = new JTextField("Destination");
            JTextField weightText = new JTextField("Weight");
            String title = "Insert the Source, Destination, Weight\nvalues to add to the new Edge.\nIn order to finish click on the OK button.\n";
            srcText.setToolTipText("Enter the Source Node");
            destText.setToolTipText("Enter the Destination Node");
            weightText.setToolTipText("Enter the weight of this Edge");
            Object[] options = {title, "\n", srcText, destText, weightText};

            JOptionPane j = new JOptionPane();
            j.setMessage(options);
            j.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = j.createDialog(null, "Add new Edge");
            dialog.setVisible(true);

            int src;
            int dest;
            double weight;
            try {
                src = Integer.parseInt(srcText.getText());
                dest = Integer.parseInt(destText.getText());
                weight = Double.parseDouble(weightText.getText());
                this.graph.connect(src, dest, weight);
            }
            catch (NumberFormatException ex){
                JOptionPane.showOptionDialog(null,
                        "Wrong input!\nPlease enter only numeric values.",
                        "INPUT ERROR!",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        null,
                        null);
            }
            System.out.println("Edge added");
        }

        else if (addNodeItem.equals(event) || AN.equals(event)) {
            JTextField xCoordination = new JTextField("  X  ");
            JTextField yCoordination = new JTextField("  Y  ");
            JTextField ID = new JTextField(" ID ");
            String title = "Insert the X, Y, ID values to add to the new Node\nIn order to finish click on the OK button.\n";
            xCoordination.setToolTipText("Enter the X coordination");
            yCoordination.setToolTipText("Enter the y coordination");
            ID.setToolTipText("Enter the ID number");
            Object[] options = {title, "\n", xCoordination, yCoordination, ID};

            JOptionPane j = new JOptionPane();
            j.setMessage(options);
            j.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = j.createDialog(null, "Add new Node");
            dialog.setVisible(true);

            double x;
            double y;
            int id;
            try {
                x = Double.parseDouble(xCoordination.getText());
                y = Double.parseDouble(yCoordination.getText());
                id = Integer.parseInt(ID.getText());
                NodeData newNode = new Node(x, y, id);
                this.graph.addNode(newNode);
            }
            catch (NumberFormatException ex){
                JOptionPane.showOptionDialog(null,
                        "Wrong input!\nPlease enter only numeric values.",
                        "INPUT ERROR!",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        null,
                        null);
            }
            System.out.println("Node added");
        }

        else if (removeEdgeItem.equals(event) || RE.equals(event)) {
            JTextField srcText = new JTextField("Source");
            JTextField destText = new JTextField("Destination");
            String title = """
                    Insert the Source Node and the Destination Node
                    values of the Edge you would like to delete.
                    In order to finish click on the OK button.
                    """;
            srcText.setToolTipText("Enter the Source Node");
            destText.setToolTipText("Enter the Destination Node");
            Object[] options = {title, "\n", srcText, destText};

            JOptionPane j = new JOptionPane();
            j.setMessage(options);
            j.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = j.createDialog(null, "Remove Edge");
            dialog.setVisible(true);

            int src;
            int dest;
            try {
                src = Integer.parseInt(srcText.getText());
                dest = Integer.parseInt(destText.getText());
                try {
                    this.graph.removeEdge(src, dest);
                }
                catch (NullPointerException nep){
                    JOptionPane.showOptionDialog(null,
                            "Wrong input!\nThe Graph does not contain\nthe Edge between: " + src + "->" + dest + ".",
                            "INPUT ERROR!",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.ERROR_MESSAGE,
                            null,
                            null,
                            null);
                }
            }
            catch (NumberFormatException ex){
                JOptionPane.showOptionDialog(null,
                        "Wrong input!\nPlease enter only numeric values.",
                        "INPUT ERROR!",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        null,
                        null);
            }
            System.out.println("Edge removed");
        }

        else if (removeNodeItem.equals(event) || RN.equals(event)) {
            JTextField ID = new JTextField(" ID ");
            String title = """
                    Insert the ID number of the Node
                    you would like to delete.
                    In order to finish click on the OK button.
                    """;
            ID.setToolTipText("Enter the ID number");
            Object[] options = {title, "\n", ID};

            JOptionPane j = new JOptionPane();
            j.setMessage(options);
            j.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = j.createDialog(null, "Remove Node");
            dialog.setVisible(true);

            int id;
            try {
                id = Integer.parseInt(ID.getText());
                try {
                    this.graph.removeNode(id);
                }
                catch (NullPointerException nep){
                    JOptionPane.showOptionDialog(null,
                            "Wrong input!\nThe Graph does not contain\n Node: " + id + ".",
                            "INPUT ERROR!",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.ERROR_MESSAGE,
                            null,
                            null,
                            null);
                }
            }
            catch (NumberFormatException ex){
                JOptionPane.showOptionDialog(null,
                        "Wrong input!\nPlease enter only numeric values.",
                        "INPUT ERROR!",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        null,
                        null);
            }
            System.out.println("Node removed");
        }

        else if (isConnectedItem.equals(event) || IC.equals(event)) {
            boolean ans = algo.isConnected();
            String output = ans? "This graph is strongly connected!" : "This graph is not strongly connected!";
            JOptionPane.showOptionDialog(null,
                    output,
                    "IsConnected",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    null,
                    null);
            System.out.println(ans);
//            this.ta.append("\n" + ans);
            this.outputText += output;
//             updateTerminal(outputText);
//            this.ta.setRows(this.outputText.length());
            this.ta.append("\n" + "This graph is not strongly connected!");
//            this.outputText = this.ta.getText();
//            this.ta.setText(this.outputText + "hello");
//            this.ta.setForeground(Color.white);
//            this.outputPanel.repaint();
        }

        else if (shortestPath.equals(event) || SP.equals(event)) {
            JTextField srcText = new JTextField("Source");
            JTextField destText = new JTextField("Destination");
            String title = """
                    Insert the Source Node and the Destination
                    Node values of the Path you seek.
                    In order to finish click on the OK button.
                    """;
            srcText.setToolTipText("Enter the Source Node");
            destText.setToolTipText("Enter the Destination Node");
            Object[] options = {title, "\n", srcText, destText};

            JOptionPane j = new JOptionPane();
            j.setMessage(options);
            j.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = j.createDialog(null, "Shortest Path");
            dialog.setVisible(true);

            int src;
            int dest;
            try {
                src = Integer.parseInt(srcText.getText());
                dest = Integer.parseInt(destText.getText());
                if(graph.getNodes().containsKey(src)){
                    if(graph.getNodes().containsKey(dest)){
                        double sp = algo.shortestPathDist(src, dest);
                        this.mainPanel.setPath(src,dest);
                        this.mainPanel.setDest(dest);
                        this.mainPanel.setSrc(src);
                        this.mainPanel.setPathActivated(true);
//                        System.out.println(sp);
                    }
                    else{
                        JOptionPane.showOptionDialog(null,
                                "Wrong input!\nThe destination entered does not appear in the Graph.",
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
                            "Wrong input!\nThe source entered does not appear in the Graph.",
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
                        "Wrong input!\nPlease enter only numeric values.",
                        "INPUT ERROR!",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        null,
                        null);
            }
        }

        else if (centerItem.equals(event) || CE.equals(event)) {
            this.mainPanel.setCenterActivated(true);
            repaint();
            this.colored = true;
            JOptionPane.showOptionDialog(null,
                    "The center of this Graph is Node number: " + this.mainPanel.getCenter().getKey() + ".\n" +
                            "The Node Color is Red and The index is colored in Yellow.",
                    "Center of Graph",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    null,
                    null);
            System.out.println(this.mainPanel.getCenter());
        }

        else if (TSPItem.equals(event) || TSP.equals(event)) {
            int choose = chooseInputTSPState();
            LinkedList<NodeData> cities = new LinkedList<>();
            boolean citiesInitiated = false;
            // Case 1 -> one long string.
            if(choose == 0){
                while (!citiesInitiated) {
                    String explanation = """
                            Enter a String of all the Nodes
                            you would like to check TSP Algorithm on.
                            Separate each Node with a single Space Key.""";
                    String input = JOptionPane.showInputDialog(explanation, "Enter the Nodes");
                    if (input == null) {
                        break;
                    }
                        String[] nodes = input.split(" ");
//                System.out.println(Arrays.toString(nodes));
                        for (String n : nodes) {
//                    cities.add(this.graph.getNode(Integer.parseInt(n)));
                            int ID = -1;
                            try {
                                ID = Integer.parseInt(n);
                                if (graph.getNodes().containsKey(ID)) {
                                    cities.add(this.graph.getNode(ID));
                                } else {
                                    JOptionPane.showOptionDialog(null,
                                            "Wrong input!\nThe ID: " + n + " does not appear in the Graph.",
                                            "ID INPUT ERROR!",
                                            JOptionPane.DEFAULT_OPTION,
                                            JOptionPane.WARNING_MESSAGE,
                                            null,
                                            null,
                                            null);
                                    break;
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showOptionDialog(null,
                                        "Wrong input!\nPlease enter only numeric values.",
                                        "INPUT ERROR!",
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.ERROR_MESSAGE,
                                        null,
                                        null,
                                        null);
                                break;
                            }
                        }
                        if (cities.size() == nodes.length) {
                            citiesInitiated = true;
                        }
//                System.out.println("NODES\n" + cities);
//                System.out.println("CITIES\n" + this.algo.tsp(cities));
                        this.mainPanel.setPathByNodesTSP(this.algo.tsp(cities));
                        this.mainPanel.setPathByNodesTSPActivated(true);
                }
            }

            // Case 2 -> each time one Node.
            else {
                JTextField input = new JTextField("Enter Node");
                Object[] options = {"Insert the first number(ID) of the Node you want to add:", input, "When finished type DONE"};
                JOptionPane j = new JOptionPane();
                j.setMessage(options);
                j.setMessageType(JOptionPane.QUESTION_MESSAGE);
                JDialog dialog = j.createDialog(null, "Add Nodes to List");
                dialog.setVisible(true);

                options[0] = "Insert the number(ID) of the Node you want to add:";
                String text = input.getText().toUpperCase(Locale.ROOT);
                while(!text.equals("DONE")){
                    int ID = -1;
                    try{
                        ID = Integer.parseInt(input.getText());
                        if(graph.getNodes().containsKey(ID)){
                            cities.add(this.graph.getNode(ID));
                        }
                        else{
                            JOptionPane.showOptionDialog(null,
                                    "Wrong input!\nThe ID entered does not appear in the Graph.",
                                    "ID INPUT ERROR!",
                                    JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE,
                                    null,
                                    null,
                                    null);
                        }
                    }
                    catch (NumberFormatException ex){
                        JOptionPane.showOptionDialog(null,
                                "Wrong input!\nPlease enter only numeric values.",
                                "INPUT ERROR!",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.ERROR_MESSAGE,
                                null,
                                null,
                                null);
                    }
                    input.setText("Enter Node");
                    j = new JOptionPane();
                    j.setMessage(options);
                    j.setMessageType(JOptionPane.QUESTION_MESSAGE);
                    dialog = j.createDialog(null, "Add Nodes to List");
                    dialog.setVisible(true);
                    text = input.getText().toUpperCase(Locale.ROOT);
                }
            }
            System.out.println("TSP activated");
        }
        if(!this.colored){repaint();}
    }

    private int chooseInputTSPState(){
        String[] options = {"one String input", "each time one String"};
        String message = """
                Choose how you would like to type the List:
                one String input = you will write the list as a single String with spaces between each Node.
                each time one string = you will write each time one city until finished.
                """;
        int ans = JOptionPane.showOptionDialog(null,
                message,
                "CHOOSE HOW TO INPUT THE TSP LIST",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                null);
        System.out.println(ans);
        return ans;
    }


}
