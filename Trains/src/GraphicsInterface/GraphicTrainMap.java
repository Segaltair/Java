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
    final int ST_SIZE = 10;

    GraphicTrainMap(int w, int h){
        //Непрозрачная панель
        setOpaque(true);

        //Border
        setBorder(BorderFactory.createLineBorder(Color.RED, 1));

        setPreferredSize(new Dimension(w,h));
    }
    public void paintComponent(Graphics g){
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
            g.drawString(stations.get(i).name, stations.get(i).x - ST_SIZE/2, stations.get(i).y - ST_SIZE/2);
            g.setColor(Color.RED);
            g.fillOval(stations.get(i).x - ST_SIZE/2, stations.get(i).y - ST_SIZE/2, ST_SIZE, ST_SIZE);
            g.setColor(Color.BLACK);
        }

        //Рисование поездов
        LocalDateTime currentTime = LocalDateTime.of(now().getYear(), now().getMonth(), now().getDayOfMonth(), now().getHour(), now().getMinute(), now().getSecond());
        for (int i = 0; i < trains.size(); i++) {
            trains.get(i).whereIsTrainNow(currentTime);
            g.setColor(Color.GREEN);
            g.fillOval((int)trains.get(i).x - ST_SIZE/2, (int)trains.get(i).y - ST_SIZE/2, ST_SIZE, ST_SIZE);
            g.setColor(Color.BLACK);
            g.drawString(trains.get(i).name, (int)trains.get(i).x, (int)trains.get(i).y + 2 * ST_SIZE);
        }
        repaint();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lTime.setText(currentTime.toString());
        }
    }