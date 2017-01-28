package Station;

/**
 * Created by Sega on 14.12.2016.
 */
public class Station {
    public int x;
    public int y;
    public String name;

    public void addStation(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getStation(){
        String s = name + " " + x + " " + y;
        return s;
    }

}