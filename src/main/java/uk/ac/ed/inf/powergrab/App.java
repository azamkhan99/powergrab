package uk.ac.ed.inf.powergrab;

/**
 * Hello world!
 */
public class App {

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

            if (!(start.inPlayArea())) throw new IllegalArgumentException("Invalid starting position");


            int seed = Integer.parseInt(args[5]);

            if (!args[6].equals("stateless") && !args[6].equals("stateful"))
                throw new IllegalArgumentException("state not entered correctly");
            String state = args[6];


            String dateJson = String.format("%s-%s-%s-%s.geojson", state, day, month, year);
            String dateText = String.format("%s-%s-%s-%s.txt", state, day, month, year);
            if (state.equals("stateless")) {
                Stateless d = new Stateless(start, seed, m);
                d.callStrategy();
                d.makeJson(dateJson);
                d.makeTxt(dateText);
            } else if (state.equals("stateful")) {

                Stateful d = new Stateful(start, seed, m);
                d.strategyCall();

                d.makeTxt(dateText);
                d.makeJson(dateJson);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
