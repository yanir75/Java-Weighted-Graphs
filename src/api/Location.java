package api;
public class Location implements GeoLocation{
    private int x;
    private int y;
    private int z;
    public Location(int x,int y,int z){
        this.x =x;
        this.y =y;
        this.z =z;
    }


    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double distance(GeoLocation g) {
        double val_x = (this.x - g.x()) * (this.x - g.x());
        double val_y = (this.y - g.y()) * (this.y - g.y());
        return Math.sqrt(val_x + val_y);
    }
}
