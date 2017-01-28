package GraphicsInterface;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import static Controller.Controller.*;

/**
 * Created by Sega on 29.12.2016.
 */
public class Save {
    public static void write(String fileName) {
        //Определяем файл
        File file = new File(fileName);

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file.exists()){
                file.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //Записываем текст у файл
                String stringStations = "";
                for (int i = 0; i < stations.size(); i++) {
                    stringStations += stations.get(i).name + "," + stations.get(i).x + "," + stations.get(i).y + ";";
                }

                String stringRoutes = "";
                for (int i = 0; i < routes.size(); i++) {
                    stringRoutes += routes.get(i).name + ",";
                    for (int j = 0; j < routes.get(i).listOfStations.size(); j++) {
                        stringRoutes += routes.get(i).listOfStations.get(j).name + ",";
                    }
                    stringRoutes = stringRoutes.substring(0, stringRoutes.length() - 1);
                    stringRoutes += ";";
                }

                String stringTrains = "";
                for (int i = 0; i < trains.size(); i++) {
                    stringTrains += trains.get(i).name + "," + trains.get(i).route.name + "," +
                            trains.get(i).capacity + "," + trains.get(i).price + "," +
                            trains.get(i).timeStart + "," + trains.get(i).velocity + ";";
                }

                String stringPassengers = "";
                for (int i = 0; i < passengers.size(); i++) {
                    stringPassengers += passengers.get(i).fullName + "," + passengers.get(i).train.name + ";";
                }

                out.println(stringStations);
                out.println(stringRoutes);
                out.println(stringTrains);
                out.println(stringPassengers);
            } finally {
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}