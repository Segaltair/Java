/*
Интерполировать таблично заданную функцию,
используя полиномы Лагранжа
*/

public class MainClass {
    static int[] x = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    static int[] y = {5, 6, 8, 10, 12, 13, 12, 10, 8, 10, 8, 11, 7, 9, 11, 10, 9, 12, 11, 6};
    static int N = 4;
    static double point = 1;
    static double dx = 0.25;

    static double li (int start, int end, int j, double a){
        double d = 1;
        for (int i = start; i < end; i++) {
            if (i != j){
                d *= (a - x[i]) / (x[j] - x[i]);
            }
        }
        return d;
    }

    public static void main(String[] args) {
        double Li;
        int end = N + 1;
        int start = 0;
        point = 1;
        for (int k = 0; k < x.length / (N + 1); k++) {
            for (int i = 0; i < N * 4; i++) {
                Li = 0;
                for (int j = start; j < end; j++) {
                    Li += y[j] * li(start, end, j, point);
                }
                System.out.println(point + " " + Li);
                point += dx;
                if (point == x.length - N + 2) break;
            }
            start = end - 1;
            end += N;
        }

        start = x.length - N - 1;
        end = x.length;
        for (int i = 0; i < N * 3 + 1; i++) {
            Li = 0;
            for (int j = start; j < end; j++) {
                Li += y[j] * li(start, end, j, point);
            }
            System.out.println(point + " " + Li);
            point += dx;
        }
        }
}