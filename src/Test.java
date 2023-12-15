import Controller.ControllerPacmanGame;
import Model.Game.PacmanGame;
import Model.Game.Maze;

public class Test {
    public static void main(String[] args) throws Exception {

        // the originalClassic layout is the default used layout for the maze
        final String mazeDefaultLayout = "src/Layouts/originalClassic.lay";
        //Maze maze = new Maze(mazeDefaultLayout);

        PacmanGame pacmanGame = new PacmanGame(mazeDefaultLayout, 50);
        ControllerPacmanGame controllerPacmanGame = new ControllerPacmanGame(pacmanGame);
    }
}