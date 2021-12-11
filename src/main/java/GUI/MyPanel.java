package GUI;

import api.Algorithms;
import api.EdgeData;
import api.MyGraph;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Iterator;

public class MyPanel extends JPanel {
    Algorithms graph, copyOfGraph;
    double minX;
    double minY;
    double maxX;
    double maxY;

    public MyPanel(MyGraph g) {
        graph = new Algorithms();
        copyOfGraph = new Algorithms();
        graph.init(g);
        copyOfGraph.init(graph.copy());
        try {
            setMin();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Dimension scale = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)scale.width;
        int height = (int)scale.height;
        this.setPreferredSize(new Dimension((int)(width / 1.3), (int)(height / 1.3)));
        repaint();
    }

    public void paint(Graphics graphics) {
        double ABSx = Math.abs(minX - maxX);
        double ABSy = Math.abs(minY - maxY);
        double scaleX = (getWidth() / ABSx) * 0.7;
        double scaleY = (getHeight() / ABSy) * 0.7;
        int w = getWidth();
        int h = getHeight();
        int r = 5;
        double theta;
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setPaint(Color.blue);
        g2d.setStroke(new BasicStroke(1));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw all points a.k.a. Nodes.
        Iterator<NodeData> nodesIter = graph.getGraph().nodeIter();
        while (nodesIter.hasNext()) {
            g2d.setPaint(Color.blue);
            NodeData n = nodesIter.next();
            double x = (n.getLocation().x() - minX) * scaleX * 0.98 + 33;
            double y = (n.getLocation().y() - minY) * scaleY * 0.98 + 33;
            g2d.fillOval((int) x - 2, (int) y - 2, 10, 10);
        }

        // Draw all Edges.
        Iterator<EdgeData> edgesIter = graph.getGraph().edgeIter();
        while (edgesIter.hasNext()) {
            EdgeData e = edgesIter.next();
            g2d.setPaint(Color.black);
            double srcX = (graph.getGraph().getNode(e.getSrc()).getLocation().x() - minX) * scaleX + 30;
            double srcY = (graph.getGraph().getNode(e.getSrc()).getLocation().y() - minY) * scaleY + 30;
            double destX = (graph.getGraph().getNode(e.getDest()).getLocation().x() - minX) * scaleX + 30;
            double destY = (graph.getGraph().getNode(e.getDest()).getLocation().y() - minY) * scaleY + 30;
            double scaleFactor = 1;
            double scaleFactor1 = 8;

            int x1 = (int) srcX;
            int y1 = (int) srcY;
            int x2 = (int) destX;
            int y2 = (int) destY;
            g2d.setStroke(new BasicStroke(2));
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
            theta = Math.atan2(y2 - y1, x2 - x1);
            g2d.setStroke(new BasicStroke(2));
            g2d.setPaint(Color.RED);
            drawArrow(g2d, theta, x2, y2);
            x1 = (int) srcX + (int) (scaleX / scaleFactor1);
            y1 = (int) srcY + (int) (scaleY / scaleFactor1);
            x2 = (int) destX + (int) (scaleX / scaleFactor1);
            y2 = (int) destY + (int) (scaleY / scaleFactor1);
        }

        nodesIter = graph.getGraph().nodeIter();
        while (nodesIter.hasNext()) {
            NodeData n = nodesIter.next();
            double x = (n.getLocation().x() - minX) * scaleX * 0.98 + 33;
            double y = (n.getLocation().y() - minY) * scaleY * 0.98 + 33;
            String xs = "" + (Math.round(n.getLocation().x() * 10000d) / 10000d);
            String ys = "" + (Math.round(n.getLocation().y() * 10000d) / 10000d);
            String coordinate = "(" + xs + "," + ys + ")";
            g2d.setStroke(new BasicStroke(1));
            g2d.setPaint(Color.white);
            g2d.fillRect((int) (x - 1.2), (int) y - 20, 90, 12);
            g2d.setPaint(Color.black);
            g2d.drawRect((int) (x - 1.2), (int) y - 20, 90, 12);
            Font f = new Font("ariel", Font.BOLD, 9);
            g2d.setFont(f);
            g2d.drawString("v" + n.getKey() + coordinate, (int) x, (int) y - 10);
        }
    }


    public void setMin() throws Exception {
        minX = Integer.MAX_VALUE;
        minY = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        maxY = Integer.MIN_VALUE;
        Iterator<NodeData> minXIter = graph.getGraph().nodeIter();
        while (minXIter.hasNext()) {
            NodeData n = minXIter.next();
            if (n.getLocation().x() < minX) {
                minX = n.getLocation().x();
            }
        }
        Iterator<NodeData> minYIter = graph.getGraph().nodeIter();
        while (minYIter.hasNext()) {
            NodeData m = minYIter.next();
            if (m.getLocation().y() < minY) {
                minY = m.getLocation().y();
            }
        }
        Iterator<NodeData> maxXIter = graph.getGraph().nodeIter();
        while (maxXIter.hasNext()) {
            NodeData n = maxXIter.next();
            if (n.getLocation().x() > maxX) {
                maxX = n.getLocation().x();
            }
        }
        Iterator<NodeData> maxYIter = graph.getGraph().nodeIter();
        while (maxYIter.hasNext()) {
            NodeData m = maxYIter.next();
            if (m.getLocation().y() > maxY) {
                maxY = m.getLocation().y();
            }
        }
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
