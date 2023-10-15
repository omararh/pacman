package Model.Game;
import Model.Agent.AgentFactory;
import Model.Agent.GhostAgent;
import Model.Agent.PacmanAgent;

import java.util.List;
public class PacmanGame extends Game {
    private Maze maze;
    public List<GhostAgent> ghosts;
    public PacmanAgent pacman;
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

    }
    @Override
    protected void takeTurn() {
        System.out.println("Tour " + turn + " du jeu en cours " + nbTurnsBySecond);
    }
    @Override
    protected boolean gameContinue() {
        return true;
    }
}
