import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

import static java.lang.Math.*;
/*
Найти значение функции,
используя метод простой итерации
*/

/**
 * Created by Sega on 27.12.2016.
 */
public class MainClass {

    public static void main(String[] args) {
        ArrayList<Double> x = new ArrayList<>();
        x.add(0.8);
        double k = -0.5;
        double fx = exp(-x.get(0)) - x.get(0)/2;
        double derFX = -exp(-x.get(0)) - 0.5;
        double eps = 0.00001;
        int i = 1;
        double xNext = x.get(i-1) - fx * k;
        double q = 1;
        double error;

        System.out.println("i=" + i + " x=" + String.format("%.6f",x.get(i-1)) + " f(x)=" + String.format("%.6f",fx));
        while ((abs(xNext - x.get(i-1)) > ((1-q)/q*eps)) && (i < 60)){
            x.add(abs(xNext));
            i++;
            fx = exp(-x.get(i-1)) - x.get(i-1)/2;
            xNext = x.get(i-1) -  fx * k;
            if (i > 2){
                q = abs((x.get(i-1) - x.get(i-2)) / (x.get(i-2) - x.get(i-3)));
                error = abs(q*(x.get(i-1) - x.get(i-2))/(q - 1));
                System.out.println("i=" + i + " x=" + String.format("%.6f",x.get(i-1)) + " f(x)=" + String.format("%.6f",fx) + " q=" +
                        String.format("%.6f",q) + " error=" + String.format("%.6f", error) + " ");
            }
            else
                System.out.println("i=" + i + " x=" + String.format("%.6f",x.get(i-1)) + " f(x)=" + String.format("%.6f",fx));
        }
    }
}
