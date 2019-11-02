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

//static int lol = 0;
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
        if (state.equals("stateless")) {
            Stateless d = new Stateless(start, seed, m);
            d.callStrategy();
            d.makeLS();


        }
        else if (state.equals("stateful")) {
            Stateful d = new Stateful(start, seed, m);

        }


    }
}
