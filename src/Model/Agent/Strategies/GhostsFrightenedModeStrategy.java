package Model.Agent.Strategies;

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

        PositionAgent nearestPacmanPosition = getNearestPacmanPosition(pacmanGame, ghost);
        ArrayList<AgentAction> possibleActions = getPossibleActions(ghost, pacmanGame);
        return chooseWorstAction(ghost, nearestPacmanPosition, possibleActions);
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

        return nearestPacmanPosition; // S'assurer qu'il y a au moins un Pac-Man dans la liste
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
