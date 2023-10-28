package Controller;
import Model.Game.PacmanGame;
import Vue.ViewPacmanGame;

public class ControllerPacmanGame extends AbstractController {
    final String mazeLayoutsBasePath = "out/production/omar-arharbi-packman/Layouts/";

    public ControllerPacmanGame(PacmanGame pacmanGame) {
        super(pacmanGame);
        ViewPacmanGame viewPacmanGame = new ViewPacmanGame(pacmanGame);
    }
}
