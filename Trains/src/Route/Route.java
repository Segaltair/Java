package Route;

import java.util.ArrayList;
import Station.Station;
import Train.Train;

/**
 * Created by Sega on 14.12.2016.
 */
public class Route {
    public ArrayList<Station> listOfStations;
    public String name;
    public ArrayList<Train> trainList;
    public int totalLength;

    public void addRoute(String name){
        listOfStations = new ArrayList();
        trainList = new ArrayList();
        this.name = name;
    }

    public void addStationToRoute(Station station){
        listOfStations.add(station);
        totalLength = 0;
        for (int i = 0; i < listOfStations.size() - 1 ; i++) {
            totalLength += getLengths().get(i);
        }
    }

    public String getRouteName(){
        String s = name;
        return s;
    }

    public String getStationList(){
        String s = "";

        for (int i = 0; i < listOfStations.size(); i++) {
            if (i != listOfStations.size() - 1){
                s += listOfStations.get(i).name + ", ";
            }else {
                s += listOfStations.get(i).name;
            }
        }
        return s;
    }

    public String getTrainList(){
        String s = "";

        for (int i = 0; i < trainList.size() ; i++) {
            if (i != trainList.size() - 1){
                s += trainList.get(i).name + ", ";
            }else { s += trainList.get(i).name;}
        }
        return s;
    }

    public ArrayList<Integer> getLengths(){
        ArrayList<Integer> lengths = new ArrayList<>();

        int lastStationX = -1;
        int lastStationY = -1;
        for (int i = 0; i < listOfStations.size(); i++) {
            if (lastStationX != -1){
                Double length = (Math.sqrt(Math.pow(listOfStations.get(i).x - lastStationX, 2) + Math.pow(listOfStations.get(i).y - lastStationY, 2)));
                lengths.add(length.intValue());
            }
            lastStationX = listOfStations.get(i).x;
            lastStationY = listOfStations.get(i).y;
        }

        return lengths;
    }
    }