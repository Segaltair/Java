package GraphicsInterface;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;

/**
 * Created by Sega on 18.01.2017.
 */
public class GraphicInterface {
    private final static int TOOLS_WIDTH = 200;
    final static int TRAIN_MAP_HEIGHT = 700;
    final static int TRAIN_MAP_WIDTH = 1000;
    private final static int WINDOW_HEIGHT = TRAIN_MAP_HEIGHT + 50;
    private final static int WINDOW_WIDTH = TRAIN_MAP_WIDTH + TOOLS_WIDTH +20;

    static LocalDateTime currentTime = LocalDateTime.of(now().getYear(), now().getMonth(), now().getDayOfMonth(), now().getHour(), now().getMinute(), now().getSecond());

    //Список активных элементов интерфейса
    static JFrame jfrm;
    static JMenuItem miOpen;
    static JMenuItem miSave;
    static JMenuItem miExit;
    static JMenuItem miStation;
    static JMenuItem miRoute;
    static JMenuItem miTrain;
    static JMenuItem miPassenger;
    static JMenuItem miStationDelete;
    static JMenuItem miRouteDelete;
    static JMenuItem miTrainDelete;
    static JMenuItem miPassengerDelete;
    static JButton bStationList;
    static JButton bRouteList;
    static JButton bTrainList;
    static JButton bPassengerList;
    static JButton bStats;
    static JLabel lTime;

    public GraphicInterface(){
        GraphicTrainMap pp;
        jfrm = new JFrame("Карта поездов");

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e) { }

        JMenuBar menuBar = new JMenuBar();

        JMenu mFile = new JMenu("Файл");
        miOpen = new JMenuItem("Открыть");
        miSave = new JMenuItem("Сохранить");
        miExit = new JMenuItem("Выход");
        mFile.add(miOpen);
        mFile.add(miSave);
        mFile.add(miExit);
        menuBar.add(mFile);

        JMenu mEdit = new JMenu("Добавить");
        miStation = new JMenuItem("Станцию");
        miRoute = new JMenuItem("Маршрут");
        miTrain = new JMenuItem("Поезд");
        miPassenger = new JMenuItem("Пассажира");
        mEdit.add(miRoute);
        mEdit.add(miStation);
        mEdit.add(miTrain);
        mEdit.add(miPassenger);
        menuBar.add(mEdit);

        JMenu mDelete = new JMenu("Удалить");
        miStationDelete = new JMenuItem("Станцию");
        miRouteDelete = new JMenuItem("Маршрут");
        miTrainDelete = new JMenuItem("Поезд");
        miPassengerDelete = new JMenuItem("Пассажира");
        mDelete.add(miRouteDelete);
        mDelete.add(miStationDelete);
        mDelete.add(miTrainDelete);
        mDelete.add(miPassengerDelete);
        menuBar.add(mDelete);

        //JMenu mHelp = new JMenu("Помощь");
        //JMenuItem miHowToUse = new JMenuItem("Как пользоваться");
        //mHelp.add(miHowToUse);
        //menuBar.add(mHelp);

        jfrm.setJMenuBar(menuBar);

        //Настройки главного окна
        jfrm.setLayout(new FlowLayout());
        jfrm.setMinimumSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfrm.setLocationRelativeTo(null);

        //Панель информации
        JPanel pTools = new JPanel();
        pTools.setPreferredSize(new Dimension(TOOLS_WIDTH, TRAIN_MAP_HEIGHT));
        pTools.setOpaque(true);
        pTools.setBorder(BorderFactory.createLineBorder(Color.black));
        pTools.setLayout(new GridLayout(7,1,30,50));

        JLabel lInfo = new JLabel("Панель информации", SwingConstants.CENTER);
        bStationList = new JButton("Список станций");
        bRouteList = new JButton("Список маршрутов");
        bTrainList = new JButton("Список поездов");
        bPassengerList = new JButton("Список пассажиров");
        bStats = new JButton("Статистика");
        lTime = new JLabel(currentTime.toString(), SwingConstants.CENTER);
        pTools.add(lInfo);
        pTools.add(bStationList);
        pTools.add(bRouteList);
        pTools.add(bTrainList);
        pTools.add(bPassengerList);
        pTools.add(bStats);
        pTools.add(lTime);

        jfrm.add(pTools);
        pp = new GraphicTrainMap(TRAIN_MAP_WIDTH, TRAIN_MAP_HEIGHT);
        jfrm.add(pp);
        pTools.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        ActionListeners.createListeners();

        jfrm.pack();
        jfrm.setVisible(true);
    }
}