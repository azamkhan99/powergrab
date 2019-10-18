package uk.ac.ed.inf.powergrab;

public class Stateless extends Drone {

    Position potential;

    public Stateless(Position latlong, int seed) {
        super(latlong, seed);
        //System.out.println("drone is stateless");

    }

    public void strategy() {
        for (Direction direction: Direction.values()){
            potential = potential.nextPosition(direction);


        }
    }


}
