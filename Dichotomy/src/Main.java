import static java.lang.Math.*;
/*
Найти значение функции,
используя метод дихотомии
*/

public class Main {
    static double f(double x){
        return exp(-x) - x/2;
    }

    public static void main(String[] args) {
        double xLeft = 0.8;
        double xRight = 1;
        double eps = 0.00001;
        double x = (xRight - xLeft) / 2 + xLeft;
        int i = 1;
        double dx = (xRight - xLeft) / 2;

        while(((xRight - xLeft)> 2*eps) && (i != 100)) {
            System.out.println("i=" + i + " x=" + String.format("%.6f" ,x) +
                    " xLeft=" + String.format("%.6f" ,xLeft) + " xRight=" + String.format("%.6f" ,xRight) +
                    " F(x)=" + String.format("%.6f" ,f(x)) + " F(xLeft)=" + String.format("%.6f" ,f(xLeft)) +
                    " F(xRight)=" + String.format("%.6f" ,f(xRight))) ;
            dx  = dx / 2;
            if (signum(f(x)) != signum(f(xLeft)))
                xRight = x;
            else
                xLeft = x;

            x = dx + xLeft;
            i++;
        }
        System.out.println("i=" + i + " x=" + String.format("%.6f" ,x) +
                " xLeft=" + String.format("%.6f" ,xLeft) + " xRight=" + String.format("%.6f" ,xRight) +
                " F(x)=" + String.format("%.6f" ,f(x)) + " F(xLeft)=" + String.format("%.6f" ,f(xLeft)) +
                " F(xRight)=" + String.format("%.6f" ,f(xRight)));
    }
}
