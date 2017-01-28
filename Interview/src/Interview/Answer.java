package Interview;

/**
 * Created by Sega on 02.01.2017.
 */
public class Answer {
    private String answer;
    private boolean isSelected = false;

    public void createAnswer(String answer){
        this.answer = answer;
    }

    public void setSelected(boolean isSelected){
        this.isSelected = isSelected;
    }

    public String getAnswer(){
        return answer;
    }

    public boolean getSelected(){
        return isSelected;
    }
}