package uk.ac.ed.inf.powergrab;

import com.mapbox.geojson.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Map {


    private ArrayList<Station> stations = new ArrayList<Station>();
    public static FeatureCollection fcs;

    public Map(String mapDate) throws IOException {
        URL mapUrl = new URL("http://homepages.inf.ed.ac.uk/stg/powergrab/" + mapDate + "/powergrabmap.geojson");
        HttpURLConnection httpConn = (HttpURLConnection) mapUrl.openConnection();
        httpConn.setReadTimeout(10000);
        httpConn.setConnectTimeout(15000);
        httpConn.setRequestMethod("GET");
        httpConn.setDoInput(true);
        httpConn.connect();
        InputStream is = httpConn.getInputStream();
        String mapSource = convertStreamToString(is);
        fcs = FeatureCollection.fromJson(mapSource);
        addToMap(fcs);

    }

    public ArrayList<Station> getStations() {
        return stations;
    }


    //Converts a stream and returns it as a string
    public String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    //Parses a point feature as a position object
    public Position fToP(Feature f) {
        Point coords = (Point) f.geometry();
        Position p = new Position(coords.latitude(), coords.longitude());
        return p;
    }


    //Parses coins and power objects and stores them in a station object
    public void addToMap(FeatureCollection fcs) {
        for (Feature f : fcs.features()) {
            double coins = f.getProperty("coins").getAsDouble();
            double power = f.getProperty("power").getAsDouble();
            Position location = fToP(f);
            Station s = new Station(coins, power, location);
            stations.add(s);
        }

    }

    //returns the total number of coins on the map
    public double totalCoins() {
        double sum = 0;
        for (Station s : stations) {
            if (s.getCoins() > 0) {
                sum += s.getCoins();
            }
        }
        return sum;
    }


}
