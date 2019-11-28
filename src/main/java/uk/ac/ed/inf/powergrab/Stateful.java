package uk.ac.ed.inf.powergrab;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

public class Stateful extends Drone {

    private ArrayList<Stations> list_closest = new ArrayList<>();
    private ArrayList<Stations> positive_stations = new ArrayList<>();
    private ArrayList<Stations> negative_stations = new ArrayList<>();


    public Stateful(Position latlong, int seed, Map map) {

        super(latlong, seed, map );
        for (Stations s : map.stations)
        {
            if (s.coins > 0) positive_stations.add(s);
            if (s.coins < 0) negative_stations.add(s);
        }

    }



    //Check to see if a drone is in the vicinity of a negatively charged station
    public boolean withinNeg(Direction d) {
        for (Stations s: negative_stations)
        {
            if(withinStation(this.currentPosition.nextPosition(d),s))
            {
                return true;
            }
        }
        return false;
    }



    //Returns the station with the smallest distance to a position
    public Stations closest (Position p, ArrayList<Stations> ls) {
        if (ls.isEmpty()) return null;
        Stations nearest = ls.get(0);
        for (Stations s : ls)
        {
            if (distance(p, s.location) < distance(p, nearest.location))
            {
                nearest = s;
            }
        }
        //System.out.println("closest positive station: " + nearest.toString());
        return nearest;
    }




    //Returns the direction to move towards
    public Direction getDirection(Stations s) {
        double in = Math.toDegrees(Math.atan2((s.location.longitude - this.currentPosition.longitude),(s.location.latitude-this.currentPosition.latitude)));
        int rad = (int)Math.round(((in % 360) / 22.5)) % 16;
        if (rad < 0)
        {
            rad = rad + 16;

        }

        return Direction.values()[rad];
    }



    //Returns the index of the direction enum to avoid
    public int avoid(double[] coinage) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0;i < coinage.length;i++)
        {
            if (coinage[i] == 0.0)
            {
                indices.add(i);
            }
        }
        int randInd = rnd.nextInt(indices.size());
        return indices.get(randInd);
    }

    //Returns an array of directions that the drone should move towards if it encounters a negative charging station
    public double[] reroute () {
        double[] dists = new double[16];
        double dist;
        for (Direction d : Direction.values()) {
            Position p1 = this.currentPosition.nextPosition(d);
            dist = distance(p1, closest(p1, positive_stations).location);
            dists[d.ordinal()] = dist;
                if (!p1.inPlayArea() || withinNeg(d))
                {
                    dist = Double.POSITIVE_INFINITY;
                    dists[d.ordinal()] = dist;
                }
        }
        return dists;
    }



    //Moves in random directions
    public  void moveRandom() {
        int d = avoid(potential_gain(negative_stations));
        movement(Direction.values()[d]);

    }

    //Moves to avoid negatively charged stations
    public void moveToAvoid() {
        int d = minIndex(reroute());
        movement(Direction.values()[d]);
    }




    //Check to see if the drone is going back and forth between two points
    public boolean is_Stuck() {
        if (posList.size() >= 3)
            return (posList.get(posList.size()-1).isEquals(posList.get(posList.size()-3)));

        else return false;

    }

    public boolean in_loop() {
        if (list_closest.size() >= 20 && positive_stations.size()>1)
            return (list_closest.get(list_closest.size()-1).isEquals(list_closest.get(list_closest.size()-20)));

        else return false;
    }




    //Moves towards closest positive station and if stuck in a loop, move in a random legal direction
    public void movetowards(Direction d) {
            if (this.currentPosition.nextPosition(d).inPlayArea() && !is_Stuck()
            && (!withinNeg(d)))
            {
                movement(d);
            }

            else if (is_Stuck()) {moveRandom();}

            else {
                moveToAvoid();
            }
    }



    //Removes a station from the list which has already been visited
    public void removeStation(ArrayList<Stations> ls) { ls.removeIf(stations -> stations.coins == 0); }

    
    //Method to make the drone move towards positive stations until all have been visited
    public void strategy() {
        Stations nextStation = closest(this.currentPosition, positive_stations);
        list_closest.add(nextStation);
        if (nextStation == null)
        {
            moveRandom();
        }

        else if (in_loop())
        {
            System.out.println("stuck in loop");
            movetowards(getDirection(positive_stations.get(rnd.nextInt(positive_stations.size()))));
            removeStation(positive_stations);
        }

        else
        {
            movetowards(getDirection(nextStation));
            removeStation(positive_stations);
        }
    }
    
    
    //Method to make the drone move until it is out of power or moves
    public void StrategyCall() {
        double tc = map.totalCoins();
        do {
            System.out.println("\n MOVE: " + (250 - this.moves) + " POWER: " + this.power + " COINS: " + this.coins);

            strategy();


        } while (this.moves > 0 && this.power > 0);
        double perc = (this.coins/tc *100);
        System.out.println("\ntotal coins= " + tc + "\n" + "percent of coins collected: " +perc);

        //System.out.println("\nDrone coins = " + this.coins);
        //System.out.println("\nDrone power = " + this.power);
    }
   

}
