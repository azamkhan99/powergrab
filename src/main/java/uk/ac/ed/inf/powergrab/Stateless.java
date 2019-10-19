package uk.ac.ed.inf.powergrab;

import java.awt.*;

public class Stateless extends Drone {


    public Stateless(Position latlong, int seed) {
        super(latlong, seed);
        //System.out.println("drone is stateless");

    }

    public double distance(Position position,int t) {

        double d;
        double x1 = position.latitude;
        double y1 = position.longitude;
        double x2 = 0;
        double y2 = 0;

        d = Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2));
        //d = Math.sqrt(Math.pow((p1.latitude-Map.sml.get(i).get(1)),2) + Math.pow((p1.longitude-Map.sml.get(i).get(0)),2));
        return d;
    }




    public void strategy() {
        for (Direction direction: Direction.values()){
            Position p1 = this.current.nextPosition(direction);
            //System.out.println("latitude: " + p1.latitude + " longitude: " + p1.longitude);
            for (int i = 0; i < Map.sml.size(); i++) {
                if (Math.sqrt(Math.pow((p1.latitude-Map.sml.get(i).get(1)),2) + Math.pow((p1.longitude-Map.sml.get(i).get(0)),2)) <= 0.00025) {
                   // if (Map.scs.get(i) > 0) {}
                    movement(direction);

                }
            }



        }
    }


}
