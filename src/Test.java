import Controller.ControllerPacmanGame;
import Model.Game.PacmanGame;
import Model.Game.Maze;

public class Test {
    public static void main(String[] args) throws Exception {
        // Problem with file accessibility ... using the absolute path
        Maze maze = new Maze("../omar-arharbi-packman/out/production/omar-arharbi-packman/Layouts/originalClassic.lay");
        PacmanGame pacmanGame = new PacmanGame(maze, 50);
        ControllerPacmanGame controllerPacmanGame = new ControllerPacmanGame(pacmanGame);
    }
}