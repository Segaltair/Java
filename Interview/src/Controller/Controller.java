package Controller;

import Interview.Interview;
import javax.swing.*;
import java.util.ArrayList;
import GraphicsInterface.GraphicsInterface;

import static Controller.Load.load;

/**
 * Created by Sega on 27.12.2016.
 */
public class Controller {
    public static ArrayList<Interview> interviews = new ArrayList<Interview>();

    public static void main(String[] args) {
        load("C:\\Users\\Sega\\IdeaProjects\\Interview\\Interview.txt");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GraphicsInterface();
            }
        });
//        ArrayList<String> answers = new ArrayList<>();
//        ArrayList<String> question = new ArrayList<>();
//        StringBuilder sb = new StringBuilder();
//        int i = 1;
//
//        try {
//            //Объект для чтения файла в буфер
//            BufferedReader in = new BufferedReader(new FileReader("D:\\notes3.txt"));
//            try {
//                //В цикле построчно считываем файл
//                String s;
//                while ((s = in.readLine()) != null) {
//                    if ( (i%2 == 0)){
//                        answers.add(s);
//                    }
//
//                    if ((s.equals("Варианты:")) && (i%2 == 1)){
//                        question.add(sb.toString());
//                        sb.delete(0, sb.length());
//                        answers.clear();
//                        i++;
//                    }
//                    sb.append(s);
//                    //sb.append("\n");
//                }
//            } finally {
//                in.close();
//            }
//        } catch(IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(question.get(0));
//        System.out.println();
//        for (int j = 0; j < answers.size(); j++) {
//            System.out.println(answers.get(j));
//        }
    }
}