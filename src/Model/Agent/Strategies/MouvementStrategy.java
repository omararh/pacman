package Model.Strategies;

import Model.Agent.Agent;
import Model.Agent.AgentAction;
import Model.Game.Maze;
import Model.Game.PacmanGame;

import java.util.ArrayList;

public abstract class MouvementStrategy
{
    protected PacmanGame game;

    public MouvementStrategy(PacmanGame game) {
        this.game = game;
    }

    public abstract AgentAction getAction(PacmanGame pacmanGame, Agent agent);

    private void addActionIfValid(Agent agent, ArrayList<AgentAction> actions, int direction) {
        AgentAction action = new AgentAction(direction);
        if (this.game.isLegalMove(agent, action)) {
            actions.add(action);
        }
    }

    protected ArrayList<AgentAction> getPossibleActions(Agent agent) {

        ArrayList<AgentAction> possibleActions = new ArrayList<>();
        addActionIfValid(agent, possibleActions, Maze.NORTH);
        addActionIfValid(agent, possibleActions, Maze.SOUTH);
        addActionIfValid(agent, possibleActions, Maze.EAST);
        addActionIfValid(agent, possibleActions, Maze.WEST);

        return possibleActions;
    }
}
