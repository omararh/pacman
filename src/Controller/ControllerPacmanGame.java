package Controller;
import Model.Agent.Strategies.KeyBoardControlStrategy;
import Model.Game.Maze;
import Model.Game.PacmanGame;
import Vue.ViewPacmanGame;
import Model.Agent.AgentAction;


import javax.swing.*;
import java.io.File;


public class ControllerPacmanGame extends AbstractController implements PacmanGameActions {
    private final String MAZE_LAYOUTS_BASE_PATH = "out/production/omar-arharbi-packman/Layouts/";
    private ViewPacmanGame viewPacmanGame;

    public ControllerPacmanGame(PacmanGame pacmanGame) {
        super(pacmanGame);
        this.viewPacmanGame = new ViewPacmanGame(pacmanGame);
        KeyboardEventListener keyboardEventListener = new KeyboardEventListener(this.viewPacmanGame, this);
        populateMazeLayoutsMenu();
        setupMazeLayoutsMenuListener();
    }

    private void populateMazeLayoutsMenu() {
        File layoutsDirectory = new File(MAZE_LAYOUTS_BASE_PATH);
        File[] filesInTheLayoutsDirectory = layoutsDirectory.listFiles();
        JComboBox<String> mazeLayoutsMenu = viewPacmanGame.getMazeLayoutsMenu();

        if (filesInTheLayoutsDirectory != null) {
            for (File file : filesInTheLayoutsDirectory) {
                if (file.isFile() && file.getName().endsWith(".lay")) {
                    String layFileWithoutExtension = file.getName().replaceFirst("[.][^.]+$", "");
                    mazeLayoutsMenu.addItem(layFileWithoutExtension);
                }
            }
        }
    }

    private void setupMazeLayoutsMenuListener() {
        JComboBox<String> mazeLayoutsMenu = viewPacmanGame.getMazeLayoutsMenu();
        mazeLayoutsMenu.addActionListener(e -> {
            String selectedLayout = (String) mazeLayoutsMenu.getSelectedItem();
            String newMazePath = MAZE_LAYOUTS_BASE_PATH + selectedLayout + ".lay";
            updateMaze(newMazePath);
        });
    }

    private void updateMaze(String mazePath) {
        try {
            Maze maze = new Maze(mazePath);
            viewPacmanGame.getPanelPacmanGame().setMaze(maze);
            ((PacmanGame) game).setMaze(maze);
            viewPacmanGame.requestFocus();
            this.restart();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to update maze: " + ex.getMessage(), ex);
        }
    }
    /*
     * defining what the entry keys are signifying for the model
     * @param char keyChar
     * @param boolean isPressed
     * @return AgentAction
     */
    public void handleKeyboardMovement(char keyChar, boolean isPressed) {
        if(!isPressed){
            return;
        }

        AgentAction action;
        switch (keyChar) {
            case 'w' -> action = new AgentAction(AgentAction.NORTH); // top
            case 's' -> action = new AgentAction(AgentAction.SOUTH); // bottom
            case 'a' -> action = new AgentAction(AgentAction.EAST);  // right
            case 'd' -> action = new AgentAction(AgentAction.WEST);  // left
            default -> action = new AgentAction(AgentAction.STOP);
        }
        // move the pacman
        ((PacmanGame) game).pacman.setMouvementStrategy(new KeyBoardControlStrategy((PacmanGame) game, action));
        // move the ghosts
    }
}
