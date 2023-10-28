package Vue;
import Model.Game.PacmanGame;
import javax.swing.*;
import java.awt.*;

public class ViewPacmanGame extends JFrame {
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
    }

    private void initializeWindow() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
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
}
