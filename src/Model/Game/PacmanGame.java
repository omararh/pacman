package Model.Game;
import Model.Agent.*;
import Model.Strategies.SimpleGhostStrategy;
import Model.Strategies.SimplePacmanStrategy;

import java.util.List;

public class PacmanGame extends Game {
    private Maze maze;
    public List<Agent> ghosts;
    public Agent pacman;
    private int nbCapsule;
    private int nbFood;
    private int score;


    private static final int GUM_NB_POINT = 10;
    private static final int POINT_FANTOM = 50;
    private static final int CAPSULE_NB_POINT = 50;
    private static final int POINT_DEAD = -100;
    private static final int POINT_WIN = 100;

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
            this.pacman.setMouvementStrategy(new SimplePacmanStrategy(this));
            this.ghosts = AgentFactory.createGhosts(this.maze.getGhosts_start());
            this.maze.resetFoodsAndCapsules();

            // In case we restart, make pacman alive and set the agents position to their initial position
            this.pacman.setAlive(true);
            this.pacman.setPosition(this.pacman.getInitialPosition());
            for(Agent ghost : ghosts) {
                ghost.setPosition(ghost.getInitialPosition());
                // change the ghosts strategy to simple
                ghost.setMouvementStrategy(new SimpleGhostStrategy(this));
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
        return true;
    }

    /**
     * Checks if the move for a given agent is legal.
     * @param agent The agent making the move.
     * @param action The action being attempted.
     * @return boolean True if the move is legal, false otherwise.
     */
    public boolean isLegalMove(Agent agent, AgentAction action) {
        if(!agent.isAlive()){
            return false;
        }
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


        if(agent.getAgentType() == TypeOfAgent.PACMAN) {
            if(maze.isFood(dirX, dirY)){
                score+=GUM_NB_POINT;
                maze.setFood(dirX, dirY, false);
                nbFood--;
            }
            if(maze.isCapsule(dirX, dirY)){
                score+=CAPSULE_NB_POINT;
                maze.setCapsule(dirX, dirY, false);
                nbCapsule--;
                // setting the strategies for the ghosts to the FrightenedModeStrategy

            }
            System.out.println("\u001B[32m" + "Score : " + this.score + "\u001B[0m");
            System.out.println("\u001B[33m" + "pacman position  : " + pacman.getPosition() + "\u001B[0m");
        }
    }

    @Override
    protected void takeTurn() {
        AgentAction action = pacman.decideNextAction(this);
        System.out.println("\u001B[33m" + "randomly chosen direction for pacman : " + action.get_direction() + "\u001B[0m");
        moveAgent(pacman, action);
        moveGhosts();
    }

    private void moveGhosts() {
        for(Agent ghost : this.ghosts) {
            AgentAction ghostAction = ghost.decideNextAction(this);
            moveAgent(ghost, ghostAction);
        }
    }

    public void setPacmanActionByKeyBoard(AgentAction action) {
        moveAgent(pacman, action);
        moveGhosts();
        setChanged();
        notifyObservers(turn);
    }
}
