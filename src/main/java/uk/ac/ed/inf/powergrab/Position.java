package uk.ac.ed.inf.powergrab;

public class Position {
	public double latitude;
	public double longitude;
	
	
	public Position(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		
	}

	public Position nextPosition(Direction direction) {
		Position pos  = new Position(0,0);
		if (direction == Direction.N) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(0))), this.longitude + 0.0003*(Math.sin(Math.toRadians(0))));
		}
		
		else if (direction == Direction.S) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(180))), this.longitude + 0.0003*(Math.sin(Math.toRadians(180))));
		}
		else if (direction == Direction.E) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(90))), this.longitude + 0.0003*(Math.sin(Math.toRadians(90))));
		}
		else if (direction == Direction.W) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(270))), this.longitude + 0.0003*(Math.sin(Math.toRadians(270))));
		}
		else if (direction == Direction.NNE) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(22.5))), this.longitude + 0.0003*(Math.sin(Math.toRadians(22.5))));
		}
		else if (direction == Direction.SSW) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(202.5))), this.longitude + 0.0003*(Math.sin(Math.toRadians(202.5))));
		}
		else if (direction == Direction.NE) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(45))), this.longitude + 0.0003*(Math.sin(Math.toRadians(45))));
		}
		else if (direction == Direction.ENE) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(67.5))), this.longitude + 0.0003*(Math.sin(Math.toRadians(67.5))));
		}
		else if (direction == Direction.ESE) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(112.5))), this.longitude + 0.0003*(Math.sin(Math.toRadians(112.5))));
		}
		else if (direction == Direction.SE) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(135))), this.longitude + 0.0003*(Math.sin(Math.toRadians(135))));
		}
		else if (direction == Direction.SSE) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(157.5))), this.longitude + 0.0003*(Math.sin(Math.toRadians(157.5))));
		}
		else if (direction == Direction.SW) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(225))), this.longitude + 0.0003*(Math.sin(Math.toRadians(225))));
		}
		else if (direction == Direction.WSW) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(247.5))), this.longitude + 0.0003*(Math.sin(Math.toRadians(247.5))));
		}
		else if (direction == Direction.WNW) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(292.5))), this.longitude + 0.0003*(Math.sin(Math.toRadians(292.5))));
		}
		else if (direction == Direction.NW) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(315))), this.longitude + 0.0003*(Math.sin(Math.toRadians(315))));
		}
		else if (direction == Direction.NNW) {
			pos = new Position(this.latitude + 0.0003*(Math.cos(Math.toRadians(337.5))), this.longitude + 0.0003*(Math.sin(Math.toRadians(337.5))));
		}
		return pos;
	}
	
	public boolean inPlayArea() {
		boolean horizontal = this.latitude > 55.942617 && this.latitude < 55.946233;
		boolean vertical = this.longitude > -3.19247 && this.longitude < -3.184319;
		
		return horizontal && vertical;
		}
	}
