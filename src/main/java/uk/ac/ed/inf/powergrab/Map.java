package uk.ac.ed.inf.powergrab;

import com.mapbox.geojson.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Map {
    public ArrayList<Stations> stations = new ArrayList<Stations>();
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
        add2map(fcs);

    }

    private String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }



    public Position f2p (Feature f) {
        Point coords = (Point) f.geometry();
        Position p = new Position(coords.latitude(), coords.longitude());
        return p;
    }


    public void add2map(FeatureCollection fcs){
        for (Feature f : fcs.features()) {
            double coins = f.getProperty("coins").getAsDouble();
            double power = f.getProperty("power").getAsDouble();
            Position location = f2p(f);
            Stations s = new Stations(coins,power,location);
            stations.add(s);
        }

    }

    public double totalCoins() {
        double sum = 0;
        for (Stations s : stations){
            if (s.coins > 0) {
                sum += s.coins;
            }
        }
        return sum;
    }





}
