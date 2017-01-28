package GraphicsInterface;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import Interview.Interview;
import Interview.Answer;
import Interview.Question;
import Controller.Controller;
import Controller.Save;

/**
 * Created by Sega on 02.01.2017.
 */
public class GraphicsInterface {
    Interview interview;
    Question question;
    Answer answer;
    int indexInterview = -1;
    int indexOfQuestion = -1;
    boolean interviewIsPassed = false;

    public boolean checkInterview (Interview interview){
        if (interview.questions.size() <= 10 && interview.questions.size() >= 1 )
            for (int i = 0; i < interview.questions.size(); i++) {
                if (interview.questions.get(i).getQuestion().equals("") ||
                        interview.questions.get(i).answers.size() > 6 ||
                        interview.questions.get(i).answers.size() < 2)
                    return false;
                for (int j = 0; j < interview.questions.get(i).answers.size(); j++) {
                    if (interview.questions.get(i).answers.get(j).getAnswer().equals(""))
                        return false;
                }
            }
        else
            return false;
        return true;
    }

    public GraphicsInterface(){

        JFrame jfrm = new JFrame("Опрос");

        JPanel pTools = new JPanel();
        pTools.setPreferredSize(new Dimension(300,400));
        pTools.setOpaque(true);
        pTools.setBorder(BorderFactory.createLineBorder(Color.black));
        pTools.setLayout(new GridLayout(0,1));
        jfrm.add(pTools);

        JMenuBar menuBar = new JMenuBar();

        JMenu mCreateInterview = new JMenu("Создать интервью");
        menuBar.add(mCreateInterview);

        JMenu mPassInterview = new JMenu("Пройти опрос");
        menuBar.add(mPassInterview);

        JMenu mStats = new JMenu("Статистика");
        menuBar.add(mStats);

        jfrm.setJMenuBar(menuBar);

        //Настройки главного окна
        jfrm.setLayout(new FlowLayout());
        jfrm.setMinimumSize(new Dimension(300,400));
        //jfrm.setResizable(false);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setLocationRelativeTo(null);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e) { }

        //Пункт "Создать опрос" в верхнем меню
        mCreateInterview.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                String resp = JOptionPane.showInputDialog("Введите название опроса");

                JButton addQA = new JButton("Добавить вопрос");
                JButton createInterviewButton = new JButton("Создать опрос");
                JDialog createInterviewDialog = new JDialog(jfrm,"Создание опроса", true);
                //createInterviewDialog.setResizable(false);
                JLabel createInterviewLabel = new JLabel("Введите вопрос", SwingConstants.CENTER);
                JTextArea textArea = new JTextArea(5,20);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);

                interview = new Interview();
                interview.createInterview(resp);

                if(!resp.equals("")) {
                    //Кнопка добавить вопрос/ответ
                    addQA.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (addQA.getText().equals("Добавить ответ")){
                                for (String s: textArea.getText().split("\n")) {
                                    answer = new Answer();
                                    answer.createAnswer(s);
                                    question.addAnswer(answer);
                                }

                                addQA.setText("Добавить вопрос");
                                createInterviewLabel.setText("Введите вопрос");
                            }else {
                                question = new Question();
                                question.createQuestion(textArea.getText());
                                interview.addQuestion(question);

                                addQA.setText("Добавить ответ");
                                createInterviewLabel.setText("Введите ответы");
                            }
                            textArea.setText(null);
                        }
                    });

                    //Кнопка создать опрос
                    createInterviewButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(checkInterview(interview)){

                                Controller.interviews.add(interview);
                                Save.write("C:\\Users\\Sega\\IdeaProjects\\Interview\\Interview.txt");
                                createInterviewDialog.dispatchEvent(new WindowEvent(createInterviewDialog, WindowEvent.WINDOW_CLOSING));
                            }
                            else {
                                JOptionPane.showMessageDialog(jfrm, "Некоректные данные");
                                createInterviewDialog.dispatchEvent(new WindowEvent(createInterviewDialog, WindowEvent.WINDOW_CLOSING));
                            }
                        }
                    });
                    createInterviewDialog.setLayout(new GridLayout(0,1));
                    createInterviewDialog.setMinimumSize(new Dimension(300,300));
                    createInterviewDialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                    JPanel buttons = new JPanel();

                    createInterviewDialog.add(createInterviewLabel, BorderLayout.CENTER);
                    createInterviewDialog.add(textArea);
                    createInterviewDialog.add(buttons);
                    buttons.add(createInterviewButton);
                    buttons.add(addQA);
                    createInterviewDialog.setLocationRelativeTo(null);
                    createInterviewDialog.setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(createInterviewDialog, "WTF?! Why empty string???");
                }
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        JButton nextQuestionButton = new JButton("Дальше");
        //Пункт "Пройти опрос" в верхнем меню
        mPassInterview.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                JButton passInterviewButton = new JButton("Пройти опрос");
                JDialog passInterviewDialog = new JDialog(jfrm,"Выбор опроса", true);
                JLabel passInterviewLabel = new JLabel("Выберите опрос", SwingConstants.CENTER);
                passInterviewDialog.setLayout(new GridLayout(0,1));
                passInterviewDialog.setMinimumSize(new Dimension(300,300));
                //passInterviewDialog.setResizable(false);
                passInterviewDialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                ArrayList<String> stationsName = new ArrayList();
                for (int i = 0; i < Controller.interviews.size(); i++) {
                    stationsName.add(Controller.interviews.get(i).getName());
                }
                JList list = new JList(stationsName.toArray());
                JScrollPane scroll = new JScrollPane(list);
                list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        indexInterview = list.getSelectedIndex();
                        if (indexInterview != -1) {
                        } else {
                        }
                    }
                });

                //Кнопка Пройти опрос в меню Пройти опрос
                passInterviewButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        passInterviewDialog.dispatchEvent(new WindowEvent(passInterviewDialog, WindowEvent.WINDOW_CLOSING));
                        pTools.removeAll();
                        interviewIsPassed = false;

                        indexOfQuestion = 0;
                        Interview thisInterview = Controller.interviews.get(indexInterview);

                        ButtonGroup bg = new ButtonGroup();
                        JRadioButton rb;
                        nextQuestionButton.setVisible(true);

                        JLabel interviewNameLabel = new JLabel(thisInterview.getName(), SwingConstants.CENTER);
                        pTools.add(interviewNameLabel);
                        interviewNameLabel = new JLabel("<html>" + (indexOfQuestion + 1) + ". " +
                                thisInterview.questions.get(indexOfQuestion).getQuestion() + "</html>");
                        pTools.add(interviewNameLabel);

                        //Выводим варианты ответов и реагируем на выбор ответов
                        for (int i = 0; i < thisInterview.questions.get(indexOfQuestion).answers.size(); i++) {
                            rb = new JRadioButton("<html>" + thisInterview.questions.get(indexOfQuestion).answers.get(i).getAnswer() +
                                    "</html>");
                            bg.add(rb);
                            pTools.add(rb);
                            int jAnswer = i;
                            rb.addItemListener(new ItemListener() {
                                @Override
                                public void itemStateChanged(ItemEvent e) {
                                    if (e.getStateChange() == ItemEvent.SELECTED) {
                                        for (int k = 0; k < thisInterview.questions.get(indexOfQuestion).answers.size(); k++) {
                                            if (k == jAnswer)
                                                thisInterview.questions.get(indexOfQuestion).answers.get(k).setSelected(true);
                                            else
                                                thisInterview.questions.get(indexOfQuestion).answers.get(k).setSelected(false);
                                        }
                                    }
                                }
                            });
                        }
                        pTools.add(nextQuestionButton);
                    }
                });

                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scroll.setPreferredSize(new Dimension(290,200));
                passInterviewDialog.add(passInterviewLabel, BorderLayout.CENTER);
                passInterviewDialog.add(scroll,BorderLayout.CENTER);
                JPanel jPanel = new JPanel();
                jPanel.add(passInterviewButton);
                passInterviewDialog.add(jPanel);
                passInterviewDialog.setLocationRelativeTo(null);
                passInterviewDialog.setVisible(true);
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        if (indexOfQuestion == -1) nextQuestionButton.setVisible(false);
        //Кнопка следующий вопрос
        nextQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexOfQuestion++;
                pTools.removeAll();

                Interview thisInterview = Controller.interviews.get(indexInterview);
                if (indexOfQuestion == (thisInterview.questions.size())) {
                    pTools.removeAll();
                    JLabel passedInterviewLabel = new JLabel("<html>Поздравляем с прохождением опроса! " +
                            "Зайдите в раздел результаты опроса, чтобы посмотреть выбранные ответы</html>");
                    pTools.add(passedInterviewLabel);
                    interviewIsPassed = true;
                }else {
                ButtonGroup bg = new ButtonGroup();
                JRadioButton rb;

                JLabel interviewNameLabel = new JLabel(thisInterview.getName(), SwingConstants.CENTER);
                pTools.add(interviewNameLabel);
                interviewNameLabel = new JLabel("<html>" + (indexOfQuestion + 1) + ". " +
                        thisInterview.questions.get(indexOfQuestion).getQuestion() + "</html>");
                pTools.add(interviewNameLabel);

                for (int i = 0; i < thisInterview.questions.get(indexOfQuestion).answers.size(); i++) {
                        rb = new JRadioButton("<html>" +
                                thisInterview.questions.get(indexOfQuestion).answers.get(i).getAnswer() + "</html>");
                        bg.add(rb);
                        pTools.add(rb);
                        int jAnswer = i;
                        rb.addItemListener(new ItemListener() {
                            @Override
                            public void itemStateChanged(ItemEvent e) {
                                if (e.getStateChange() == ItemEvent.SELECTED) {
                                    for (int k = 0; k < thisInterview.questions.get(indexOfQuestion).answers.size(); k++) {
                                        if (k == jAnswer)
                                            thisInterview.questions.get(indexOfQuestion).answers.get(k).setSelected(true);
                                        else
                                            thisInterview.questions.get(indexOfQuestion).answers.get(k).setSelected(false);
                                    }
                                }
                            }
                        });
                        pTools.add(nextQuestionButton);
                }}
            }
        });

        //Пункт "Результаты" в верхнем меню
        mStats.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                JDialog statsDialog = new JDialog(jfrm,"Результаты опроса", true);
                statsDialog.setLayout(new GridLayout(0,1));
               // statsDialog.setResizable(false);
                JPanel statsPanel = new JPanel();
                statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
                JScrollPane scroll = new JScrollPane(statsPanel);

                if (!interviewIsPassed){
                    JOptionPane.showMessageDialog(jfrm, "Пройдите сначала опрос!");
                }
                else {
                    statsDialog.setMinimumSize(new Dimension(300, 300));
                    statsDialog.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                    JLabel jl;
                    Interview thisInterview = Controller.interviews.get(indexInterview);
                    for (int i = 0; i < thisInterview.questions.size(); i++) {
                        jl = new JLabel("<html>" + (i + 1) + ". " +
                                thisInterview.questions.get(i).getQuestion() + "</html>");
                        statsPanel.add(jl);

                        for (int j = 0; j < thisInterview.questions.get(i).answers.size(); j++) {
                            if (thisInterview.questions.get(i).answers.get(j).getSelected()) {
                                jl = new JLabel("<html>" + (j + 1) + ") " +
                                        thisInterview.questions.get(i).answers.get(j).getAnswer() + "</html>");
                                Font font = new Font("Courier", Font.BOLD, 12);
                                jl.setFont(font);
                            } else
                                jl = new JLabel("<html>" + (j + 1) + ") " +
                                        thisInterview.questions.get(i).answers.get(j).getAnswer() + "</html>");
                            statsPanel.add(jl);
                        }
                        jl = new JLabel(" ");
                        statsPanel.add(jl);
                    }

                    statsDialog.add(scroll);
                    statsDialog.setLocationRelativeTo(null);
                    statsDialog.setVisible(true);
                }
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        jfrm.pack();
        jfrm.setVisible(true);
    }
}