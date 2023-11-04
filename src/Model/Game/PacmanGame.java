package Model.Game;
import Model.Agent.*;

import java.util.List;
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

    public PacmanGame(Maze maze, int maxTurn){
        super(maxTurn);
        this.maze = maze;
    }
    public Maze getMaze() {
        return maze;
    }
    public void setMaze(Maze maze) {
        this.maze = maze;
    }
    @Override
    protected void initializeGame() {
        //pacman initialization
        pacman = AgentFactory.createPacman(this.maze.getPacman_start().get(0));
        //ghosts initialization
        ghosts = AgentFactory.createGhosts(this.maze.getGhosts_start());
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
    public boolean isLegalMove(Agent agent, AgentAction action) {
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
    public void moveAgent(Agent agent, AgentAction action) {

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
        }
    }
    @Override
    protected void takeTurn() {

    }







}
