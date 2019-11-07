package uk.ac.ed.inf.powergrab;

import java.util.*;

public class Stateless extends Drone {


    public Stateless(Position latlong, int seed, Map map) {
        super(latlong, seed, map);
    }


    public double[] potential_gain(){

        double[] coin_array = new double[16];

        for (Direction direction : Direction.values())
        {
            Position p1 = this.currentPosition.nextPosition(direction);
            for (Stations s : this.map.stations)
            {
                if (!p1.inPlayArea())
                {
                    coin_array[direction.ordinal()] = Double.NEGATIVE_INFINITY;
                }
                else if (withinStation(p1, s))
                {
                    coin_array[direction.ordinal()] = s.coins;
                }
            }
        }
        //System.out.println(Arrays.toString(coin_array));
        return coin_array;
    }


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

    public void goHere() { //moves in chosen direction
        int d = best_gain(potential_gain());
        System.out.println("direction chosen: " + Direction.values()[d]);
        movement(Direction.values()[d]);
    }


    public void strategy() {
        goHere();
    }


    public void callStrategy() {

        do {
            System.out.println("\n MOVE: " + (250 - this.moves) + " POWER: " + this.power + " COINS: " + this.coins);
            strategy();

        } while (this.moves > 0 && this.power > 0);

        System.out.println("\nDrone coins = " + this.coins);
        System.out.println("Drone power = " + this.power);
    }



}




