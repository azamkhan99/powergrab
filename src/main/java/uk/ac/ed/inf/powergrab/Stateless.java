package uk.ac.ed.inf.powergrab;

import java.util.*;

public class Stateless extends Drone {


    public Stateless(Position latlong, int seed, Map map) {
        super(latlong, seed, map);
    }



    //Returns the index of the direction enum which gives the most coins
    public int best_gain(double[] coinage) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0;i < coinage.length;i++)
        {
            if (coinage[i] == 0.0)
            {
                indices.add(i);
            }
        }
        int dirInd;

            if (Arrays.stream(coinage).max().getAsDouble() < 0.0)
            {
                dirInd = maxIndex(coinage);
            }
            else if (Arrays.stream(coinage).max().getAsDouble() == 0.0)
            {
                int randInd = rnd.nextInt(indices.size());
                dirInd = indices.get(randInd);
            }
            else dirInd = maxIndex(coinage);

        return dirInd;
    }

    //moves in chosen direction
    public void goHere() { 
        int d = best_gain(potential_gain(this.map.stations));
        movement(Direction.values()[d]);
    }


  

    //Method to make the drone move until it is out of moves or power
    public void callStrategy() {

        do {
            System.out.println("\n MOVE: " + (250 - this.moves) + " POWER: " + this.power + " COINS: " + this.coins);
            goHere();

        } while (this.moves > 0 && this.power > 0);

        System.out.println("\nDrone coins = " + this.coins);
        System.out.println("Drone power = " + this.power);
    }



}




