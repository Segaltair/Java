package Passenger;

import Train.Train;

/**
 * Created by Sega on 14.12.2016.
 */

public class Passenger {
    public String fullName;
    public Train train;

    public void addPassenger(String fullName, Train train){
        this.fullName = fullName;
        this.train = train;
    }

    public String getPassenger(){
        String s;
        s = fullName + " " + train.name;
        return s;
    }
}