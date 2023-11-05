package Vue;
import Model.Game.PacmanGame;
import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ViewPacmanGame extends JFrame implements Observer {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 700;
    private static final String WINDOW_TITLE = "Pacman Game";

    private JComboBox<String> mazeLayoutsMenu;
    private PanelPacmanGame panelPacmanGame;

    public ViewPacmanGame(PacmanGame pacmanGame){
        super(WINDOW_TITLE);
        initializeWindow();
        initializePanelPacmanGame(pacmanGame);
        initializeMazeLayoutsMenu();
        addComponentsToWindow();
        pacmanGame.addObserver(this);
    }

    private void initializeWindow() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    private void initializePanelPacmanGame(PacmanGame pacmanGame) {
        panelPacmanGame = new PanelPacmanGame(pacmanGame.getMaze());
    }

    private void initializeMazeLayoutsMenu() {
        mazeLayoutsMenu = new JComboBox<>();
    }

    private void addComponentsToWindow() {
        this.add(panelPacmanGame, BorderLayout.CENTER);

        JLabel menuLabel = new JLabel("Choose the maze layout :");
        JPanel panelMenu = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelMenu.add(menuLabel);
        panelMenu.add(mazeLayoutsMenu);

        this.add(panelMenu, BorderLayout.NORTH);
        this.setVisible(true);
    }

    public JComboBox<String> getMazeLayoutsMenu() {
        return mazeLayoutsMenu;
    }

    public PanelPacmanGame getPanelPacmanGame() {
        return panelPacmanGame;
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
