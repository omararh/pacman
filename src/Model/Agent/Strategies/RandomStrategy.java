package Model.Strategies;

import Model.Agent.Agent;
import Model.Agent.AgentAction;
import Model.Game.Maze;
import Model.Game.PacmanGame;

import java.util.ArrayList;
import java.util.Random;

// ------- In this strategy pacman and the ghosts will be moving in a random way -------------- //
public class RandomStrategy extends MouvementStrategy {

    public RandomStrategy(PacmanGame game) {
        super(game);
    }

    /**
     * @return AgentAction A random valid action.
     */
    @Override
    public AgentAction getAction(PacmanGame pacmanGame, Agent agent) {
        if (agent == null || pacmanGame.getMaze() == null) {
            throw new IllegalArgumentException("Agent or Maze cannot be null.");
        }

        ArrayList<AgentAction> actions = getPossibleActions(agent);
        return actions.isEmpty() ? new AgentAction(Maze.STOP) : actions.get(new Random().nextInt(actions.size()));
    }
}
