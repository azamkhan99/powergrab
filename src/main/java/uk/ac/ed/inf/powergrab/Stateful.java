package uk.ac.ed.inf.powergrab;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.List;

public class Stateful extends Drone {

    public Stateful(Position latlong, int seed, Map map) {
        super(latlong, seed, map );
    }



    public ArrayList<Stations> stationPos() {
        ArrayList<Stations> positiveList = new ArrayList<>();
        for (Stations s : this.map.stations){
            if (s.coins > 0) {
                positiveList.add(s);
            }
        }
        //System.out.println("list size = " + positiveList.size() + " " + positiveList.toString());
        return positiveList;
    }


    public ArrayList<Stations> stationNeg() {
        ArrayList<Stations> negativeList = new ArrayList<>();
        for (Stations s : this.map.stations){
            if (s.coins < 0) {
                negativeList.add(s);
            }
        }
        //System.out.println("list size = " + positiveList.size() + " " + positiveList.toString());
        return negativeList;
    }


    public Stations closest (ArrayList<Stations> ls) {
        if (ls.isEmpty()) return null;
        Stations nearest = ls.get(0);
        for (Stations s : ls)
        {
            if (distance(this.currentPosition, s.location) < distance(this.currentPosition, nearest.location))
            {
                nearest = s;
            }
        }
        //System.out.println("closest positive station: " + nearest.toString());
        return nearest;
    }

    public boolean withinNeg(Direction d) {
        for (Stations s: stationNeg()) {
            if(withinStation(this.currentPosition.nextPosition(d),s)) {
                return true;
            }
        }

        return false;
    }


    public Direction getDirection(Stations s) {
        double in = Math.toDegrees(Math.atan2((s.location.longitude - this.currentPosition.longitude),(s.location.latitude-this.currentPosition.latitude)));
        int rad = (int)Math.round(((in % 360) / 22.5)) % 16;
        if (rad < 0) {
            rad = rad + 16;
            //no. of contains in array
        }
        //System.out.println("enum index = " +rad);
        Direction d = Direction.values()[rad];

        return d;
    }



    public double[] potential_loss() {

        double[] neg_coins = new double[16];

        for (Direction direction : Direction.values())
        {
            Position p1 = this.currentPosition.nextPosition(direction);
            for (Stations s : stationNeg())
            {
                if (!p1.inPlayArea())
                {
                    neg_coins[direction.ordinal()] = Double.NEGATIVE_INFINITY;
                }
                else if (withinStation(p1, s))
                {
                    neg_coins[direction.ordinal()] = s.coins;
                }
            }
        }
        //System.out.println(Arrays.toString(neg_coins));
        return neg_coins;
    }

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
        int dirInd = indices.get(randInd);
        return dirInd;
    }



    public void moveToAvoid() {
        int d = avoid(potential_loss());
        movement(Direction.values()[d]);
    }


    /*public void movetowards(Direction d) {
        if (this.currentPosition.nextPosition(d).inPlayArea()
                && (!withinStation(this.currentPosition.nextPosition(d), closest(stationNeg()))))
            movement(d);
        else moveToAvoid();
    }*/


    public void movetowards(Direction d) {
            if (this.currentPosition.nextPosition(d).inPlayArea()
            && (!withinNeg(d))) {
                movement(d);
            }
            else {
                moveToAvoid();
            }
    }


    public void removeStation(ArrayList<Stations> ls) { ls.removeIf(stations -> stations.coins == 0); }


    public void strategy() {
        //System.out.println(this.currentPosition.toString());
        if (closest(stationPos()) == null) { moveToAvoid();} else {
            movetowards(getDirection(closest(stationPos())));
            removeStation(stationPos());}
        //System.out.println(this.currentPosition.toString());
    }


    public void StrategyCall() {
        double tc = map.totalCoins();
        do {
            //System.out.println("\n MOVE: " + (250 - this.moves) + " POWER: " + this.power + " COINS: " + this.coins);
            strategy();

        } while (this.moves > 0 && this.power > 0);
        double perc = Math.round(this.coins/tc *100);
        System.out.println("\ntotal coins= " + tc + "\n" + "percent of coins collected: " +perc);

        //System.out.println("\nDrone coins = " + this.coins);
        //System.out.println("Drone power = " + this.power);

    }

}
