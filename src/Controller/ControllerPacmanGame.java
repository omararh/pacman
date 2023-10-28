package Controller;
import Model.Game.Maze;
import Model.Game.PacmanGame;
import Vue.ViewPacmanGame;

import javax.swing.*;
import java.io.File;

public class ControllerPacmanGame extends AbstractController {

    private final String mazeLayoutsBasePath = "out/production/omar-arharbi-packman/Layouts/";
    private ViewPacmanGame viewPacmanGame;

    public ControllerPacmanGame(PacmanGame pacmanGame) {
        super(pacmanGame);
        this.viewPacmanGame = new ViewPacmanGame(pacmanGame);
        populateMazeLayoutsMenu();
        setupMazeLayoutsMenuListener();
    }

    private void populateMazeLayoutsMenu() {
        File layoutsDirectory = new File(mazeLayoutsBasePath);
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
            String newMazePath = mazeLayoutsBasePath + selectedLayout + ".lay";
            updateMaze(newMazePath);
        });
    }

    private void updateMaze(String mazePath) {
        try {
            Maze maze = new Maze(mazePath);
            viewPacmanGame.getPanelPacmanGame().setMaze(maze);
            viewPacmanGame.getPanelPacmanGame().repaint();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to update maze: " + ex.getMessage(), ex);
        }
    }
}
