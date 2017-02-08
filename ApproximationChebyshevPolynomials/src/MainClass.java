import static java.lang.Math.pow;
/*
Аппроксимировать таблично заданную функцию,
используя полином Чебышева по 7ую степень включительно
*/

public class MainClass {
    static int N = 7;

    static double t0(int x){
        return 1;}
    static double t1(int x){
        return x;}
    static double t2(int x){
        return (2*x*x-1);}
    static double t3(int x){
        return (4*x*x*x-3*x);}
    static double t4(int x){
        return (8* pow(x,4)-8*x*x+1);}
    static double t5(int x){
        return (16* pow(x,5)-20*x*x*x+5*x);}
    static double t6(int x){
        return (32*pow(x,6) - 48*pow(x,4) + 18*x*x - 1);}
    static double t7(int x){
        return (64*pow(x,7) - 112*pow(x,5) + 56*pow(x,3) - 7*x);}

    static void approx(){
        int[] y = {5, 6, 8, 10, 12, 13, 12, 10, 8, 10, 8, 11, 7, 9, 11, 10, 9, 12, 11, 6};
        double[][] k = new double[N+1][N+2]; //[строки][столбцы]

        for ( int i = 1 ; i <= 20 ; i++){
            double q1=0, q2=0, q=0;
            for ( int j = 0; j < N+1; j++){
                switch (j) {
                    case 0:  q1=t0(i);q=t0(i);
                        break;
                    case 1:  q1=t1(i);q=t1(i);
                        break;
                    case 2:  q1=t2(i);q=t2(i);
                        break;
                    case 3:  q1=t3(i);q=t3(i);
                        break;
                    case 4:  q1=t4(i);q=t4(i);
                        break;
                    case 5:  q1=t5(i);q=t5(i);
                        break;
                    case 6:  q1=t6(i);q=t6(i);
                        break;
                    case 7:  q1=t7(i);q=t7(i);
                        break;}
                for (int m=0; m<N+1; m++){
                    switch (m) {
                        case 0:  q2=t0(i);
                            break;
                        case 1:  q2=t1(i);
                            break;
                        case 2:  q2=t2(i);
                            break;
                        case 3:  q2=t3(i);
                            break;
                        case 4:  q2=t4(i);
                            break;
                        case 5:  q2=t5(i);
                            break;
                        case 6:  q2=t6(i);
                            break;
                        case 7:  q2=t7(i);
                            break;}
                    k[j][m]=k[j][m]+q1*q2;

                }
                k[j][N+1]=k[j][N+1]+q*y[i-1];
            }
        }

        double[] a = new double[N+1];

        for (int i=0; i<N+1; i++){
            for (int j=i+1; j<N+1; j++){
                for (int m=N+1; m>=0; m--){
                    k[j][m] = k[j][m] - k[i][m]*k[j][i]/k[i][i];
                }}}
        for (int j=0; j<N+1; j++){
            for (int i=N+1; i>=0; i--){
                k[j][i]=k[j][i]/k[j][j];
            }}

        for ( int m=0; m<N+1; m++){
            a[m]=k[m][N+1];
        }
        for (int m=N; m>=0; m--){
            for (int j=m-1; j>=0; j--){
                a[j] = a[j] - k[j][m]*a[m];
            }}

        int j = 0;
        for (int i=N;i>=0; i--){
            System.out.print(" a"+(j)+"=");
            //System.out.printf("%.8f ", a[i]);
            System.out.print(a[i]);
            j++;
        }
        System.out.println();
        System.out.println();

        //Выводим результат
        double sum = 0;
        for (int i = 1; i < 21; i++) {
            double fx = a[7]*t7(i) + a[6]*t6(i) + a[5]*t5(i) + a[4]*t4(i) + a[3]*t3(i) + a[2]*t2(i) + a[1]*t1(i) + a[0]*t0(i);
            double error = pow(y[i-1] - fx,2);
            System.out.println("x=" + i + " y=" + y[i-1] + " f(x)=" + fx + " err=" + error);
            sum += error;
        }
        System.out.println(sum);
    }

    public static void main(String[] args) {
        approx();
    }
}