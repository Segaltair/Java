package Train;

import Passenger.Passenger;
import Route.Route;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static java.lang.Math.pow;

/**
 * Created by Sega on 14.12.2016.
 */
public class Train {
    public String name;
    public int capacity;
    public int price;
    public Route route;
    public LocalDateTime timeStart;
    public LocalDateTime timeEnd;
    public int velocity;
    public int passed = 0;
    public ArrayList<Passenger> passengerList;
    public double x;
    public double y;
    private double passedSeconds = 0;

    public void addTrain(String name, Route route, int capacity, int price, LocalDateTime timeStart, int velocity){
        this.name = name;
        this.capacity = capacity;
        this.price = price;
        this.route = route;
        this.timeStart = timeStart;
        this.velocity = velocity;
        this.x = route.listOfStations.get(0).x;
        this.y = route.listOfStations.get(0).y;
        passengerList = new ArrayList<>();
    }

    public String getTrain(){
        String s = name + ", capacity " + capacity + ", price " + price + ", timeStart " + timeStart + ", velocity " + velocity;
        return s;
    }

    public void whereIsTrainNow(LocalDateTime currentTime){
        if (currentTime.isEqual(timeStart) || currentTime.isAfter(timeStart)) {
            if(route.listOfStations.size() != passed + 1) {
                double l = route.getLengths().get(passed);
                double seconds = currentTime.getSecond() - timeStart.getSecond() +
                        60*(currentTime.getMinute() - timeStart.getMinute()) +
                        3600*(currentTime.getHour() - timeStart.getHour()) +
                        LocalDateTime.now().getNano()/1000000000.0 -
                        passedSeconds;
                x = ((route.listOfStations.get(passed + 1).x - route.listOfStations.get(passed).x) / l)*velocity*(seconds) + route.listOfStations.get(passed).x;
                y = ((route.listOfStations.get(passed + 1).y - route.listOfStations.get(passed).y) / l)*velocity*(seconds) + route.listOfStations.get(passed).y;

                if(l < (pow((pow(x - route.listOfStations.get(passed).x, 2) + pow(y - route.listOfStations.get(passed).y, 2)), 0.5))){
                    passed++;
                    x = route.listOfStations.get(passed).x;
                    y = route.listOfStations.get(passed).y;
                    passedSeconds += seconds;
                }}
        }
    }
}