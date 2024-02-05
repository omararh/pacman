package Model.Game;
import Model.Agent.*;
import Model.Agent.Strategies.*;

import java.util.List;

public class PacmanGame extends Game {
    private Maze maze;
    public List<Agent> ghosts;
    public Agent pacman;
    private int nbCapsule;
    private int nbFood;
    private int score;
    private int frightenedModeStartTurn = 0;


    private static final int GUM_NB_POINT = 10;
    private static final int CAPSULE_NB_POINT = 50;
    private static final int POINT_GHOST = 50;

    /**
     * Constructor for PacmanGame.
     * @param mazeFile String representing the file path of the maze.
     * @param maxTurn Maximum number of turns for the game.
     * @throws Exception if the maze file cannot be read or is invalid.
     */
    public PacmanGame(String mazeFile, int maxTurn) throws Exception {
        super(maxTurn);
        this.maze = new Maze(mazeFile);


        initializeGame();
    }

    public int getScore() {
        return score;
    }

    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    protected void initializeGame() {
        try {
            this.pacman = AgentFactory.createPacman(this.maze.getPacman_start().get(0));
            this.pacman.setMouvementStrategy(new PacmanToFoodStrategy(this));
            this.ghosts = AgentFactory.createGhosts(this.maze.getGhosts_start());
            this.maze.resetFoodsAndCapsules();

            // In case we restart, make pacman alive and set the agents position to their initial position
            this.pacman.setAgentState(AgentState.ALIVE);
            this.pacman.setPosition(this.pacman.getInitialPosition());
            for(Agent ghost : ghosts) {
                ghost.setPosition(ghost.getInitialPosition());
                ghost.setMouvementStrategy(new RandomStrategy(this));
            }

            this.score = 0;
            this.nbFood = getNbFood(this.maze);
            this.nbCapsule = getNbCapsule(this.maze);

            System.out.println("\u001B[32m" + "Pacman started position " +
                    pacman.getPosition() + "\u001B[0m");
        } catch (Exception e) {
            System.out.println("Error initializing game: " + e.getMessage());
        }
    }

    /**
     * Calculates the number of food in the maze.
     * @param maze The Maze object.
     * @return int Number of food.
     */
    public int getNbFood(Maze maze) {
        int nb = 0;
        for(int i =0; i < maze.getSizeX(); i++) {
            for(int j =0; j < maze.getSizeY(); j++) {
                if(maze.isFood(i, j)) {
                    nb += 1;
                }
            }
        }
        return nb;
    }

    /**
     * Calculates the number of capsules in the maze.
     * @param maze The Maze object.
     * @return int Number of capsules.
     */
    public int getNbCapsule(Maze maze) {
        int nb = 0;
        for(int i =0; i < maze.getSizeX(); i++) {
            for(int j =0; j < maze.getSizeY(); j++) {
                if(maze.isCapsule(i, j)) {
                    nb += 1;
                }
            }
        }
        return nb;
    }

    @Override
    protected boolean gameContinue() {
        return ((nbFood > 0 || nbCapsule > 0) && !(pacman.getAgentState() == AgentState.DEAD));
    }

    @Override
    protected void gameOver(){
        if(nbFood == 0 && nbCapsule == 0 && !(pacman.getAgentState() == AgentState.DEAD)){
            this.currentState = GameState.VICTORY;
        }
    }

    /**
     * Checks if the move for a given agent is legal.
     * @param agent The agent making the move.
     * @param action The action being attempted.
     * @return boolean True if the move is legal, false otherwise.
     */
    public boolean isLegalMove(Agent agent, AgentAction action) {
        int dirX = agent.getPosition().getX() + action.get_vx();
        int dirY = agent.getPosition().getY() + action.get_vy();

        return !maze.isWall(dirX, dirY);
    }

    /**
     * Updates the position of an agent based on the given action.
     * @param agent The agent who will move.
     * @param action The action to perform for this move.
     */
    private void moveAgent(Agent agent, AgentAction action) {
        if(! isLegalMove(agent, action)) {
            System.out.println("\u001B[31m" + "Illegal move !!!" + "\u001B[0m");
            return;
        }

        int dirX = agent.getPosition().getX() + action.get_vx();
        int dirY = agent.getPosition().getY() + action.get_vy();


        agent.setPosition(new PositionAgent(dirX, dirY, action.get_direction()));

        if (agent.getAgentType() == TypeOfAgent.PACMAN ) {
            if(maze.isFood(dirX, dirY)){
                score+=GUM_NB_POINT;
                maze.setFood(dirX, dirY, false);
                nbFood--;
            }
            if(maze.isCapsule(dirX, dirY)) {
                score += CAPSULE_NB_POINT;
                maze.setCapsule(dirX, dirY, false);
                nbCapsule--;
                frightenedModeStartTurn = this.turn;

                for (Agent ghost : ghosts) {
                    ghost.setMouvementStrategy(new GhostsFrightenedModeStrategy(this));
                    ghost.setAgentState(AgentState.SCARED);
                }
                System.out.println("\u001B[32m" + "Score : " + this.score + "\u001B[0m");
                System.out.println("\u001B[33m" + "pacman position  : " + pacman.getPosition() + "\u001B[0m");
            }
        }
    }

    @Override
    protected void takeTurn() {
        AgentAction action = pacman.decideNextAction(this);
        System.out.println("\u001B[33m" + "chosen direction for pacman : " + action.get_direction() + "\u001B[0m");
        moveAgent(pacman, action);
        for(Agent ghost : this.ghosts) {
            AgentAction ghostAction = ghost.decideNextAction(this);
            moveAgent(ghost, ghostAction);
        }

        if (frightenedModeStartTurn != 0 && this.turn - frightenedModeStartTurn >= 20) {
            resetGhostStrategies();
            frightenedModeStartTurn = 0;
        }

        if(isPacmanCollidingWithGhost()){

            if(this.ghosts.get(0).getAgentState() == AgentState.SCARED) {
                for (Agent ghost : ghosts) {
                    if (ghost.getPosition().getX() == pacman.getPosition().getX() && ghost.getPosition().getY() == pacman.getPosition().getY()) {
                        ghost.setPosition(ghost.getInitialPosition());
                    }
                }

                score+=POINT_GHOST;
                return;
            }
            this.currentState = GameState.GAME_OVER;
            this.pacman.setAgentState(AgentState.DEAD);
            System.out.println("pacman State --> " + this.pacman.getAgentState());
        }
    }

    private void resetGhostStrategies() {
        for (Agent ghost : ghosts) {
            ghost.setMouvementStrategy(new GhostToPacmanStrategy(this));
            ghost.setAgentState(AgentState.ALIVE);
        }
    }

    private boolean isPacmanCollidingWithGhost() {
        for (Agent ghost : ghosts) {
            if (ghost.getPosition().getX() == pacman.getPosition().getX() && ghost.getPosition().getY() == pacman.getPosition().getY()) {
                return true;
            }
        }
        return false;
    }
}
