package GUI;

import api.MyGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class MyPanel extends JPanel {
    MyGraph graph;

    public MyPanel(MyGraph g) {
        graph = g;
        this.setPreferredSize(new Dimension(600, 600));
    }

    public void paint(Graphics graphics) {
        int w = getWidth();
        int h = getHeight();
        double theta, x, y;
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setPaint(Color.blue);
        double x1 = w * 3 / 24, y1 = h * 3 / 32, x2 = w * 11 / 24, y2 = y1;
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        // draw this arrow head at point x2, y2 and measure
        // angle theta relative to same point, ie, y2 - and x2 -
        theta = Math.atan2(y2 - y1, x2 - x1);
        drawArrow(g2d, theta, x2, y2);

        x1 = w * 3 / 8;
        y1 = h * 13 / 15;
        x2 = w * 2 / 3;
        y2 = y1;
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        theta = Math.atan2(y1 - y2, x1 - x2);
        drawArrow(g2d, theta, x1, y1);

        g2d.setPaint(Color.red);
        x1 = w * 3 / 24;
        y1 = h * 4 / 32;
        x2 = x1;
        y2 = h * 18 / 32;
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        theta = Math.atan2(y2 - y1, x2 - x1);
        drawArrow(g2d, theta, x2, y2);

        g2d.setPaint(Color.orange);
        x1 = w * 5 / 32;
        y1 = h * 27 / 32;
        x2 = w * 27 / 32;
        y2 = h * 5 / 32;
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        theta = Math.atan2(y2 - y1, x2 - x1);
        drawArrow(g2d, theta, x2, y2);

        g2d.setPaint(Color.green.darker());
        x1 = w / 2;
        y1 = h / 2;
        x2 = w * 27 / 32;
        y2 = h * 27 / 32;
        g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        theta = Math.atan2(y2 - y1, x2 - x1);
        drawArrow(g2d, theta, x2, y2);
        g2d.setStroke(new BasicStroke(3));
    }

    private void drawArrow(Graphics2D g2, double theta, double x0, double y0) {
        int barb = 8;
        double phi = Math.PI / 6;
        double x = x0 - barb * Math.cos(theta + phi);
        double y = y0 - barb * Math.sin(theta + phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
        x = x0 - barb * Math.cos(theta - phi);
        y = y0 - barb * Math.sin(theta - phi);
        g2.draw(new Line2D.Double(x0, y0, x, y));
    }

}
