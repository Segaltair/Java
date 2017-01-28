package Interview;

import java.util.ArrayList;

/**
 * Created by Sega on 02.01.2017.
 */
public class Question {
    String question;
    public ArrayList<Answer> answers;

    public void createQuestion(String question){
        this.question = question;
        answers = new ArrayList<Answer>();
    }

    public void addAnswer(Answer answer){
        answers.add(answer);
    }

    public String getQuestion(){
        return question;
    }
}