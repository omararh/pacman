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
        initializeGame();
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

        this.score = 0;
        System.out.println("\u001B[32m" + "pacman Started position " + pacman.getPosition() + "\u001B[0m");
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
        AgentAction action = new AgentAction(3);
        moveAgent(pacman, action);
    }
    public void setPacmanAction(AgentAction action) {
        moveAgent(pacman, action);
    }
}
