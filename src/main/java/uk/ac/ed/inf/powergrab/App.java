package uk.ac.ed.inf.powergrab;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App
{

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String day = in.next();
        String month = in.next();
        String year = in.next();

        String date = String.format("%s/%s/%s", year, month, day);


        Map m = new Map(date);
        double latitude = in.nextDouble();
        double longitude = in.nextDouble();
        Position start = new Position(latitude, longitude);

        int seed = in.nextInt();

        String state = in.next();
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

        }


    }
}
