package Interview;

import java.util.ArrayList;

/**
 * Created by Sega on 02.01.2017.
 */
public class Interview {
    String name;
    public ArrayList<Question> questions;

    public void createInterview(String name){
        this.name = name;
        questions = new ArrayList<Question>();
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    public String getName(){
        return name;
    }
}