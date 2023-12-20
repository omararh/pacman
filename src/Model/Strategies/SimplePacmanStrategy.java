package Model.Strategies;

import Model.Agent.Agent;
import Model.Agent.AgentAction;
import Model.Agent.TypeOfAgent;
import Model.Game.Maze;
import Model.Game.PacmanGame;

import java.util.ArrayList;

public class SimplePacmanStrategy extends MouvementStrategy {

    public SimplePacmanStrategy(PacmanGame game) {
        super(game);
    }

    /**
     * Determines the next action for Pac-Man to move towards the nearest food or capsule.
     *
     * @param pacmanGame The current state of the Pac-Man game.
     * @param pacman The Pac-Man agent for which to determine the action.
     * @return The decided AgentAction for Pac-Man.
     */
    @Override
    public AgentAction getAction(PacmanGame pacmanGame, Agent pacman) {
        if (pacman.getAgentType() != TypeOfAgent.PACMAN) {
            throw new IllegalArgumentException("The Agent should be pac-man.");
        }

        Maze maze = pacmanGame.getMaze();
        ArrayList<AgentAction> possibleActions = getPossibleActions(pacman);
        return chooseBestActionToFoodOrCapsule(maze, pacman, possibleActions);
    }

    private AgentAction chooseBestActionToFoodOrCapsule(Maze maze, Agent pacman, ArrayList<AgentAction> possibleActions) {
        if (possibleActions.isEmpty()) {
            return new AgentAction(Maze.STOP);
        }

        int currentDirection = pacman.getPosition().getDir();
        AgentAction bestAction = null;
        double minDistance = Double.MAX_VALUE;
        boolean sameDirectionFound = false;

        for (AgentAction action : possibleActions) {
            int newX = pacman.getPosition().getX() + action.get_vx();
            int newY = pacman.getPosition().getY() + action.get_vy();
            double distance = calculateDistanceToNearestFoodOrCapsule(maze, newX, newY);

            if (distance < minDistance || (!sameDirectionFound && action.get_direction() == currentDirection)) {
                minDistance = distance;
                bestAction = action;
                sameDirectionFound = action.get_direction() == currentDirection;
            }
        }

        return bestAction;
    }

    private double calculateDistanceToNearestFoodOrCapsule(Maze maze, int x, int y) {
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < maze.getSizeX(); i++) {
            for (int j = 0; j < maze.getSizeY(); j++) {
                if (maze.isFood(i, j) || maze.isCapsule(i, j)) {
                    double distance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                    if (distance < minDistance) {
                        minDistance = distance;
                    }
                }
            }
        }

        return minDistance;
    }
}
