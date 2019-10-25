package uk.ac.ed.inf.powergrab;


import java.util.Random;

public class Drone {

    double coins;
    int moves;
    double power;
    Position currentPosition;
    Random rnd;

    public Drone(Position latlong, int seed){
        this.currentPosition = latlong;
        this.rnd = new Random(seed);
        this.coins = 0.0;
        this.moves = 250;
        this.power = 250.0;

    }

    /*public boolean[] ChecklegalMoves() {
        boolean[] legalMoves = new boolean[16];
        for (Direction direction: Direction.values()) {}
        return legalMoves;
    }*/


    public void movement(Direction direction) {

        this.moves -= 1;
        this.power -= 1.25;
        this.currentPosition = currentPosition.nextPosition(direction);




    }

}
