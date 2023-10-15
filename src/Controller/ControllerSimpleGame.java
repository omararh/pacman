package Controller;
import Model.Game.SimpleGame;
import Vue.ViewCommand;
import Vue.ViewSimpleGame;

public class ControllerSimpleGame extends AbstractController {
    public ControllerSimpleGame(SimpleGame simpleGame) {
        super(simpleGame);
        ViewCommand vue = new ViewCommand(this, simpleGame);
        ViewSimpleGame simpleVue = new ViewSimpleGame(simpleGame);
    }
}
