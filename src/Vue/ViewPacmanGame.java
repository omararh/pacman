package Vue;
import Model.Game.Maze;
import Model.Game.PacmanGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ViewPacmanGame extends JFrame {
    final String mazeLayoutsBasePath = "out/production/omar-arharbi-packman/Layouts/";
    File layoutsDirectory = new File(mazeLayoutsBasePath);
    File[] filesInTheLayoutsDirectory;
    private JComboBox<String> mazeLayoutsMenu;



    public ViewPacmanGame(PacmanGame pacmanGame){
        super("Pacman Game");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700, 700);
        PanelPacmanGame panelPacmanGame = new PanelPacmanGame(pacmanGame.getMaze());
        this.add(panelPacmanGame);

        // adding a menu for changing the layouts
        mazeLayoutsMenu = new JComboBox<>();

        // menu content automation
        filesInTheLayoutsDirectory = layoutsDirectory.listFiles();
        if(filesInTheLayoutsDirectory != null) {
            for (File file : filesInTheLayoutsDirectory) {
                if (file.isFile() && file.getName().endsWith(".lay")) {
                    String layFileWithOutExtension = file.getName().replaceFirst("[.][^.]+$", "");
                    mazeLayoutsMenu.addItem(layFileWithOutExtension);
                }
            }
        }

        // Add ActionListener to the JComboBox
        mazeLayoutsMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedLayout = (String) mazeLayoutsMenu.getSelectedItem();
                String newMaze = mazeLayoutsBasePath + selectedLayout + ".lay";
                try {
                    Maze maze = new Maze(newMaze);
                    panelPacmanGame.setMaze(maze);
                    panelPacmanGame.repaint();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JLabel menuLabel = new JLabel("Choose the maze layout :");

        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelMenu.add(menuLabel);
        panelMenu.add(mazeLayoutsMenu);

        this.add(panelMenu, BorderLayout.NORTH);
        this.setVisible(true);
    }

}
