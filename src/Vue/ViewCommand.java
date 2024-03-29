package Vue;

import Controller.AbstractController;
import Model.Game.Game;
import Model.Game.GameState;
import Model.Game.PacmanGame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ViewCommand implements Observer {
    private JFrame windowCommand;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel leftBottomPanel;
    private GridLayout mainLayout;
    private GridLayout topLayout;
    private GridLayout bottomLayout;
    private GridLayout leftBottomLayout;
    private JButton restartBtn;
    private JButton pauseBtn;
    private JButton runBtn;
    private JButton stepBtn;
    private JSlider slider;
    private JLabel sliderLabel;
    private JLabel text;
    private AbstractController controller;
    private Game game;
    private JFrame imageFrame;

    static final int slider_min = 0;
    static final int slider_max = 10;
    public static final int slider_init = 2;
    private final String GAME_OVER_IMAGE_PATH = "out/production/omar-arharbi-packman/Icons/game_over.png";
    private final String YOU_WIW_IMAGE_PATH = "out/production/omar-arharbi-packman/Icons/you_win.png";


    public ViewCommand(AbstractController controller, Game game) {
        this.controller = controller;
        this.game = game;
        this.game.addObserver(this);

        initButtons();
        initText();
        initSlider();

        //Panels initializations
        initLeftBottomPanel();
        initBottomPanel();
        initTopPanel();
        initMainPanel();

        initCommandWindow();
        setListeners();
    }
    private void setTurn(int turn) {
        text.setText("Turn : " + turn);
    }
    private void initButtons() {
        Icon icon_restart = new ImageIcon("src/Icons/icon_restart.png");
        Icon icon_run = new ImageIcon("src/Icons/icon_run.png");
        Icon icon_step = new ImageIcon("src/Icons/icon_step.png");
        Icon icon_pause = new ImageIcon("src/Icons/icon_pause.png");

        restartBtn = new JButton(icon_restart);
        runBtn = new JButton(icon_run);
        stepBtn = new JButton(icon_step);
        pauseBtn = new JButton(icon_pause);

        restartBtn.setEnabled(false);
        stepBtn.setEnabled(true);
        pauseBtn.setEnabled(false);
    }
    /*
     * Set the behavior after a click of each button
     */
    private void setListeners() {
        runBtn.addActionListener(e -> {
            runBtn.setEnabled(false);
            restartBtn.setEnabled(true);
            stepBtn.setEnabled(false);
            pauseBtn.setEnabled(true);

            controller.play();
        });
        pauseBtn.addActionListener(e -> {
            runBtn.setEnabled(true);
            restartBtn.setEnabled(true);
            stepBtn.setEnabled(true);
            pauseBtn.setEnabled(false);

            controller.pause();
        });
        restartBtn.addActionListener(e -> {
            runBtn.setEnabled(true);
            restartBtn.setEnabled(false);
            stepBtn.setEnabled(true);
            pauseBtn.setEnabled(false);

            controller.restart();
        });
        stepBtn.addActionListener(e -> {
            runBtn.setEnabled(true);
            restartBtn.setEnabled(true);
            stepBtn.setEnabled(true);
            pauseBtn.setEnabled(false);

            controller.step();
        });
        slider.addChangeListener(e->{
            controller.setTurnsBySecond(slider.getValue());
        });
    }
    private void initText() {
        text = new JLabel("", SwingConstants.CENTER);
        setTurn(0);
    }

    private void initSlider() {
        sliderLabel = new JLabel("Number of turn per second", JLabel.CENTER);
        slider = new JSlider(JSlider.HORIZONTAL, slider_min, slider_max,
                slider_init);

        slider.setMajorTickSpacing(1);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
    }
    private void initLeftBottomPanel() {
        leftBottomPanel = new JPanel();
        leftBottomLayout = new GridLayout(2, 1);
        leftBottomPanel.setLayout(leftBottomLayout);

        leftBottomPanel.add(sliderLabel);
        leftBottomPanel.add(slider);
    }
    private void initBottomPanel() {
        bottomPanel = new JPanel();
        bottomLayout = new GridLayout(1, 2);
        bottomPanel.setLayout(bottomLayout);

        bottomPanel.add(leftBottomPanel);
        bottomPanel.add(text);
    }
    private void initTopPanel() {
        topPanel = new JPanel();
        topLayout = new GridLayout(1, 4);
        topPanel.setLayout(topLayout);

        topPanel.add(restartBtn);
        topPanel.add(runBtn);
        topPanel.add(stepBtn);
        topPanel.add(pauseBtn);
    }
    private void initMainPanel() {
        mainPanel = new JPanel();
        mainLayout = new GridLayout(2, 1);
        mainPanel.setLayout(mainLayout);

        mainPanel.add(topPanel);
        mainPanel.add(bottomPanel);
    }
    private void initCommandWindow() {
        windowCommand = new JFrame();
        windowCommand.setTitle("Commande");
        windowCommand.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowCommand.setSize(new Dimension(700, 300));

        Dimension windowSize = windowCommand.getSize();
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x  - (int)(windowSize.width*1.3) ;
        int dy = centerPoint.y - windowSize.height / 2;
        windowCommand.setLocation(dx, dy);

        windowCommand.add(mainPanel);
        windowCommand.setVisible(true);
    }

    /*
     * @param String path : path to the image that we will display     *
     *  an image when the game is finished to express to the player if he won or lost
     */
    private void displayEndGameImage(String path) throws InterruptedException {
        imageFrame = new JFrame();
        imageFrame.setUndecorated(true);
        imageFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        ImageIcon originalIcon = new ImageIcon(path);

        JLabel imageLabel = new JLabel(originalIcon);
        imageFrame.add(imageLabel);
        imageFrame.setVisible(true);
        Thread.sleep(500);
        imageFrame.setVisible(false);
}


    @Override
    public void update(Observable o, Object arg) {
        setTurn((int) arg);

        try {
            if (game.getCurrentState() == GameState.GAME_OVER) {
                displayEndGameImage(GAME_OVER_IMAGE_PATH);
            }else if (game.getCurrentState() == GameState.VICTORY){
                displayEndGameImage(YOU_WIW_IMAGE_PATH);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

