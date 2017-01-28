package Controller;

import Interview.Interview;
import java.io.*;
import java.util.Scanner;
import Interview.*;

/**
 * Created by Sega on 03.01.2017.
 */
public class Load {
    public static void load(String fileName) {
        //Определяем файл
        File file = new File(fileName);

        try {
            Scanner sc = new Scanner(file);
            Interview currentInterview;
            Question question;
            Answer answer;
            String s;

            while(sc.hasNextLine()){
                currentInterview = new Interview();
                currentInterview.createInterview(sc.nextLine());
                Controller.interviews.add(currentInterview);
                s = sc.nextLine();

                while(!s.equals("Конец опроса")){
                    question = new Question();
                    question.createQuestion(s);
                    currentInterview.questions.add(question);
                    s = sc.nextLine();

                    while (!s.equals("")){
                        answer = new Answer();
                        answer.createAnswer(s);
                        question.answers.add(answer);
                        s = sc.nextLine();
                    }
                    s = sc.nextLine();
                }
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
