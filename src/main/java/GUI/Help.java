package GUI;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import javax.swing.text.SimpleAttributeSet;
import java.awt.*;
import java.io.FileReader;
import java.util.Scanner;

public class Help {
    private JFrame frame;
    JLabel label, label2, label3;
    JTextArea JTA, JTA2, JTA3;

    // state -> 1 = tutorial , 2 = shortcuts
    public Help(int state) {
        try {
            FileReader readTextFile = new FileReader("src/main/java/GUI/icons/ShortcutsFile.txt");

            Scanner fileReaderScan = new Scanner(readTextFile);

            String storeAllString = "";

            while (fileReaderScan.hasNextLine()) {
                String temp = fileReaderScan.nextLine() + "\n";

                storeAllString = storeAllString + temp;
            }

            this.JTA = new JTextArea(storeAllString);
            JTA.setLineWrap(true);
            JTA.setWrapStyleWord(true);
            JScrollPane scrollBarForTextArea = new JScrollPane(JTA, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            this.frame = new JFrame("Open text file into JTextArea");
            frame.add(scrollBarForTextArea);
            this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.frame.setSize(new Dimension(650, 800));
            this.frame.setLocationRelativeTo(null);
            this.frame.setResizable(false);
            this.frame.setVisible(true);
        } catch (Exception exception) {
            //Print Error in file processing if it can't process your text file
            System.out.println("Error in file processing");
        }
    }
    //            String title;
//            String h1, h2, h3, h4;
//            String b1, b2, b3, b4;
//
//            title = "                                              Shortcuts";
//
//            h1 = "   1 - File menu";
//            h2 = "   2 - Edit menu";
//            h3 = "   3 - Algorithms menu";
//            h4 = "   4 - View menu";
//
//            b1 = """
//					         * Load a File                                ALT+F+L
//					         * Save a File                                ALT+F+S
//					         * Clear the Frame                            ALT+F+C
//					         * Reset to the last Graph loaded             ALT+F+R
//					         * Exit the program                           ALT+F+E
//
//																				""";
//            b2 = """
//                             * Add new Node                               ALT+E+A
//					         * Add new Edge                               ALT+E+S
//					         * Remove  Node                               ALT+E+D
//					         * Remove  Edge                               ALT+E+F
//
//																				""";
//            b3 = """
//                             * isConnected                                ALT+A+I
//					         * ShortestPath                               ALT+A+S
//					         * Center of the Graph                        ALT+A+C
//					         * TSP                                        ALT+A+T
//
//																				""";
//            b4 = """
//					    *        Hide buttons bar                           ALT+V+H
//                        *        Show buttons bar                           ALT+V+S
//						*        Set Full Screen                            ALT+V+F
//						*        Set Costume scale                           ALT+V+C""";
//
//            JLabel header = new JLabel(title);
//            header.setFont(new Font("david", Font.BOLD, 35));
//            header.setForeground(Color.BLUE);
//            header.setVisible(true);
//            this.frame.add(header, BorderLayout.NORTH);
//
//            JTextArea H1, H2, H3, H4, B1, B2, B3, B4;
//            H1 =  new JTextArea();
//            H2 =  new JTextArea();
//            H3 =  new JTextArea();
//            H4 =  new JTextArea();
//
//            B1 =  new JTextArea();
//            B2 =  new JTextArea();
//            B3 =  new JTextArea();
//            B4 =  new JTextArea();
//
//            H1.setText(h1);
//            H1.setEditable(false);
//            H1.setFont(new Font("david", Font.BOLD, 20));
//            H1.setLineWrap(true);
//            H1.setWrapStyleWord(true);
//            H1.setVisible(true);
//            this.frame.add(H1);
//
//            B1.setText(b1);
//            B1.setEditable(false);
//            B1.setFont(new Font("david", Font.BOLD, 15));
//            B1.setLineWrap(true);
//            B1.setWrapStyleWord(true);
//            B1.setVisible(true);
//            this.frame.add(B1);
//
//
//            H2.setText(h2);
//            H2.setEditable(false);
//            H2.setFont(new Font("david", Font.BOLD, 20));
//            H2.setLineWrap(true);
//            H2.setWrapStyleWord(true);
//            H2.setVisible(true);
//            this.frame.add(H2);
//
//
//            B2.setText(b2);
//            B2.setEditable(false);
//            B2.setFont(new Font("david", Font.BOLD, 15));
//            B2.setLineWrap(true);
//            B2.setWrapStyleWord(true);
//            B2.setVisible(true);
//            this.frame.add(B2);
//
//            H3.setText(h3);
//            H3.setEditable(false);
//            H3.setFont(new Font("david", Font.BOLD, 20));
//            H3.setLineWrap(true);
//            H3.setWrapStyleWord(true);
//            H3.setVisible(true);
//            this.frame.add(H3);
//
//
//            B3.setText(b3);
//            B3.setEditable(false);
//            B3.setFont(new Font("david", Font.BOLD, 15));
//            B3.setLineWrap(true);
//            B3.setWrapStyleWord(true);
//            B3.setVisible(true);
//            this.frame.add(B3);
//
//            H4.setText(h4);
//            H4.setEditable(false);
//            H4.setFont(new Font("david", Font.BOLD, 20));
//            H4.setLineWrap(true);
//            H4.setEditable(false);
//            H4.setWrapStyleWord(true);
//            H4.setVisible(true);
//            this.frame.add(H4);
//
//            B4.setText(b4);
//            B4.setEditable(false);
//            B4.setFont(new Font("david", Font.BOLD, 15));
//            B4.setLineWrap(true);
//            B4.setEditable(false);
//            B4.setWrapStyleWord(true);
//            B4.setVisible(true);
//            this.frame.add(B4);
}
