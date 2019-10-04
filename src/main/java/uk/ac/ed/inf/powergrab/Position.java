package uk.ac.ed.inf.powergrab;

public class Position {
	public double latitude;
	public double longitude;


	public Position(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Position nextPosition(Direction direction) {
	    Position pos;
        pos = new Position(this.latitude + 0.0003*(Math.cos(direction.angle)), this.longitude + 0.0003*(Math.sin(direction.angle)));
        return pos;
	}
	
	public boolean inPlayArea() {

		boolean horizontal = (this.latitude > 55.942617) && (this.latitude < 55.946233);
		boolean vertical = (this.longitude > -3.19247) && (this.longitude < -3.184319);
		
		return horizontal && vertical;
		}
	}
