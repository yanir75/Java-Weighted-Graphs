package GUI;

import api.Algorithms;
import api.EdgeData;
import api.MyGraph;
import api.NodeData;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MyPanel extends JPanel {
    private Algorithms graph, copyOfGraph;
    private NodeData center;
    private boolean isCenterActivated, isPathActivated, isTSPActivated;
    private LinkedList<NodeData> pathByNodes;
    private List<NodeData> pathByNodesTSP;
    private int src, dest;
    double minX;
    double minY;
    double maxX;
    double maxY;

    public MyPanel(MyGraph g) {
        this.graph = new Algorithms();
        this.copyOfGraph = new Algorithms();
        this.graph.init(g);
        this.copyOfGraph.init(this.graph.copy());
        this.center = this.graph.center();
        this.isCenterActivated = false;
        this.isPathActivated = false;
        this.isTSPActivated = false;
        this.src = -1;
        this.dest = -1;
        this.pathByNodes = new LinkedList<>();
        this.pathByNodesTSP = new LinkedList<>();
        try {
            setMin();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Dimension scale = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)scale.width;
        int height = (int)scale.height;
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);
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
            g2d.setStroke(new BasicStroke(1));
            NodeData n = nodesIter.next();
            int width = 14;
            int height = 14;
            if(n.getKey() == this.center.getKey() && this.isCenterActivated){
                g2d.setPaint(new Color(255,0,0));
                g2d.setStroke(new BasicStroke(3));
                width = 18;
                height = 18;
            }
            double x = (n.getLocation().x() - minX) * scaleX * 0.98 + 33;
            double y = (n.getLocation().y() - minY) * scaleY * 0.98 + 33;
            g2d.fillOval((int) x - 3, (int) y - 3, width, height);
        }

        // Draw all Edges.
        Iterator<EdgeData> edgesIter = graph.getGraph().edgeIter();
        while (edgesIter.hasNext()) {
            EdgeData e = edgesIter.next();
            g2d.setPaint(Color.black);
            double srcX = (graph.getGraph().getNode(e.getSrc()).getLocation().x() - minX) * scaleX + 31;
            double srcY = (graph.getGraph().getNode(e.getSrc()).getLocation().y() - minY) * scaleY + 31;
            double destX = (graph.getGraph().getNode(e.getDest()).getLocation().x() - minX) * scaleX + 31;
            double destY = (graph.getGraph().getNode(e.getDest()).getLocation().y() - minY) * scaleY + 31;
            int x1 = (int) srcX;
            int y1 = (int) srcY;
            int x2 = (int) destX;
            int y2 = (int) destY;
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
            g2d.setStroke(new BasicStroke(3));
            g2d.setPaint(Color.black);
            drawArrowLine(g2d, x1, y1, x2, y2, 15, 7);
            double medX = middle(x1, x2);
            double medY = middle(y1, y2);
            g2d.setStroke(new BasicStroke(1));
            g2d.setPaint(Color.white);
            g2d.fillRect((int) (medX - 2), (int) medY - 10, 35, 15);
            g2d.setPaint(Color.black);
            g2d.drawRect((int) (medX - 2), (int) medY - 10, 35, 15);
            g2d.setFont(new Font("ariel", Font.BOLD, 9));
            g2d.drawString((Math.round(e.getWeight() * 10000d) / 10000d) + "", (int) medX, (int) medY);

        }

        nodesIter = graph.getGraph().nodeIter();
        while (nodesIter.hasNext()) {
            NodeData n = nodesIter.next();
            double x = (n.getLocation().x() - minX) * scaleX * 0.98 + 33;
            double y = (n.getLocation().y() - minY) * scaleY * 0.98 + 33;
//            String xs = "" + (Math.round(n.getLocation().x() * 10000d) / 10000d);
//            String ys = "" + (Math.round(n.getLocation().y() * 10000d) / 10000d);
//            String coordinate = "(" + xs + "," + ys + ")";
            g2d.setStroke(new BasicStroke(1));
            g2d.setPaint(Color.white);
            if(n.getKey() == this.center.getKey() && this.isCenterActivated){
                g2d.setPaint(Color.YELLOW);
                this.isCenterActivated = false;
            }
            Rectangle2D rec = new Rectangle2D.Double((int) (x - 3), (int) y - 25, 20, 20);
            double centerX = rec.getCenterX();
            double centerY = rec.getCenterY();
            g2d.fillRect((int) (x - 3), (int) y - 25, 20, 20);
            g2d.setPaint(Color.black);
            g2d.drawRect((int) (x - 3), (int) y - 25, 20, 20);
            g2d.setFont(new Font("ariel", Font.BOLD, 14));
//            g2d.drawString("v" + n.getKey() + coordinate, (int) x, (int) y - 10);
            g2d.drawString(n.getKey() + "", (int) centerX - 8, (int) centerY + 6);
        }
        int src = -1;
        int dest = -1;
        if(!this.pathByNodes.isEmpty()){
            src = this.pathByNodes.get(0).getKey();
            this.pathByNodes.remove(0);
        }
        while(this.pathByNodes.size() > 0){
            g2d.setPaint(new Color(0,204,0));
            dest = this.pathByNodes.get(0).getKey();
            this.pathByNodes.remove(0);
            double srcX = (graph.getGraph().getNode(src).getLocation().x() - minX) * scaleX + 30;
            double srcY = (graph.getGraph().getNode(src).getLocation().y() - minY) * scaleY + 30;
            double destX = (graph.getGraph().getNode(dest).getLocation().x() - minX) * scaleX + 30;
            double destY = (graph.getGraph().getNode(dest).getLocation().y() - minY) * scaleY + 30;
            int x1 = (int) srcX;
            int y1 = (int) srcY;
            int x2 = (int) destX;
            int y2 = (int) destY;
            g2d.setStroke(new BasicStroke(3));
            drawArrowLine(g2d, x1, y1, x2, y2, 15, 7);
            src = dest;
            if(this.pathByNodes.size() == 0){
                this.isPathActivated = false;
            }
        }

        src = -1;
        dest = -1;
//        System.out.println(this.pathByNodesTSP.toString());
        if(!this.pathByNodesTSP.isEmpty()){
            src = this.pathByNodesTSP.get(0).getKey();
            this.pathByNodesTSP.remove(0);
        }
        while(this.pathByNodesTSP.size() > 0){
            g2d.setPaint(new Color(255,51,255));
            dest = this.pathByNodesTSP.get(0).getKey();
            this.pathByNodesTSP.remove(0);
            double srcX = (graph.getGraph().getNode(src).getLocation().x() - minX) * scaleX + 30;
            double srcY = (graph.getGraph().getNode(src).getLocation().y() - minY) * scaleY + 30;
            double destX = (graph.getGraph().getNode(dest).getLocation().x() - minX) * scaleX + 30;
            double destY = (graph.getGraph().getNode(dest).getLocation().y() - minY) * scaleY + 30;
            int x1 = (int) srcX;
            int y1 = (int) srcY;
            int x2 = (int) destX;
            int y2 = (int) destY;
            g2d.setStroke(new BasicStroke(3));
            drawArrowLine(g2d, x1, y1, x2, y2, 15, 7);
            src = dest;
            if(this.pathByNodesTSP.size() == 0){
                this.isTSPActivated = false;
            }
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


    /**
     * Draw an arrow line between two points.
     * @param g the graphics component.
     * @param x1 x-position of first point.
     * @param y1 y-position of first point.
     * @param x2 x-position of second point.
     * @param y2 y-position of second point.
     * @param d  the width of the arrow.
     * @param h  the height of the arrow.
     * this code has been taken from:
     *          https://stackoverflow.com/questions/2027613/how-to-draw-a-directed-arrow-line-in-java,
     *          with some modifications to fit to out needs.
     */
    private void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] Xpoints = {x2, (int) xm, (int) xn};
        int[] Ypoints = {y2, (int) ym, (int) yn};
        g.drawLine(x1, y1, x2, y2);
        g.setColor(Color.black);
        g.fillPolygon(Xpoints, Ypoints, 3);
    }

    private double middle(double x1, double x2){
        return Math.abs((x2 + x1) / 2);
    }

    public Algorithms getGraph() {
        return graph;
    }

    public void setCenterActivated(boolean centerActivated) {
        isCenterActivated = centerActivated;
    }

    public void setPath(int src, int dest) {
        this.pathByNodes = (LinkedList<NodeData>) this.graph.shortestPath(src,dest);
//        System.out.println(this.pathByNodes);
    }

    public int getSrc() {
        return src;
    }

    public void setSrc(int src) {
        this.src = src;
    }

    public int getDest() {
        return dest;
    }

    public java.util.List<NodeData> getPathByNodesTSP() {
        return pathByNodesTSP;
    }

    public void setPathByNodesTSP(List<NodeData> path) {
//        System.out.println("THE PATH\n" + path);
//        this.pathByNodesTSP = new LinkedList<>();
//        this.pathByNodesTSP.addAll(path);
        this.pathByNodesTSP = path;
    }

    public void setDest(int dest) {
        this.dest = dest;
    }

    public void setPathActivated(boolean pathActivated) {
        isPathActivated = pathActivated;
    }

    public void setPathByNodesTSPActivated(boolean pathActivated) {
        this.isTSPActivated = pathActivated;
    }
    public NodeData getCenter() {
        return center;
    }
}
