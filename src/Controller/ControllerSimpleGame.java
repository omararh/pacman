package Controller;
import Model.Game.SimpleGame;
import Vue.ViewSimpleGame;

public class ControllerSimpleGame extends AbstractController {
    public ControllerSimpleGame(SimpleGame simpleGame) {
        super(simpleGame);
        ViewSimpleGame simpleVue = new ViewSimpleGame(simpleGame);
    }
}
