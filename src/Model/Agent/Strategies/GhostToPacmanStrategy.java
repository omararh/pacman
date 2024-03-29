package Model.Agent.Strategies;

import Model.Agent.Agent;
import Model.Agent.AgentAction;
import Model.Agent.PositionAgent;
import Model.Agent.TypeOfAgent;
import Model.Game.Maze;
import Model.Game.PacmanGame;

import java.util.ArrayList;

// ------- In this strategy the ghost will be trying to catch pacman -------------- //
public class GhostToPacmanStrategy extends MouvementStrategy {

    public GhostToPacmanStrategy(PacmanGame game) {
        super(game);
    }

    /**
     * Determines the next action for a ghost based on its strategy to move towards Pac-Man.
     *
     * @param pacmanGame The current state of the Pac-Man game.
     * @param ghost The ghost agent for which to determine the action.
     * @return The decided AgentAction for the ghost.
     */
    @Override
    public AgentAction getAction(PacmanGame pacmanGame, Agent ghost) {
        if (ghost.getAgentType() != TypeOfAgent.GHOST) {
            throw new IllegalArgumentException("The Agent should be a ghost.");
        }

        PositionAgent nearestPacmanPosition = getNearestPacmanPosition(pacmanGame, ghost);
        ArrayList<AgentAction> possibleActions = getPossibleActions(ghost);
        return chooseBestAction(ghost, nearestPacmanPosition, possibleActions);
    }

    private PositionAgent getNearestPacmanPosition(PacmanGame pacmanGame, Agent ghost) {
        double minDistance = Double.MAX_VALUE;
        PositionAgent nearestPacmanPosition = null;

        for (Agent pacman : pacmanGame.pacmans) {
            double distance = Math.sqrt(Math.pow(pacman.getPosition().getX() - ghost.getPosition().getX(), 2)
                    + Math.pow(pacman.getPosition().getY() - ghost.getPosition().getY(), 2));
            if (distance < minDistance) {
                minDistance = distance;
                nearestPacmanPosition = pacman.getPosition();
            }
        }

        return nearestPacmanPosition;
    }


    /**
     * Chooses the best action that minimizes the distance to Pac-Man among the valid actions.
     *
     * @param ghost The ghost agent for which to choose the action.
     * @param pacmanPosition The current position of Pac-Man.
     * @param possibleActions List of possible actions for the ghost.
     * @return The best AgentAction for the ghost.
     */
    private AgentAction chooseBestAction(Agent ghost, PositionAgent pacmanPosition, ArrayList<AgentAction> possibleActions) {
        if (possibleActions.isEmpty()) {
            return new AgentAction(Maze.STOP);
        }

        int ghostX = ghost.getPosition().getX();
        int ghostY = ghost.getPosition().getY();
        int pacmanX = pacmanPosition.getX();
        int pacmanY = pacmanPosition.getY();

        AgentAction bestAction = possibleActions.get(0);
        double minDistance = Double.MAX_VALUE;
        for (AgentAction action : possibleActions) {
            int newX = ghostX + action.get_vx();
            int newY = ghostY + action.get_vy();
            double distance = Math.sqrt(Math.pow(newX - pacmanX, 2) + Math.pow(newY - pacmanY, 2));
            if (distance < minDistance) {
                minDistance = distance;
                bestAction = action;
            }
        }

        return bestAction;
    }
}
