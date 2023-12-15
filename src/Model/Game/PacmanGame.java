package Model.Game;
import Model.Agent.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PacmanGame extends Game {
    private Maze maze;
    public List<GhostAgent> ghosts;
    public PacmanAgent pacman;
    private int nbCapsule;
    private int nbFood;
    private int score;
    private static final int POINT_GUM = 10;
    private static final int POINT_FANTOM = 50;
    private static final int POINT_CAPSULE = 50;
    private static final int POINT_DEAD = -100;
    private static final int POINT_WIN = 100;

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
    protected void initializeGame()  {
        //if (this.pacman == null) {
            this.pacman = AgentFactory.createPacman(this.maze.getPacman_start().get(0));
            this.ghosts = AgentFactory.createGhosts(this.maze.getGhosts_start());
        //}else {
            this.maze.resetFoodsAndCapsules();
        //}
        // in case if we restart :
        // we have to make the ghosts alive again and pacman also +
        for(Agent ghost : this.ghosts){
            ghost.setAlive(true);
        }
        this.pacman.setAlive(true);

        // set there position into the initial positions
        this.pacman.setPosition(this.pacman.getInitialPosition());
        for(Agent ghost : ghosts) {
            ghost.setPosition(ghost.getInitialPosition());
        }

        this.score = 0;
        this.nbFood = countFoods(this.maze);
        this.nbCapsule = countCapsules(this.maze);

        System.out.println("\u001B[32m" + "pacman Started position " +
                pacman.getPosition() + "\u001B[0m");
    }
    public int countFoods(Maze maze) {
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

    public int countCapsules(Maze maze) {
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
    /*
     * check if the agent can make the move
     * @param Agent
     * @param AgentAction
     * @return boolean
     */
    private boolean isLegalMove(Agent agent, AgentAction action) {
        if(!agent.isAlive()){
            return false;
        }
        int dirX = agent.getPosition().getX() + action.get_vx();
        int dirY = agent.getPosition().getY() + action.get_vy();

        return !maze.isWall(dirX, dirY);
    }
    /*
     * update the position
     * @param Agent
     * @param AgentAction
     */
    private void moveAgent(Agent agent, AgentAction action) {
        if(! isLegalMove(agent, action)) {
            System.out.println("\u001B[31m" + "Illegal move !!!" + "\u001B[0m");
            return;
        }

        int dirX = agent.getPosition().getX() + action.get_vx();
        int dirY = agent.getPosition().getY() + action.get_vy();


        agent.setPosition(new PositionAgent(dirX, dirY, action.get_direction()));


        if(agent instanceof PacmanAgent) {
            if(maze.isFood(dirX, dirY)){
                score+=POINT_GUM;
                nbFood--;
            }
            if(maze.isCapsule(dirX, dirY)){
                score+=POINT_CAPSULE;
                nbCapsule--;
            }
            System.out.println("\u001B[32m" + "Score : " + this.score + "\u001B[0m");
            System.out.println("\u001B[33m" + "pacman position  : " + pacman.getPosition() + "\u001B[0m");
        }
    }
    @Override
    protected void takeTurn() {
        AgentAction action = getAgentRandomAction(pacman, maze);
        System.out.println("\u001B[33m" + "randomly chosen direction : " + action.get_direction() + "\u001B[0m");
        moveAgent(pacman, action);

        moveGhosts();
    }
    private void moveGhosts() {
        for(Agent ghost : this.ghosts) {
            AgentAction ghostAction = getAgentRandomAction(ghost, maze);
            moveAgent(ghost, ghostAction);
        }

    }
    public void setPacmanAction(AgentAction action) {
        moveAgent(pacman, action);
        moveGhosts();
        setChanged();
        notifyObservers(turn);
    }
     /*
     * return a random valid action
     * @param agent
     * @param maze
     * @return AgentPosition
     */
     private AgentAction getAgentRandomAction(Agent agent, Maze maze) {
         Random random = new Random();
         ArrayList<AgentAction> actions = new ArrayList<>();

         if (!maze.isWall(agent.getPosition().getX(), agent.getPosition().getY() - 1)) {
             actions.add(new AgentAction(Maze.NORTH));
         }
         if (!maze.isWall(agent.getPosition().getX(), agent.getPosition().getY() + 1)) {
             actions.add(new AgentAction(Maze.SOUTH));
         }
         if (!maze.isWall(agent.getPosition().getX() + 1, agent.getPosition().getY())) {
             actions.add(new AgentAction(Maze.EAST));
         }
         if (!maze.isWall(agent.getPosition().getX() - 1, agent.getPosition().getY())) {
             actions.add(new AgentAction(Maze.WEST));
         }

         if (actions.size() > 0) {
             return actions.get(random.nextInt(actions.size()));
         } else {
             return new AgentAction(Maze.STOP);
         }
     }
}
