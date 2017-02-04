package GraphicsInterface;

import javax.swing.*;
        import javax.swing.event.ChangeEvent;
        import javax.swing.event.ChangeListener;
        import javax.swing.event.ListSelectionEvent;
        import javax.swing.event.ListSelectionListener;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.awt.event.WindowEvent;
        import java.time.LocalDateTime;
        import java.util.ArrayList;
        import Controller.Controller;
        import Route.Route;
        import Station.*;

        import static Controller.Controller.trains;
        import static GraphicsInterface.GraphicInterface.*;
        import static Controller.Controller.*;
        import static java.time.LocalDateTime.now;

/**
 * Created by Sega on 18.01.2017.
 */
public class ActionListeners {
    private static int index;
    private static int velocity = 1;

    private static void messageBox(Component component, String string){
        JOptionPane.showMessageDialog(component, string);
    }

    public static void createListeners(){
        //Пункт "Открыть" в верхнем меню
        miOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Load.load(System.getProperty("user.dir") + "\\trainMap.txt");
                messageBox(jfrm, "Карта загружена!");
            }
        });

        //Пункт "Сохранить" в верхнем меню
        miSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Save.write(System.getProperty("user.dir") + "\\trainMap.txt");
                messageBox(jfrm, "Карта сохранена!");
            }
        });

        //Пункт "Выход" в верхнем меню
        miExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jfrm.dispatchEvent(new WindowEvent(jfrm, WindowEvent.WINDOW_CLOSING));
            }
        });

        //Пункт "добавить - станцию" в верхнем меню
        miStation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //("Введите имя и координаты станции через запятую(без пробелов!)");
                JDialog jd = new JDialog(jfrm,"Добавить станцию", true);
                jd.setLayout(new FlowLayout());
                jd.setMinimumSize(new Dimension(300,250));
                jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                jd.setResizable(false);
                jd.setLocationRelativeTo(null);

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new GridLayout(5,1));
                jd.add(jPanel);

                JLabel jLabelStation = new JLabel("Название станции", SwingConstants.CENTER);
                jPanel.add(jLabelStation);
                JTextField jTextFieldStation = new JTextField(20);
                jPanel.add(jTextFieldStation);

                JLabel jLabelXY = new JLabel("Координаты", SwingConstants.CENTER);
                jPanel.add(jLabelXY);

                JPanel jPanelXY = new JPanel();
                jPanelXY.setLayout(new GridLayout(1,2));
                jPanel.add(jPanelXY);

                JPanel jPanelX = new JPanel();
                jPanelXY.add(jPanelX);

                JPanel jPanelY = new JPanel();
                jPanelXY.add(jPanelY);

                JLabel jLabelX = new JLabel("x: ");
                jPanelX.add(jLabelX);
                JTextField jTextFieldX = new JTextField(4);
                jPanelX.add(jTextFieldX);

                JLabel jLabelY = new JLabel("y: ");
                jPanelY.add(jLabelY);
                JTextField jTextFieldY = new JTextField(4);
                jPanelY.add(jTextFieldY);

                JButton jButtonOK = new JButton("Создать");
                JPanel jPanelButtonOK = new JPanel();
                jPanelButtonOK.add(jButtonOK);
                jPanel.add(jPanelButtonOK);

                jButtonOK.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String stringError = "Ошибка! В названии станции должно быть меньше 20 символов, " +
                                "координаты х и у должны быть целым числом и меньше размеров окна (" + TRAIN_MAP_WIDTH +
                                "," + TRAIN_MAP_HEIGHT + ")";
                        try {
                            if (jTextFieldStation.getText().length() <= 20 &&
                                    (jTextFieldStation.getText().length() > 0) &&
                                    (Integer.parseInt(jTextFieldX.getText()) < TRAIN_MAP_WIDTH) &&
                                    (Integer.parseInt(jTextFieldY.getText()) < TRAIN_MAP_HEIGHT) &&
                                    (Integer.parseInt(jTextFieldX.getText()) > 0) &&
                                    (Integer.parseInt(jTextFieldY.getText()) > 0)) {
                                createStation(jTextFieldStation.getText(), Integer.parseInt(jTextFieldX.getText()),
                                        Integer.parseInt(jTextFieldY.getText()));
                                JOptionPane.showMessageDialog(jd, "Создано!");
                                jd.dispatchEvent(new WindowEvent(jd, WindowEvent.WINDOW_CLOSING));
                            }else messageBox(jd, stringError);
                        }catch (NumberFormatException e1){
                            messageBox(jd,stringError);
                        }
                    }
                });

                jd.setVisible(true);
            }
        });

        //Пункт "Добавить - Маршрут" в верхнем меню
        miRoute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jd = new JDialog(jfrm,"Добавить станции", true);
                jd.setLayout(new GridLayout(0,1));
                jd.setMinimumSize(new Dimension(400,200));
                jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                //jd.setResizable(false);
                jd.setLocationRelativeTo(null);

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new FlowLayout());
                jd.add(jPanel);

                JTextField jTextFieldNameOfRoute = new JTextField("Название маршрута", 20);
                jPanel.add(jTextFieldNameOfRoute);

                JPanel jPanelStations = new JPanel();
                jPanelStations.setLayout(new GridLayout(0,3));
                jPanel.add(jPanelStations);

                final ArrayList<Station> stationArrayList = new ArrayList();
                for (int i = 0; i < stations.size(); i++) {
                    final int indexOfStation = i;
                    JButton jButton = new JButton(stations.get(i).name);
                    jPanelStations.add(jButton);
                    jButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            stationArrayList.add(stations.get(indexOfStation));
                            jButton.setVisible(false);
                            if (stationArrayList.size() > 0)
                                jTextFieldNameOfRoute.setText(stationArrayList.get(0).name + " - "
                                        + stationArrayList.get(stationArrayList.size() - 1).name);
                        }
                    });
                }

                JButton jButtonCreateRoute = new JButton("Создать маршрут");
                jPanel.add(jButtonCreateRoute);
                jButtonCreateRoute.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(jTextFieldNameOfRoute.getText().length() != 0
                                && jTextFieldNameOfRoute.getText().length() < 40
                                && stationArrayList.size() < 2) {
                            messageBox(jfrm, "Название маршрута неккоректно, либо количество станций в маршруте < 2!");
                        }else{
                            createRoute(jTextFieldNameOfRoute.getText());
                            for (int i = 0; i < stationArrayList.size(); i++)
                                routes.get(routes.size()-1).addStationToRoute(stationArrayList.get(i));
                            messageBox(jd, "Маршрут создан!");
                            jd.dispatchEvent(new WindowEvent(jd, WindowEvent.WINDOW_CLOSING));
                        }
                    }
                });

                jd.setVisible(true);
            }
        });

        //Пункт "Добавить - Поезд" в верхнем меню
        miTrain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = -1;
                JDialog jd = new JDialog(jfrm,"Добавить поезд", true);
                jd.setLayout(new FlowLayout());
                jd.setMinimumSize(new Dimension(250,550));
                jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                //jd.setResizable(false);
                jd.setLocationRelativeTo(null);

                JPanel jPanelRoute = new JPanel();
                jPanelRoute.setLayout(new BoxLayout(jPanelRoute, BoxLayout.Y_AXIS));
                jd.add(jPanelRoute);

                JLabel jLabel = new JLabel("Выберите маршрут", SwingConstants.CENTER);
                jPanelRoute.add(jLabel);

                //Список маршрутов
                ArrayList<String> routesName = new ArrayList();
                for (int i = 0; i < routes.size(); i++) {
                    routesName.add(routes.get(i).name);
                }
                JList list = new JList(routesName.toArray());
                JScrollPane scroll = new JScrollPane(list);
                list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        index = list.getSelectedIndex();
                    }
                });
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scroll.setPreferredSize(new Dimension(200,80));
                jPanelRoute.add(scroll,BorderLayout.CENTER);
                //---------------

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new GridLayout(0,1,1,15));
                jd.add(jPanel);

                JPanel jPanelNameCapacityPrice = new JPanel();
                jPanelNameCapacityPrice.setLayout(new GridLayout(0,1,1,10));
                jPanel.add(jPanelNameCapacityPrice);

                JTextField jTextFieldNameOfRoute = new JTextField("Имя поезда", 10);
                jPanelNameCapacityPrice.add(jTextFieldNameOfRoute);
                JTextField jTextFieldCapacity = new JTextField("Количество мест", 8);
                jPanelNameCapacityPrice.add(jTextFieldCapacity);
                JTextField jTextFieldPrice = new JTextField("Стоимость билета", 8);
                jPanelNameCapacityPrice.add(jTextFieldPrice);

                //Дата
                JLabel jLabelDate = new JLabel("Дата и время начала движения");
                JPanel jPanelDate = new JPanel();
                jPanelDate.setLayout(new GridLayout(0,1));
                jPanelDate.add(jLabelDate);
                JTextField jTextFieldDate = new JTextField(LocalDateTime.of(now().getYear(),
                        now().getMonth(), now().getDayOfMonth(), now().getHour(),
                        now().getMinute(), now().getSecond()).toString(), 15);
                jPanelDate.add(jTextFieldDate);
                jPanel.add(jPanelDate);

                //Скорость
                JPanel jPanelVelocity = new JPanel();
                jPanelVelocity.setLayout(new BoxLayout(jPanelVelocity, BoxLayout.Y_AXIS));
                JLabel jLabelVelocity = new JLabel("Скорость", SwingConstants.CENTER);
                jPanelVelocity.add(jLabelVelocity);
                //------------------------------------
                JSlider jSliderVelocity = new JSlider(JSlider.HORIZONTAL, 0, 30, 15);
                jSliderVelocity.setMajorTickSpacing(5);
                jSliderVelocity.setMinorTickSpacing(1);
                jSliderVelocity.setPaintTicks(true);
                jSliderVelocity.setPaintLabels(true);
                JPanel jPanelSliderVelocity = new JPanel();
                jSliderVelocity.addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        velocity = jSliderVelocity.getValue();
                    }
                });
                jPanelSliderVelocity.add(jSliderVelocity);
                jPanelVelocity.add(jPanelSliderVelocity);
                jPanel.add(jPanelVelocity);

                JButton jButtonCreateTrain = new JButton("Создать");
                jd.add(jButtonCreateTrain);

                jButtonCreateTrain.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{
                            if(index != -1) {
                                createTrain(jTextFieldNameOfRoute.getText(), routes.get(index),
                                        Integer.parseInt(jTextFieldCapacity.getText()),
                                        Integer.parseInt(jTextFieldPrice.getText()),
                                        LocalDateTime.parse(jTextFieldDate.getText()),
                                        velocity);
                                messageBox(jd, "Поезд создан!");
                                jd.dispatchEvent(new WindowEvent(jd, WindowEvent.WINDOW_CLOSING));
                            } else
                                messageBox(jd, "Ошибка! Неверный формат данных");}
                        catch (java.lang.NumberFormatException e1){
                            messageBox(jd, "Ошибка! Неверный формат данных");
                        }

                    }
                });

                jd.setVisible(true);
            }
        });

        //Пункт "Добавить - Пассажира" в верхнем меню
        miPassenger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jd = new JDialog(jfrm,"Добавить пассажира", true);
                jd.setLayout(new FlowLayout());
                jd.setMinimumSize(new Dimension(200,250));
                jd.setLocationRelativeTo(null);
                jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                JPanel jPanelTrain = new JPanel();
                jPanelTrain.setLayout(new BoxLayout(jPanelTrain, BoxLayout.Y_AXIS));
                jd.add(jPanelTrain);

                JLabel jLabelTrain = new JLabel("Выберите поезд", SwingConstants.CENTER);
                jPanelTrain.add(jLabelTrain);

                //Список поездов
                index = -1;
                ArrayList<String> trainsName = new ArrayList();
                for (int i = 0; i < trains.size(); i++) {
                    trainsName.add(trains.get(i).name);
                }
                JList list = new JList(trainsName.toArray());
                JScrollPane scroll = new JScrollPane(list);
                list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        index = list.getSelectedIndex();
                    }
                });
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scroll.setPreferredSize(new Dimension(90,100));
                jPanelTrain.add(scroll,BorderLayout.CENTER);
                //-------------------------------------------

                JTextField jTextFieldFullName = new JTextField("Ваше полное имя", 10);
                jPanelTrain.add(jTextFieldFullName);

                JButton jButtonCreatePassenger = new JButton("Создать");
                jPanelTrain.add(jButtonCreatePassenger);
                jButtonCreatePassenger.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (index != -1){
                            createPassenger(jTextFieldFullName.getText(), trains.get(index));
                            messageBox(jd, "Пассажир зарегестрирован!");
                            jd.dispatchEvent(new WindowEvent(jd, WindowEvent.WINDOW_CLOSING));
                        } else
                            messageBox(jd, "Выберите поезд!");
                    }
                });

                jd.setVisible(true);
            }
        });

        //Пункт "Удалить - станцию" в верхнем меню
        miStationDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = -1;
                JDialog jd = new JDialog(jfrm,"Удаление станции", true);
                jd.setLayout(new FlowLayout());
                jd.setMinimumSize(new Dimension(160,230));
                jd.setLocationRelativeTo(null);
                jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
                jd.add(jPanel);

                ArrayList<String> stationsName = new ArrayList();
                for (int i = 0; i < stations.size(); i++) {
                    stationsName.add(stations.get(i).name);
                }
                JList list = new JList(stationsName.toArray());
                JScrollPane scroll = new JScrollPane(list);
                list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        index = list.getSelectedIndex();
                    }
                });
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scroll.setPreferredSize(new Dimension(140,150));
                jPanel.add(scroll,BorderLayout.CENTER);

                JButton jButtonDelete = new JButton("Удалить");
                jPanel.add(jButtonDelete);
                jButtonDelete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boolean delete = true;
                        if(index != -1){
                            for (int i = 0; i < routes.size(); i++) {
                                for (int j = 0; j < routes.get(i).listOfStations.size(); j++) {
                                    if (routes.get(i).listOfStations.get(j).equals(stations.get(index))){
                                        delete = false;
                                    }
                                }
                            }
                            if (delete){
                                deleteStation(stations.get(index));
                                messageBox(jd, "Удалено!");
                                jd.dispatchEvent(new WindowEvent(jd, WindowEvent.WINDOW_CLOSING));
                            } else messageBox(jd, "Нельзя удалить станцию через которую проходит маршрут!");
                        }else messageBox(jd, "Сначала выберите станцию!");
                    }
                });

                jd.setVisible(true);
            }
        });

        //Пункт "Удалить - Маршрут" в верхнем меню
        miRouteDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = -1;
                JDialog jd = new JDialog(jfrm,"Удаление маршрута", true);
                jd.setLayout(new FlowLayout());
                jd.setMinimumSize(new Dimension(200,200));
                jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                //jd.setResizable(false);
                jd.setLocationRelativeTo(null);

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
                jd.add(jPanel);

                ArrayList<String> routesName = new ArrayList<>();
                for (int i = 0; i < routes.size(); i++) {
                    routesName.add(routes.get(i).name);
                }
                JList list = new JList(routesName.toArray());
                JScrollPane scroll = new JScrollPane(list);
                list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        index = list.getSelectedIndex();
                    }
                });
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scroll.setPreferredSize(new Dimension(180,120));
                jPanel.add(scroll,BorderLayout.CENTER);

                JButton jButtonDelete = new JButton("Удалить");
                jPanel.add(jButtonDelete);

                jButtonDelete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(index != -1){
                            int n = JOptionPane.showConfirmDialog(jd, "Будут удалены все поезда и пассажиры " +
                                    "на этом маршруте.\n" +
                                    "                       Продолжить?", "Вы уверены?", JOptionPane.OK_CANCEL_OPTION);
                            if (n == JOptionPane.OK_OPTION){
                                //Удаляем все поезда и пассажиров на этом маршруте
                                for (int i = 0; i < routes.get(index).trainList.size(); i++) {
                                    for (int j = 0; j < routes.get(index).trainList.get(i).passengerList.size(); j++) {
                                        deletePassenger(routes.get(index).trainList.get(i).passengerList.get(j));
                                    }
                                    deleteTrain(routes.get(index).trainList.get(i));
                                }
                                deleteRoute(routes.get(index));

                                jd.dispatchEvent(new WindowEvent(jd, WindowEvent.WINDOW_CLOSING));
                                messageBox(jfrm, "Удалено!");
                            }
                        }else{
                            messageBox(jd, "Сначала выберите маршрут, который хотите удалить!");
                        }
                    }
                });

                jd.setVisible(true);
            }
        });

        //Пункт "Удалить - поезд" в верхнем меню
        miTrainDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                index = -1;
                JDialog jd = new JDialog(jfrm,"Удаление поезда", true);
                jd.setLayout(new FlowLayout());
                jd.setMinimumSize(new Dimension(160,230));
                jd.setLocationRelativeTo(null);
                jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
                jd.add(jPanel);

                ArrayList<String> trainsName = new ArrayList();
                for (int i = 0; i < trains.size(); i++) {
                    trainsName.add(trains.get(i).name);
                }
                JList list = new JList(trainsName.toArray());
                JScrollPane scroll = new JScrollPane(list);
                list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        index = list.getSelectedIndex();
                    }
                });
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scroll.setPreferredSize(new Dimension(150,150));
                jPanel.add(scroll);

                JButton jButton = new JButton("Удалить");
                jPanel.add(jButton);
                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(index != -1){
                            if (trains.get(index).passengerList.size() > 0){
                                messageBox(jd, "Нельзя удалить поезд в котором есть пассажиры!");
                            }
                            else{
                                deleteTrain(trains.get(index));
                                messageBox(jd, "Удалено!");
                                jd.dispatchEvent(new WindowEvent(jd, WindowEvent.WINDOW_CLOSING));
                            }
                        }else messageBox(jd, "Сначала выберите поезд!");
                    }
                });

                jd.setVisible(true);
            }
        });

        //Пункт "Удалить - пассажира" в верхнем меню
        miPassengerDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jd = new JDialog(jfrm,"Удаление пассажира", true);
                jd.setLayout(new FlowLayout());
                jd.setMinimumSize(new Dimension(160,230));
                jd.setLocationRelativeTo(null);
                jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
                jd.add(jPanel);

                ArrayList<String> passengersName = new ArrayList();
                for (int i = 0; i < passengers.size(); i++) {
                    passengersName.add(passengers.get(i).fullName);
                }
                JList list = new JList(passengersName.toArray());
                JScrollPane scroll = new JScrollPane(list);
                list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        index = list.getSelectedIndex();
                    }
                });
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scroll.setPreferredSize(new Dimension(150,150));
                jPanel.add(scroll);

                JButton jButton = new JButton("Удалить");
                jPanel.add(jButton);
                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(index != -1){
                            deletePassenger(passengers.get(index));
                            messageBox(jd, "Удалено!");
                            jd.dispatchEvent(new WindowEvent(jd, WindowEvent.WINDOW_CLOSING));
                        }else messageBox(jd, "Сначала выберите пассажира!");
                    }
                });

                jd.setVisible(true);
            }
        });

        //Кнопка "список станций"
        bStationList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jd = new JDialog(jfrm,"Список станций", true);
                jd.setLayout(new GridLayout(0,1));
                jd.setMinimumSize(new Dimension(200,200));
                jd.setLocationRelativeTo(null);
                jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

                ArrayList<String> stationsName = new ArrayList();
                for (int i = 0; i < stations.size(); i++) {
                    stationsName.add(stations.get(i).name);
                }
                JList list = new JList(stationsName.toArray());
                JScrollPane scroll = new JScrollPane(list);
                JLabel jl = new JLabel("Выберите станцию");
                list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        int i = list.getSelectedIndex();
                        if (i != -1){
                            jl.setText("Координаты: " + stations.get(i).x + ", " + stations.get(i).y);
                        }
                        else {
                            jl.setText("Выберите станцию");}
                    }
                });
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scroll.setPreferredSize(new Dimension(180,100));
                jPanel.add(scroll);
                jPanel.add(jl);
                jd.add(jPanel);

                jd.setVisible(true);
            }
        });

        //Кнопка "Список маршрутов"
        bRouteList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jd = new JDialog(jfrm,"Список маршрутов", true);
                jd.setMinimumSize(new Dimension(300,200));
                jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                jd.setLayout(new GridLayout(0,1));

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

                ArrayList<String> routesName = new ArrayList();
                for (int i = 0; i < routes.size(); i++) {
                    routesName.add(routes.get(i).name);
                }
                JList list = new JList(routesName.toArray());
                JScrollPane scroll = new JScrollPane(list);
                JLabel jlRoute = new JLabel("Путь маршрута:");
                JLabel jlTotalLength = new JLabel("Общая длина пути:");
                JLabel jlStations = new JLabel("Количество станций:");
                list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        int i = list.getSelectedIndex();
                        if (i != -1){
                            jlRoute.setText("Путь маршрута: " + routes.get(i).getStationList());
                            jlStations.setText("Количество станций: " + routes.get(i).listOfStations.size());
                            jlTotalLength.setText("Общая длина пути: " + routes.get(i).totalLength);
                        }
                    }
                });
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scroll.setPreferredSize(new Dimension(200,100));
                jd.add(scroll);
                jPanel.add(jlRoute);
                jPanel.add(jlTotalLength);
                jPanel.add(jlStations);
                jd.add(jPanel);
                jd.setLocationRelativeTo(null);

                jd.setVisible(true);
            }
        });

        //Кнопка "Список поездов
        bTrainList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jd = new JDialog(jfrm,"Список поездов", true);
                jd.setLayout(new GridLayout(0,1));
                jd.setMinimumSize(new Dimension(300,250));
                jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

                ArrayList<String> trainsName = new ArrayList();
                for (int i = 0; i < trains.size(); i++) {
                    trainsName.add(trains.get(i).name);
                }
                JList list = new JList(trainsName.toArray());
                JScrollPane scroll = new JScrollPane(list);
                JLabel jlRoute = new JLabel();
                JLabel jlCap = new JLabel();
                JLabel jlPassengersCount = new JLabel();
                JLabel jlPrice = new JLabel();
                JLabel jlVelocity = new JLabel();
                JLabel jlDateStart = new JLabel();
                JLabel jlDateEnd = new JLabel();
                list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        int i = list.getSelectedIndex();
                        if (i != -1){
                            for (Route route: routes) {
                                if (route.trainList.contains(trains.get(i)))
                                    jlRoute.setText("Маршрут: " + route.name);
                            }
                            jlCap.setText("Общая вместимость: " + trains.get(i).capacity);
                            jlPassengersCount.setText("Количество пассажиров: " + trains.get(i).passengerList.size());
                            jlPrice.setText("Цена билета: " + trains.get(i).price);
                            jlVelocity.setText("Скорость поезда: " + trains.get(i).velocity + "px/sec");
                            jlDateStart.setText("Время отправления: " + trains.get(i).timeStart);
                            jlDateEnd.setText("Время прибытия: " + trains.get(i).timeEnd);
                        }
                    }
                });
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scroll.setPreferredSize(new Dimension(290,100));
                jd.add(scroll,BorderLayout.CENTER);
                jPanel.add(jlRoute);
                jPanel.add(jlCap);
                jPanel.add(jlPassengersCount);
                jPanel.add(jlPrice);
                jPanel.add(jlVelocity);
                jPanel.add(jlDateStart);
                jPanel.add(jlDateEnd);
                jd.add(jPanel);
                jd.setLocationRelativeTo(null);
                jd.setVisible(true);
            }
        });

        //Кнопка "Список пассажиров"
        bPassengerList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog jd = new JDialog(jfrm,"Список пассажиров", true);
                jd.setLayout(new GridLayout(0,1));
                jd.setMinimumSize(new Dimension(200,200));
                jd.setLocationRelativeTo(null);
                jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

                ArrayList<String> passengersName = new ArrayList();
                for (int i = 0; i < passengers.size(); i++) {
                    passengersName.add(passengers.get(i).fullName);
                }
                JList list = new JList(passengersName.toArray());
                JScrollPane scroll = new JScrollPane(list);
                JLabel jlTrain = new JLabel("Выберите пассажира");
                list.addListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        int i = list.getSelectedIndex();
                        if (i != -1){
                            jlTrain.setText("Поезд: " + passengers.get(i).train.name);
                        }
                    }
                });
                list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scroll.setPreferredSize(new Dimension(150,120));
                jPanel.add(scroll);
                jPanel.add(jlTrain);
                jd.add(jPanel);

                jd.setVisible(true);
            }
        });

        //Кнопка "Статистика"
        bStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                Статистика за последние 24часа/всего
                колво поездов отправившихся в рейс
                Кол-во проданных билетов
                билетов продано на сумму
                */
                index = 0;
                JDialog jd = new JDialog(jfrm,"Список пассажиров", true);
                jd.setLayout(new GridLayout(0,1));
                jd.setMinimumSize(new Dimension(250,200));
                jd.setLocationRelativeTo(null);
                jd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                jd.add(panel);

                JPanel panelComboBox = new JPanel();
                panelComboBox.setLayout(new BoxLayout(panelComboBox, BoxLayout.Y_AXIS));
                panel.add(panelComboBox);

                JPanel panelLabels = new JPanel();
                panelLabels.setLayout(new GridLayout(0,1));
                panel.add(panelLabels);

                String[] items = {
                        "Всего",
                        "За последние 24 часа"
                };

                JLabel labelTrains = new JLabel("В рейс было отправлено: " + Controller.trains.size() + " поезда(ов)");
                JLabel labelTickets = new JLabel("Проданных билетов: " +  Controller.passengers.size());
                int money = 0;
                for (int i = 0; i < Controller.trains.size(); i++) {
                    money += Controller.trains.get(i).price*Controller.trains.get(i).passengerList.size();
                }
                JLabel labelMoney = new JLabel("Продано билетов на сумму: " + money + "руб.");

                JComboBox comboBox = new JComboBox(items);
                comboBox.setMaximumSize(new Dimension(150,25));
                comboBox.setSelectedIndex(index);
                comboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        index = comboBox.getSelectedIndex();
                        int trainsCount = 0;
                        int tickets = 0;
                        int money = 0;
                        if (index == 0) {
                            trainsCount = trains.size();
                            System.out.println(trains.size());
                            tickets = passengers.size();
                            for (int i = 0; i < trains.size(); i++) {
                                money += trains.get(i).price * trains.get(i).passengerList.size();
                            }

                        }else{
                            for (int i = 0; i < trains.size(); i++) {
                                int seconds = currentTime.getSecond() - trains.get(i).timeStart.getSecond() +
                                        60*(currentTime.getMinute() - trains.get(i).timeStart.getMinute()) +
                                        3600*(currentTime.getHour() - trains.get(i).timeStart.getHour()) +
                                        86400*(currentTime.getDayOfMonth() - trains.get(i).timeStart.getDayOfMonth());
                                if ((seconds < 86400) &&
                                        (currentTime.getMonthValue() == trains.get(i).timeStart.getMonthValue()) &&
                                        (currentTime.getYear() == trains.get(i).timeStart.getYear())){
                                    trainsCount++;
                                    tickets += trains.get(i).passengerList.size();
                                    money += trains.get(i).passengerList.size() * trains.get(i).price;
                                }
                            }
                        }
                        labelTrains.setText("В рейс было отправлено: " + trainsCount + " поезда(ов)");
                        labelTickets.setText("Проданных билетов: " + tickets);
                        labelMoney.setText("Продано билетов на сумму: " + money + "руб.");
                    }
                });
                panelComboBox.add(comboBox);
                panelLabels.add(labelTrains);
                panelLabels.add(labelTickets);
                panelLabels.add(labelMoney);

                jd.setVisible(true);
            }
        });
    }
}