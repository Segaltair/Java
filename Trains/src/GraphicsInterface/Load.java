package GraphicsInterface;

import java.io.*;
import java.time.LocalDateTime;
import static Controller.Controller.*;
/**
 * Created by Sega on 29.12.2016.
 */
public class Load {
    public static void load(String fileName) {
        stations.removeAll(stations);
        routes.removeAll(routes);
        trains.removeAll(trains);
        passengers.removeAll(passengers);

        //Определяем файл
        File file = new File(fileName);

        try {
            //Объект для чтения файла в буфер
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            try {
                String s;

                s = in.readLine();
                String[] stringStations = s.split(";");
                for (int i = 0; i < stringStations.length; i++) {
                    String[] station = stringStations[i].split(",");
                    createStation(station[0], Integer.parseInt(station[1]), Integer.parseInt(station[2]));
                }

                s = in.readLine();
                String[] stringSplitRoutes = s.split(";");
                for (int i = 0; i < stringSplitRoutes.length; i++) {
                    String[] stringRoutes = stringSplitRoutes[i].split(",");
                    createRoute(stringRoutes[0]);
                    for (int j = 1; j < stringRoutes.length; j++) {//ot 0 do 2
                        for (int k = 0; k < stations.size(); k++) {//ot 0 do 5
                            if (stations.get(k).name.equals(stringRoutes[j])){
                                for (int l = 0; l < routes.size(); l++) {
                                    if (routes.get(l).name.equals(stringRoutes[0])){
                                        routes.get(l).addStationToRoute(stations.get(k));
                                    }
                                }
                            }
                        }
                    }
                }

                s = in.readLine();
                String[] stringTrains = s.split(";");
                for (int i = 0; i < stringTrains.length; i++) {
                    String[] train = stringTrains[i].split(",");
                    for (int j = 0; j < routes.size(); j++){
                        if (train[1].equals(routes.get(j).name)){
                            createTrain(train[0], routes.get(j), Integer.parseInt(train[2]), Integer.parseInt(train[3]), LocalDateTime.parse(train[4]), Integer.parseInt(train[5]));
                            break;
                        }
                    }
                }

                s = in.readLine();
                String[] stringPassengers = s.split(";");
                for (int i = 0; i < stringPassengers.length; i++) {
                    String[] passenger = stringPassengers[i].split(",");
                    for (int j = 0; j < trains.size(); j++) {
                        if (trains.get(j).name.equals(passenger[1])){
                            createPassenger(passenger[0], trains.get(j));
                        }
                    }
                }

            } finally {
                //Также не забываем закрыть файл
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}