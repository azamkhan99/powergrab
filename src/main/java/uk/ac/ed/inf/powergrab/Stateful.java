package uk.ac.ed.inf.powergrab;

import java.util.ArrayList;
import java.util.List;

public class Stateful extends Drone {

    private ArrayList<Station> listClosest = new ArrayList<>();
    private ArrayList<Station> positiveStations = new ArrayList<>();
    private ArrayList<Station> negativeStations = new ArrayList<>();

//
    Stateful(Position latLong, int seed, Map map) {

        super(latLong, seed, map);
        for (Station s : map.getStations()) {
            if (s.getCoins() > 0) positiveStations.add(s);
            if (s.getCoins() < 0) negativeStations.add(s);
        }

    }


    //Check to see if a drone is in the vicinity of a negatively charged station
    private boolean withinNeg(Direction d) {
        for (Station s : negativeStations) {
            if (withinStation(this.getCurrentPosition().nextPosition(d), s)) {
                return true;
            }
        }
        return false;
    }


    //Returns the station with the smallest distance to a position from a list of station objects
    private Station closest(Position p, ArrayList<Station> ls) {
        if (ls.isEmpty()) return null;
        Station nearest = ls.get(0);
        for (Station s : ls) {
            if (distance(p, s.getLocation()) < distance(p, nearest.getLocation())) {
                nearest = s;
            }
        }
        return nearest;
    }


    //Returns the direction to move towards
    private Direction getDirection(Station s) {
        double in = Math.toDegrees(Math.atan2((s.getLocation().longitude - this.getCurrentPosition().longitude),
                (s.getLocation().latitude - this.getCurrentPosition().latitude)));
        int rad = (int) Math.round(((in % 360) / 22.5)) % 16;
        if (rad < 0) {
            rad = rad + 16;

        }

        return Direction.values()[rad];
    }


    //Returns the index of the direction enum to avoid
    private int avoid(double[] coinage) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < coinage.length; i++) {
            if (coinage[i] == 0.0) {
                indices.add(i);
            }
        }
        int randInd = getRnd().nextInt(indices.size());
        return indices.get(randInd);
    }

    //Returns an array of directions that the drone should move towards if it encounters a negative charging station
    private double[] reroute() {
        double[] dists = new double[16];
        double dist;
        for (Direction d : Direction.values()) {
            Position p1 = this.getCurrentPosition().nextPosition(d);
            dist = distance(p1, closest(p1, positiveStations).getLocation());
            dists[d.ordinal()] = dist;
            if (!p1.inPlayArea() || withinNeg(d)) {
                dist = Double.POSITIVE_INFINITY;
                dists[d.ordinal()] = dist;
            }
        }
        return dists;
    }


    //Moves in random directions
    private void moveRandom() {
        int d = avoid(potentialGain(negativeStations));
        movement(Direction.values()[d]);

    }

    //Moves to avoid negatively charged stations
    private void moveToAvoid() {
        int d = minIndex(reroute());
        movement(Direction.values()[d]);
    }


    //Check to see if the drone is going back and forth between two points
    private boolean isStuck() {
        if (getPosList().size() >= 3)
            return (getPosList().get(getPosList().size() - 1).isEquals(getPosList().get(getPosList().size() - 3)));

        else return false;

    }

    //Check to see if a move is similar to the one 20 moves ago
    private boolean inLoop() {
        if (listClosest.size() >= 20 && positiveStations.size() > 1)
            return (listClosest.get(listClosest.size() - 1).isEquals(listClosest.get(listClosest.size() - 20)));

        else return false;
    }


    //Moves towards closest positive station and if stuck in a loop, move in a random legal direction
    private void moveTowards(Direction d) {
        if (this.getCurrentPosition().nextPosition(d).inPlayArea() && !isStuck()
                && (!withinNeg(d))) {
            movement(d);
        } else if (isStuck()) {
            moveRandom();
        } else {
            moveToAvoid();
        }
    }


    //Removes a station from the list which has already been visited
    private void removeStation(ArrayList<Station> ls) {
        ls.removeIf(stations -> stations.getCoins() == 0);
    }


    //Method to make the drone move towards positive stations until all have been visited
    private void strategy() {
        Station nextStation = closest(this.getCurrentPosition(), positiveStations);
        listClosest.add(nextStation);
        if (nextStation == null) {
            moveRandom();
        } else if (inLoop()) {
            System.out.println("stuck in loop");
            moveTowards(getDirection(positiveStations.get(getRnd().nextInt(positiveStations.size()))));
            removeStation(positiveStations);
        } else {
            moveTowards(getDirection(nextStation));
            removeStation(positiveStations);
        }
    }


    //Method to make the drone move until it is out of power or moves.
    void strategyCall() {
        double tc = getMap().totalCoins();
        do {
            System.out.println("\n MOVE: " + (250 - this.getMoves()) + " POWER: " + this.getPower() + " COINS: " +
                    this.getCoins());

            strategy();

        } while (this.getMoves() > 0 && this.getPower() > 0);
        double percent = (this.getCoins() / tc * 100);
        System.out.println("\ntotal coins= " + tc + "\n" + "percent of coins collected: " + percent);
    }


}
