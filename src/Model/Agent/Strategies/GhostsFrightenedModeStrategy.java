package Model.Strategies;

import Model.Agent.Agent;
import Model.Agent.AgentAction;
import Model.Agent.PositionAgent;
import Model.Agent.TypeOfAgent;
import Model.Game.Maze;
import Model.Game.PacmanGame;

import java.util.ArrayList;

public class GhostsFrightenedModeStrategy extends MouvementStrategy {

    public GhostsFrightenedModeStrategy(PacmanGame game) {
        super(game);
    }

    @Override
    public AgentAction getAction(PacmanGame pacmanGame, Agent ghost) {
        if (ghost.getAgentType() != TypeOfAgent.GHOST) {
            throw new IllegalArgumentException("The Agent should be a ghost.");
        }

        PositionAgent pacmanPosition = pacmanGame.pacman.getPosition();
        ArrayList<AgentAction> possibleActions = getPossibleActions(ghost, pacmanGame);
        return chooseWorstAction(ghost, pacmanPosition, possibleActions);
    }

    private ArrayList<AgentAction> getPossibleActions(Agent ghost, PacmanGame pacmanGame) {
        ArrayList<AgentAction> possibleActions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            AgentAction action = new AgentAction(i);
            if (pacmanGame.isLegalMove(ghost, action)) {
                possibleActions.add(action);
            }
        }
        return possibleActions;
    }

    private AgentAction chooseWorstAction(Agent ghost, PositionAgent pacmanPosition, ArrayList<AgentAction> possibleActions) {
        if (possibleActions.isEmpty()) {
            return new AgentAction(Maze.STOP);
        }

        int ghostX = ghost.getPosition().getX();
        int ghostY = ghost.getPosition().getY();
        int pacmanX = pacmanPosition.getX();
        int pacmanY = pacmanPosition.getY();

        AgentAction worstAction = possibleActions.get(0);
        double maxDistance = -1;
        for (AgentAction action : possibleActions) {
            int newX = ghostX + action.get_vx();
            int newY = ghostY + action.get_vy();
            double distance = Math.sqrt(Math.pow(newX - pacmanX, 2) + Math.pow(newY - pacmanY, 2));
            if (distance > maxDistance) {
                maxDistance = distance;
                worstAction = action;
            }
        }

        return worstAction;
    }
}
