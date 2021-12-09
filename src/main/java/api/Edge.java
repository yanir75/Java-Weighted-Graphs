package api;

public class Edge implements EdgeData{
    private final int src;
    private final int dest;
    private final double weight;
    private String info;

    // need to fill

    public Edge(int src, int dest, double weight){
        if(weight<=0){
            throw new RuntimeException("weight must be positive");
        }
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }
    public Edge(EdgeData e){
        this.src = e.getSrc();
        this.dest = e.getDest();
        this.weight = e.getWeight();
        this.tag=e.getTag();
        this.info=e.getInfo();
    }
    public Edge copy()
    {
        Edge e = new Edge(src,dest,weight);
        e.info=info;
        return e;
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

//    @Override
//    public String toString() {
//        return "\n{src = " + src +
//                ", dest = " + dest +
//                ", weight = " + weight +
//                "}";
//    }
@Override
public String toString(){
        return "{\n"+'"'+"src"+'"'+": "+src+",\n"+'"'+"w"+'"'+": "+weight+",\n"+'"'+"dest"+'"'+": "+dest+"\n}";
}

    public static void main(String[] args) {
        Edge e = new Edge(5,5,5);
        System.out.println(e.toString());
    }
}
