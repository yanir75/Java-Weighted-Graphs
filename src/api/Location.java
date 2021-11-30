package api;
public class Location implements GeoLocation{
    private int _x;
    private int _y;
    private int _z;
    public Location(int x,int y,int z){
        _x=x;
        _y=y;
        _z=z;
    }
    @Override
    public double x() {
        return _x;
    }

    @Override
    public double y() {
        return _y;
    }

    @Override
    public double z() {
        return _z;
    }

    @Override
    public double distance(GeoLocation g) {
    return 0.0;
    }
}
