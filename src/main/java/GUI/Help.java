package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.Scanner;

public class Help {
    private final JFrame frame;
    private JButton back;
    private JPanel panel;
    private JLabel title;
    private JTextArea JTA;

    // state -> 1 = tutorial , 2 = shortcuts
    public Help(int state) {
        this.frame = new JFrame("Shortcuts");
        this.panel = new JPanel();
        this.panel.setPreferredSize(new Dimension(600,35));
        this.JTA = new JTextArea();
        this.back = new JButton("BACK");
        this.back.addActionListener(e -> frame.dispose());
        this.panel.add(back);
        this.back.setVisible(true);
        this.panel.setVisible(true);
        try {
            FileReader readTextFile = new FileReader("src/main/java/GUI/icons/ShortcutsFile.txt");

            Scanner fileReaderScan = new Scanner(readTextFile);

            String storeAllString = "";

            while (fileReaderScan.hasNextLine()) {
                String temp = fileReaderScan.nextLine() + "\n";

                storeAllString = storeAllString + temp;
            }
            this.title = new JLabel();
            this.title.setText("\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\s\sShortcuts Help");
            this.title.setLayout(new BorderLayout());
            this.title.setFont(new Font("Garamond", Font.BOLD, 40));
            this.title.setBackground(Color.darkGray);
            this.title.setVisible(true);

            this.JTA.setFont(new Font("Garamond", Font.BOLD, 19));
            this.JTA.setText(storeAllString);
            this.JTA.setLineWrap(true);
            this.JTA.setWrapStyleWord(true);

            ImageIcon img = new ImageIcon("src/main/java/GUI/icons/graph.jpg");
            this.frame.setIconImage(img.getImage());
            this.frame.add(this.title, BorderLayout.NORTH);
            this.frame.add(JTA, BorderLayout.CENTER);
            this.frame.add(panel, BorderLayout.PAGE_END);
            this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.frame.setSize(new Dimension(620, 685));
            this.frame.setLocationRelativeTo(null);
            this.frame.setResizable(false);
            this.frame.setVisible(true);
            this.frame.setAlwaysOnTop(true);

        } catch (Exception exception) {
            //Print Error in file processing if it can't process your text file
            System.out.println("Error in file processing");
        }
    }
}
