package uk.ac.ed.inf.powergrab;

import java.awt.*;

public class Stateless extends Drone {


    public Stateless(Position latlong, int seed) {
        super(latlong, seed);
        //System.out.println("drone is stateless");
    }

    public double distance(Position position, Position location) {
        double x1 = position.latitude;
        double y1 = position.longitude;
        double x2 = location.latitude;
        double y2 = location.longitude;
        double d = Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2));
        return d;
    }

    public void strategy() {
        for (Direction direction: Direction.values()){
            Position p1 = this.currentPosition.nextPosition(direction);
            for (Stations s : Map.stations) {
                if (distance(p1, s.location) <= 0.00025) {
                    coin_check(direction, s);

                }
                else if (distance(p1, s.location) > 0.00025) {
                    movement(Direction.values()[rnd.nextInt(Direction.values().length)]);
                    coin_check(direction, s);
                }
            }

        }
    }

    private void coin_check(Direction direction, Stations s) {
        if (s.coins > 0) {

            System.out.println("initial: " + this.currentPosition.toString());
            movement(direction);
            this.coins += s.coins;
            this.power += s.power;
            s.power = 0;
            s.coins = 0;
            System.out.println("after method call: " + this.currentPosition.toString());
            System.out.println("drone coins = " + this.coins);
            System.out.println("drone power = " + this.power);
            System.out.println(direction);
            System.out.println("station coins = " + s.coins);

        }
    }

    public void callStrategy() {
        while (this.moves > 0 || this.power > 0) {
            strategy();
        }
    }


}
