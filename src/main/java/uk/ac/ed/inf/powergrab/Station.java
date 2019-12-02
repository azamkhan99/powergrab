package uk.ac.ed.inf.powergrab;

public class Station {
    private double coins;
    private double power;
    private Position location;

    public Station(double coins, double power, Position location) {
        this.coins = coins;
        this.power = power;
        this.location = location;
    }

    public double getCoins() {
        return coins;
    }

    public void setCoins(double coins) {
        this.coins = coins;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public Position getLocation() {
        return location;
    }


    public String toString() {
        return "coins: " + this.coins + "power: " + this.power + "latitude: " + this.location.latitude + "longitude: " + this.location.longitude;
    }

    boolean isEquals(Station s) {
        return this.coins == s.coins && this.power == s.power && this.location == s.location;
    }

}
