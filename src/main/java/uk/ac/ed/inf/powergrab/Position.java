package uk.ac.ed.inf.powergrab;


public class Position {
    public double latitude;
    public double longitude;
    public final double distance = 0.0003;

    public Position(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String toString() {
        return String.format("latitude: " + latitude + "	longitude: " + longitude);
    }

    public Position nextPosition(Direction direction) {
        Position pos;
        pos = new Position(this.latitude + distance * (Math.cos(direction.angle)), this.longitude + distance * (Math.sin(direction.angle)));
        return pos;
    }

    public boolean inPlayArea() {
        //A check to see if the drone is in the legal playing area
        boolean horizontal = (this.latitude > 55.942617) && (this.latitude < 55.946233);
        boolean vertical = (this.longitude > -3.19247) && (this.longitude < -3.184319);

        return horizontal && vertical;
    }

    public boolean isEquals(Position p) {
        return this.latitude == p.latitude && this.longitude == p.longitude;
    }

}
