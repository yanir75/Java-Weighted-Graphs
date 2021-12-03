package api;

public class Edge implements EdgeData{
    private final int src;
    private final int dest;
    private final double weight;
    private String info;

    // need to fill

    public Edge(int src, int dest, double weight){
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
    int tag;
    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    @Override
    public String toString() {
        return "{src = " + src +
                ", dest = " + dest +
                ", weight = " + weight +
                '}';
    }
}
