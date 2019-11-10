package uk.ac.ed.inf.powergrab;



import com.mapbox.geojson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Drone {

    double coins;
    int moves;
    double power;
    Position currentPosition;
    Random rnd;
    Map map;
    static ArrayList<Point> route = new ArrayList<Point>();
    static ArrayList<String> lines = new ArrayList<>();
    static ArrayList<Position> posList = new ArrayList<>();

    public double totalCoins() {
        ArrayList<Double> coinList = new ArrayList<>();
        double sum = 0;
        for (Stations s : this.map.stations){
            if (s.coins > 0) {
                sum += s.coins;
            }
        }
        System.out.println("\nlol: "+ sum + "\n");
        return sum;
    }


    public Drone(Position latlong, int seed, Map map){
        this.currentPosition = latlong;
        this.rnd = new Random(seed);
        this.coins = 0.0;
        this.moves = 250;
        this.power = 250.0;
        this.map = map;
        this.route.add(Point.fromLngLat(this.currentPosition.longitude, this.currentPosition.latitude));

    }

    public double distance(Position position, Position location) {
        double x1 = position.latitude;
        double y1 = position.longitude;
        double x2 = location.latitude;
        double y2 = location.longitude;
        double d = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        return d;
    }

    public int maxIndex(double[] arr){
        if ( arr == null || arr.length == 0 )
        {
            return -1;
        } // null or empty
        int biggest = 0;
        for (int i = 1;i < arr.length;i++)
        {
            if (arr[i] > arr[biggest]) biggest = i;
        }
        return biggest;
    }

    public int minIndex(double[] arr){
        if ( arr == null || arr.length == 0 )
        {
            return -1;
        } // null or empty
        int biggest = 0;
        for (int i = 1;i < arr.length;i++)
        {
            if (arr[i] < arr[biggest]) biggest = i;
        }
        return biggest;
    }


    public boolean withinStation (Position position, Stations station) {
        return distance(position, station.location) < 0.00025;
    }


    public Stations closestStation () {
        Stations nearest = this.map.stations.get(0);
        for (Stations s : this.map.stations)
        {
            if (distance(this.currentPosition, s.location) < distance(this.currentPosition, nearest.location))
            {
                nearest = s;
            }
        }
        return nearest;
    }

    public ArrayList<Direction> ChecklegalMoves() {
        ArrayList<Direction> legalMoves = new ArrayList<>();
        for (Direction direction: Direction.values()) {
            if(this.currentPosition.nextPosition(direction).inPlayArea()) {
                legalMoves.add(direction);
            }
        }
        return legalMoves;
    }


    public void movement(Direction direction) {
        this.moves -= 1;
        this.power -= 1.25;
        Double initialLatitude = this.currentPosition.latitude;
        Double initialLongitude = this.currentPosition.longitude;

        this.currentPosition = currentPosition.nextPosition(direction);
        //Position newPosition = this.currentPosition;
        posList.add(this.currentPosition);
        //System.out.println(posList.toString());


        if (withinStation(this.currentPosition, closestStation()))
        {
            this.coins += closestStation().coins;
            this.power += closestStation().power;
            closestStation().coins = 0;
            closestStation().power = 0;
        }

        String move = initialLatitude + "," + initialLongitude + "," + direction + "," + this.currentPosition.latitude + "," + this.currentPosition.longitude + "," + this.coins + "," + this.power + "\n";
        lines.add(move);
        Point r = Point.fromLngLat(this.currentPosition.longitude, this.currentPosition.latitude);
        route.add((r));
    }





    public void makejsonfile(String mapdate) {
        LineString ls = LineString.fromLngLats(route);

        Map.fcs.features().add(Feature.fromGeometry(ls));
        try (FileWriter file = new FileWriter(mapdate))
        {
            file.write(Map.fcs.toJson());
            file.flush();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void maketxtfile(String mapdate) {
        try (FileWriter writer = new FileWriter(mapdate))
        {
            for(String line: lines)
            {
                writer.write(line + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }


}
