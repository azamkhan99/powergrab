package uk.ac.ed.inf.powergrab;

import java.io.IOException;
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
        int day = in.nextInt();
        int month = in.nextInt();
        int year = in.nextInt();
        String date = String.format("%d/0%d/%d", year, month, day);

        Map m = new Map(date);

       /* for (Stations s : m.stations) {
            System.out.println(s);
        }*/


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
            d.movement(Direction.N);

        }


    }
}
