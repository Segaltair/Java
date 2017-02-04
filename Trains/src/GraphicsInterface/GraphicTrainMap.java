package GraphicsInterface;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import static Controller.Controller.*;
import static GraphicsInterface.GraphicInterface.lTime;
import static java.time.LocalDateTime.now;

/**
 * Created by Sega on 20.12.2016.
 */
public class GraphicTrainMap extends JPanel {

    GraphicTrainMap(int w, int h){
        //Непрозрачная панель
        setOpaque(true);

        //Border
        setBorder(BorderFactory.createLineBorder(Color.RED, 1));

        setPreferredSize(new Dimension(w,h));
    }
    public void paintComponent(Graphics g){
        final int STATION_SIZE = 10;//размер станций
        final int RATE = 10;//частота обновлений отрисовки поездов

        super.paintComponent(g);

        //Рисование маршрутов
        int lastStationX = -1;
        int lastStationY = -1;
        //Рисуем все маршруты
        for (int i = 0; i < routes.size() ; i++) {
            //Рисуем один маршрут
            for (int j = 0; j < routes.get(i).listOfStations.size(); j++) {
                if (lastStationX != -1){
                    //x,y - координаты надписи длины между двумя станциями
                    int x = Math.abs(routes.get(i).listOfStations.get(j).x - lastStationX)/2 + Math.min(routes.get(i).listOfStations.get(j).x,lastStationX);
                    int y = Math.abs(routes.get(i).listOfStations.get(j).y - lastStationY)/2 + Math.min(routes.get(i).listOfStations.get(j).y, lastStationY);

                    g.drawString(routes.get(i).getLengths().get(j - 1).toString(), x, y);
                    g.drawLine(lastStationX, lastStationY, routes.get(i).listOfStations.get(j).x, routes.get(i).listOfStations.get(j).y);
                }
                lastStationX = routes.get(i).listOfStations.get(j).x;
                lastStationY = routes.get(i).listOfStations.get(j).y;
            }
            lastStationX = -1;
            lastStationY = -1;
        }

        //Рисование станций
        for (int i = 0; i < stations.size(); i++) {
            g.drawString(stations.get(i).name, stations.get(i).x - STATION_SIZE /2, stations.get(i).y - STATION_SIZE /2);
            g.setColor(Color.RED);
            g.fillOval(stations.get(i).x - STATION_SIZE /2, stations.get(i).y - STATION_SIZE /2, STATION_SIZE, STATION_SIZE);
            g.setColor(Color.BLACK);
        }

        //Рисование поездов
        LocalDateTime currentTime = LocalDateTime.of(now().getYear(), now().getMonth(), now().getDayOfMonth(), now().getHour(), now().getMinute(), now().getSecond());
        for (int i = 0; i < trains.size(); i++) {
            String trainName = trains.get(i).name;
            trains.get(i).whereIsTrainNow(currentTime);
            g.setColor(Color.GREEN);
            g.fillOval((int)trains.get(i).x - STATION_SIZE /2, (int)trains.get(i).y - STATION_SIZE /2, STATION_SIZE, STATION_SIZE);
            g.setColor(Color.BLACK);
            for (int j = 0; j < trains.size(); j++) {
                if(i != j && (trains.get(i).x == trains.get(j).x && trains.get(i).y == trains.get(j).y)) {
                    trainName = "";
                    for (int k = 0; k < trains.size(); k++) {
                        if (trains.get(i).x == trains.get(k).x && trains.get(i).y == trains.get(k).y)
                            trainName += trains.get(k).name + ", ";
                    }
                    trainName = trainName.substring(0, trainName.length() - 2);
                }
            }
            g.drawString(trainName, (int) trains.get(i).x, (int) trains.get(i).y + 2 * STATION_SIZE);
        }

        repaint();
        try {
            Thread.sleep(RATE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lTime.setText(currentTime.toString());
    }
}