package uk.ac.ed.inf.powergrab;



import com.mapbox.geojson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * This is the main class
 *
 */

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



    public Drone(Position latlong, int seed, Map map){
        this.currentPosition = latlong;
        this.rnd = new Random(seed);
        this.coins = 0.0;
        this.moves = 250;
        this.power = 250.0;
        this.map = map;
        route.add(Point.fromLngLat(this.currentPosition.longitude, this.currentPosition.latitude));

    }


    //A simple function to calculate the euclidean distance between two points
    public static double distance(Position position, Position location) {
        double x1 = position.latitude;
        double y1 = position.longitude;
        double x2 = location.latitude;
        double y2 = location.longitude;
        double d = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        return d;
    }

    //A method which returns the index of the largest element in an array
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

    //A method which returns the index of the smallest element in an array
    public int minIndex(double[] arr){
        if ( arr == null || arr.length == 0 )
        {
            return -1;
        } // null or empty
        int smallest = 0;
        for (int i = 1;i < arr.length;i++)
        {
            if (arr[i] < arr[smallest]) smallest = i;
        }
        return smallest;
    }

    //A function to check if the drone is within a charging station
    public boolean withinStation (Position position, Stations station) {
        return distance(position, station.location) < 0.00025;
    }

    //A method which returns the station with the smallest distance to the drone
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



    //This method returns an array of the coin values that can be transferred in each of the 16 directions
    public double[] potential_gain(ArrayList<Stations> stations){

        double[] coin_array = new double[16];

        for (Direction direction : Direction.values())
        {
            Position p1 = this.currentPosition.nextPosition(direction);
            for (Stations s : stations)
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
        return coin_array;
    }


    /**
     * The main method used for the drone's movement which stores the current coin, location and power values, performs
     * the transfer of coins and power from a charging station
     */
    public void movement(Direction direction) {
        this.moves -= 1;
        this.power -= 1.25;
        Double initialLatitude = this.currentPosition.latitude;
        Double initialLongitude = this.currentPosition.longitude;

        this.currentPosition = currentPosition.nextPosition(direction);
        posList.add(this.currentPosition);


        if (withinStation(this.currentPosition, closestStation()))
        {

            this.coins += closestStation().coins;
            this.power += closestStation().power;
            closestStation().coins = 0;
            closestStation().power = 0;

            if ((this.coins += closestStation().coins)<0) {
                closestStation().coins = this.coins-closestStation().coins;
                this.coins = 0;
            }
            if ((this.power += closestStation().power)<0) {
                closestStation().power = this.power-closestStation().power;
                this.power = 0;
            }
        }

        String move = initialLatitude + "," + initialLongitude + "," + direction + "," + this.currentPosition.latitude + "," + this.currentPosition.longitude + "," + this.coins + "," + this.power;
        lines.add(move);
        Point r = Point.fromLngLat(this.currentPosition.longitude, this.currentPosition.latitude);
        route.add((r));
    }



    //A method to produce the json file
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

    //A method that produces the textfile
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
