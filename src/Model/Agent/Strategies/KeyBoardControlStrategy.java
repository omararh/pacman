package Model.Agent.Strategies;

import Model.Agent.Agent;
import Model.Agent.AgentAction;
import Model.Game.PacmanGame;

// ------- In this strategy pacman is controlled by the key board -------------- //
public class KeyBoardControlStrategy extends MouvementStrategy {
    private AgentAction action;

    public KeyBoardControlStrategy(PacmanGame game, AgentAction action) {
        super(game);
        this.action = action;
    }

    @Override
    public AgentAction getAction(PacmanGame pacmanGame, Agent agent) {
        return this.action;
    }
}
