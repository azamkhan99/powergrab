package uk.ac.ed.inf.powergrab;


import java.util.Random;

public class Drone {

    float coins;
    int moves;
    double power;
    Position current;
    Random rnd;

    public Drone(Position latlong, int seed){
        this.current = latlong;
        this.rnd = new Random(seed);
        this.coins = 0;
        this.moves = 250;
        this.power = 250.0;

    }

    public void movement(Direction direction) {
        this.moves -= 1;
        this.power -= 1.25;
        this.current = current.nextPosition(direction);
        System.out.println(this.power);
    }

}
