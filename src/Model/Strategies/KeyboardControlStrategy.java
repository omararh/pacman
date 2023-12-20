package Model.Strategies;

import Model.Agent.Agent;
import Model.Agent.AgentAction;
import Model.Game.PacmanGame;

// ------- In this strategy pacman is controlled by the key board -------------- //
public class KeyboardControlStrategy extends MouvementStrategy{
    public KeyboardControlStrategy(PacmanGame game) {
        super(game);
    }

    @Override
    public AgentAction getAction(PacmanGame pacmanGame, Agent agent) {
        return null;
    }
}
