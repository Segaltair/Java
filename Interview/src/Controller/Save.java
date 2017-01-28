package Controller;

import Interview.Interview;

import java.io.*;

/**
 * Created by Sega on 03.01.2017.
 */
//C:\Users\Sega\IdeaProjects\Interview.txt
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
            PrintStream out = new PrintStream(file, "UTF-8");
            Interview currentInterview;
            try {
                //Записываем текст у файл
                for (int i = 0; i < Controller.interviews.size(); i++) {
                    currentInterview = Controller.interviews.get(i);
                    out.println(currentInterview.getName());

                    for (int j = 0; j < currentInterview.questions.size(); j++) {
                        out.println(currentInterview.questions.get(j).getQuestion());

                        for (int k = 0; k < currentInterview.questions.get(j).answers.size(); k++) {
                            out.println(currentInterview.questions.get(j).answers.get(k).getAnswer());
                        }
                        out.println();
                    }
                    out.println("Конец опроса");
                }
            } finally {
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
}