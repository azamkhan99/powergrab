package uk.ac.ed.inf.powergrab;

public class Stations {
    double coins;
    double power;
    Position location;

    public Stations(double coins, double power, Position location){
        this.coins = coins; this.power = power; this.location = location;
    }

    public String toString() {
        return "coins: " + this.coins + "power: " + this.power + "latitude: " + this.location.latitude + "longitude: " + this.location.longitude;
    }
}
