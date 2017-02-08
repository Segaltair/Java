import java.util.ArrayList;
/*
Найти значение функции,
используя метод Ньютона
*/
import static java.lang.Math.*;

/**
 * Created by Sega on 27.12.2016.
 */
public class MainClass {
    public static void main(String[] args) {
        ArrayList<Double> x = new ArrayList<>();
        x.add(0.8);
        double fx = exp(-x.get(0)) - x.get(0)/2;
        double derFX = -exp(-x.get(0)) - 0.5;
        double xn = x.get(0) - fx/derFX;
        double eps = 0.00001;
        int i = 1;
        System.out.println("i=" + i + " x=" + String.format("%.6f", x.get(0)) + " f(x)=" + String.format("%.6f", fx));
        while (abs(xn-x.get(i-1)) > eps && i < 10){
            i++;
            x.add(xn);
            fx = exp(-x.get(i-1)) - x.get(i-1)/2;
            derFX = -exp(-x.get(i-1)) - 0.5;
            xn = x.get(i-1) - fx/derFX;
            if (i > 2) {
                double q = abs((x.get(i - 1) - x.get(i - 2)) / (x.get(i - 2) - x.get(i - 3)));
                double error = abs(q*(x.get(i-1) - x.get(i-2))/(q-1));
                System.out.println("i=" + i + " x=" + String.format("%.6f", x.get(i - 1)) + " f(x)=" +
                        String.format("%.6f", fx) + " q=" + String.format("%.6f", q) + " error=" + String.format("%.6f", error));
            }else
                System.out.println("i=" + i + " x=" + String.format("%.6f", x.get(i-1)) + " f(x)=" + String.format("%.6f", fx));
        }
    }
}
