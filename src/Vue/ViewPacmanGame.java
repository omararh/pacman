package Vue;
import Model.Agent.Agent;
import Model.Agent.PositionAgent;
import Model.Game.PacmanGame;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class ViewPacmanGame extends JFrame implements Observer {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 700;
    private static final String WINDOW_TITLE = "Pacman Game";

    private JComboBox<String> mazeLayoutsMenu;
    private JLabel scoreLabel;
    private PanelPacmanGame panelPacmanGame;
    private int scoreToBeDisplayedInUI;

    public ViewPacmanGame(PacmanGame pacmanGame){
        super(WINDOW_TITLE);
        initializeWindow();
        initializePanelPacmanGame(pacmanGame);
        initializeScoreLabel();
        initializeMazeLayoutsMenu();
        addComponentsToWindow();
        this.scoreToBeDisplayedInUI = 0;
        pacmanGame.addObserver(this);
    }

    private void initializeWindow() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    private void initializeScoreLabel() {
        scoreLabel = new JLabel("Score: 0");
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
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(menuLabel);
        topPanel.add(mazeLayoutsMenu);
        topPanel.add(scoreLabel);

        this.add(topPanel, BorderLayout.NORTH);
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
        PacmanGame game = (PacmanGame) o;
        updatePacmanPosition(game);
        updateGhostsPosition(game);
        updateMazeState(game);
        this.scoreToBeDisplayedInUI = game.getScore();
        scoreLabel.setForeground(Color.GREEN);
        scoreLabel.setText("Score: " + this.scoreToBeDisplayedInUI);
        panelPacmanGame.repaint();
        //System.out.println("state --> " + game.getGameState());
    }

    private void updatePacmanPosition(PacmanGame game) {
        ArrayList<PositionAgent> pacmanPositions = new ArrayList<>();
        pacmanPositions.add(game.pacman.getPosition());
        panelPacmanGame.setPacmans_pos(pacmanPositions);
    }

    private void updateGhostsPosition(PacmanGame game){
        ArrayList<PositionAgent> ghostPositions = new ArrayList<>();
        for(Agent ghost : game.ghosts) {
            ghostPositions.add(ghost.getPosition());
        }
        panelPacmanGame.setGhosts_pos(ghostPositions);
    }

    private void updateMazeState(PacmanGame game) {
        int x = game.pacman.getPosition().getX();
        int y = game.pacman.getPosition().getY();
        game.getMaze().setFood(x, y, false);
        game.getMaze().setCapsule(x, y, false);
    }
}
