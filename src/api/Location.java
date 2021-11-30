package api;
public class Location implements GeoLocation{
    private double x;
    private double y;
    private double z;
    public Location(double x, double y, double z){
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

    @Override
    public String toString() {
        return "(x:" + x + ", y:" + y + ", z:" + z + ')';
    }
}
