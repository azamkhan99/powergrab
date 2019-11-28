package uk.ac.ed.inf.powergrab;

import java.io.IOException;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App
{

    public static void main(String[] args) {
    	try {

        String day = args[0];  
        String month = args[1];
        String year = args[2];

        String date = String.format("%s/%s/%s", year, month, day);


        Map m = new Map(date);
        
        double latitude = Double.parseDouble(args[3]);
        double longitude = Double.parseDouble(args[4]);
        Position start = new Position(latitude, longitude);

        
        int seed = Integer.parseInt(args[5]);

       
        String state = args[6];
        String datejson = String.format("%s-%s-%s-%s.geojson", state, day, month, year);
        String datetxt = String.format("%s-%s-%s-%s.txt", state, day, month, year);
        if (state.equals("stateless")) {
            Stateless d = new Stateless(start, seed, m);
            d.callStrategy();
            d.makejsonfile(datejson);
            d.maketxtfile(datetxt);
        }
        
       
        else if (state.equals("stateful")) {

            Stateful d = new Stateful(start, seed, m);
            d.StrategyCall();

            d.maketxtfile(datetxt);
            d.makejsonfile(datejson);
            
        }
        
    	}catch (Exception e) {
    		e.printStackTrace();
    	}

    }
}
