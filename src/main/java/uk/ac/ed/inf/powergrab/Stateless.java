package uk.ac.ed.inf.powergrab;

import java.util.*;

public class Stateless extends Drone {


    Stateless(Position latLong, int seed, Map map) {
        super(latLong, seed, map);
    }


    //Returns the index of the direction enum which gives the most coins
    private int bestGain(double[] coinage) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < coinage.length; i++) {
            if (coinage[i] == 0.0) {
                indices.add(i);
            }
        }
        int dirInd;

        if (Arrays.stream(coinage).max().getAsDouble() < 0.0) {
            dirInd = maxIndex(coinage);
        } else if (Arrays.stream(coinage).max().getAsDouble() == 0.0) {
            int randInd = rnd.nextInt(indices.size());
            dirInd = indices.get(randInd);
        } else dirInd = maxIndex(coinage);

        return dirInd;
    }

    //moves in chosen direction
    private void goHere() {
        int d = bestGain(potentialGain(map.stations));
        movement(Direction.values()[d]);
    }


    //Method to make the drone move until it is out of moves or power
    void callStrategy() {

        do {
            System.out.println("\n MOVE: " + (250 - this.moves) + " POWER: " + this.power + " COINS: " + this.coins);
            goHere();

        } while (this.moves > 0 && this.power > 0);

        System.out.println("\nDrone coins = " + this.coins);
        System.out.println("Drone power = " + this.power);
    }


}




