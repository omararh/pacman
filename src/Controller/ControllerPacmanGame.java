package Controller;
import Model.Game.PacmanGame;
import Vue.ViewCommand;
import Vue.ViewPacmanGame;
public class ControllerPacmanGame extends AbstractController {
    public ControllerPacmanGame(PacmanGame pacmanGame) {
        super(pacmanGame);
        ViewCommand viewCommand = new ViewCommand(this, pacmanGame);
        ViewPacmanGame viewPacmanGame = new ViewPacmanGame(pacmanGame);
    }
}
