package Model.Agent.Strategies;

import Model.Agent.Agent;
import Model.Agent.AgentAction;
import Model.Agent.PositionAgent;
import Model.Agent.TypeOfAgent;
import Model.Game.Maze;
import Model.Game.PacmanGame;

import java.util.ArrayList;

// ------- In this strategy pacman will be trying to search for the food/capsule -------------- //
public class PacmanToFoodStrategy extends MouvementStrategy {

    private PositionAgent lastPosition = null;

    public PacmanToFoodStrategy(PacmanGame game) {
        super(game);
    }

    /**
     * Determines the next action for Pac-Man to move towards the nearest food or capsule.
     *
     * @param pacmanGame The current state of the Pac-Man game.
     * @param pacman     The Pac-Man agent for which to determine the action.
     * @return The decided AgentAction for Pac-Man.
     * @throws IllegalArgumentException if the agent is not Pac-Man.
     */
    @Override
    public AgentAction getAction(PacmanGame pacmanGame, Agent pacman) throws IllegalArgumentException {
        if (pacman.getAgentType() != TypeOfAgent.PACMAN) {
            throw new IllegalArgumentException("The Agent should be Pac-Man.");
        }

        Maze maze = pacmanGame.getMaze();
        ArrayList<AgentAction> possibleActions = getPossibleActions(pacman);
        return chooseBestActionToFoodOrCapsule(maze, pacman, possibleActions);
    }

    /**
     * Chooses the best action for Pac-Man to take, aiming to move towards the nearest food or capsule.
     *
     * @param maze            The maze of the game.
     * @param pacman          The Pac-Man agent.
     * @param possibleActions List of possible actions for Pac-Man.
     * @return The optimal AgentAction to take.
     */
    private AgentAction chooseBestActionToFoodOrCapsule(Maze maze, Agent pacman, ArrayList<AgentAction> possibleActions) {
        if (possibleActions.isEmpty()) {
            return new AgentAction(Maze.STOP);
        }

        AgentAction bestAction = null;
        double minDistance = Double.MAX_VALUE;
        PositionAgent currentPosition = pacman.getPosition();

        for (AgentAction action : possibleActions) {
            PositionAgent newPosition = generateNewPosition(currentPosition, action);

            if (isReturningToLastPosition(newPosition) && possibleActions.size() > 1) {
                continue;
            }

            double distance = calculateDistanceToNearestFoodOrCapsule(maze, newPosition.getX(), newPosition.getY());
            if (distance < minDistance) {
                minDistance = distance;
                bestAction = action;
            }
        }

        setLastPosition(currentPosition);
        return bestAction != null ? bestAction : new AgentAction(Maze.STOP);
    }

    /**
     * Generates a new position based on the current position and an action.
     *
     * @param currentPosition The current position of Pac-Man.
     * @param action          The action to be taken.
     * @return The new position after taking the action.
     */
    private PositionAgent generateNewPosition(PositionAgent currentPosition, AgentAction action) {
        return new PositionAgent(
                currentPosition.getX() + action.get_vx(),
                currentPosition.getY() + action.get_vy(),
                action.get_direction()
        );
    }

    /**
     * Checks if the new position is the same as the last position.
     *
     * @param newPosition The new position to check.
     * @return true if the new position is the same as the last position; false otherwise.
     */
    private boolean isReturningToLastPosition(PositionAgent newPosition) {
        return lastPosition != null && newPosition.equals(lastPosition);
    }

    /**
     * Updates the last known position Pac-Man.
     *
     * @param currentPosition The current position of Pac-Man.
     */
    private void setLastPosition(PositionAgent currentPosition) {
        lastPosition = currentPosition;
    }

    /**
     * Calculates the distance from a given position to the nearest food or capsule in the maze.
     *
     * @param maze The maze of the game.
     * @param x    The x-coordinate of the position.
     * @param y    The y-coordinate of the position.
     * @return The minimum distance to the nearest food or capsule.
     */
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
