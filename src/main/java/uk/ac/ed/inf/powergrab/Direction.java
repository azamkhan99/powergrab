package uk.ac.ed.inf.powergrab;


enum Direction {
		N(0),
		NNE(1),
		NE(2),
		ENE(3),
		E(4),
		ESE(5),
		SE(6),
		SSE(7),
		S(8),
		SSW(9),
		SW(10),
		WSW(11),
		W(12),
		WNW(13),
		NW(14),
		NNW(15);
	
	private final Double angle;
	
	private Direction(int angle) {
		this.angle = angle * Math.PI/8;
	}
	
	
		
}	
	 
	
	
	


