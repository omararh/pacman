package Model.Game;

import Model.Agent.*;
import Model.Agent.Strategies.*;

import java.util.List;
import java.util.ArrayList;

public class PacmanGame extends Game {
    private Maze maze;
    public List<Agent> ghosts;
    public List<Agent> pacmans; // Gérer plusieurs Pac-Man
    private int nbCapsule;
    private int nbFood;
    private int score;
    private int frightenedModeStartTurn = 0;

    private static final int GUM_NB_POINT = 10;
    private static final int CAPSULE_NB_POINT = 50;

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
            this.pacmans = AgentFactory.createPacmans(this.maze.getPacman_start());
            for (Agent pacman : pacmans) {
                pacman.setMouvementStrategy(new PacmanToFoodStrategy(this));
            }

            this.ghosts = AgentFactory.createGhosts(this.maze.getGhosts_start());
            this.maze.resetFoodsAndCapsules();

            for(Agent pacman : pacmans) {
                pacman.setAgentState(AgentState.ALIVE);
                pacman.setPosition(pacman.getInitialPosition());
            }

            for (Agent ghost : ghosts) {
                ghost.setPosition(ghost.getInitialPosition());
                ghost.setMouvementStrategy(new RandomStrategy(this));
            }

            this.score = 0;
            // Correction: appel direct aux méthodes de Maze sans argument
            this.nbFood = maze.getNbFood();
            this.nbCapsule = maze.getNbCapsule();
        } catch (Exception e) {
            System.out.println("Error initializing game: " + e.getMessage());
        }
    }


    @Override
    protected void takeTurn() {
        for (Agent pacman : pacmans) {
            AgentAction action = pacman.decideNextAction(this);
            moveAgent(pacman, action);
        }

        for (Agent ghost : ghosts) {
            AgentAction ghostAction = ghost.decideNextAction(this);
            moveAgent(ghost, ghostAction);
        }

        if (frightenedModeStartTurn != 0 && this.turn - frightenedModeStartTurn >= 20) {
            resetGhostStrategies();
            frightenedModeStartTurn = 0;
        }

        for (Agent pacman : pacmans) {
            if (isPacmanCollidingWithGhost(pacman)) {
                this.currentState = GameState.GAME_OVER;
                pacman.setAgentState(AgentState.DEAD);
                System.out.println("Pacman State --> " + pacman.getAgentState());
                break;
            }
        }
    }

    @Override
    protected boolean gameContinue() {
        boolean isAnyPacmanAlive = pacmans.stream().anyMatch(pacman -> pacman.getAgentState() != AgentState.DEAD);

        // Continue le jeu si au moins un Pac-Man est en vie et qu'il reste de la nourriture ou des capsules
        return isAnyPacmanAlive && (nbFood > 0 || nbCapsule > 0);
    }

    @Override
    protected void gameOver() {
        if (nbFood == 0 && nbCapsule == 0) {
            // Toute la nourriture et toutes les capsules ont été consommées, les joueurs ont gagné
            System.out.println("Victoire! Toute la nourriture a été consommée.");
            this.currentState = GameState.VICTORY;
        } else {
            // Tous les Pac-Man sont morts, les joueurs ont perdu
            System.out.println("Défaite. Tous les Pac-Man sont morts.");
            this.currentState = GameState.GAME_OVER;
        }
    }

    public boolean isLegalMove(Agent agent, AgentAction action) {
        int newX = agent.getPosition().getX() + action.get_vx();
        int newY = agent.getPosition().getY() + action.get_vy();

        // Vérifiez si le nouveau déplacement est dans les limites du labyrinthe
        if (newX >= 0 && newX < maze.getSizeX() && newY >= 0 && newY < maze.getSizeY()) {
            // Vérifie si le nouvel emplacement n'est pas un mur
            return !maze.isWall(newX, newY);
        }
        return false; // Le mouvement est hors des limites ou vers un mur
    }

    private void moveAgent(Agent agent, AgentAction action) {
        if (!isLegalMove(agent, action)) {
            System.out.println("\u001B[31m" + "Illegal move !!!" + "\u001B[0m");
            return;
        }

        int dirX = agent.getPosition().getX() + action.get_vx();
        int dirY = agent.getPosition().getY() + action.get_vy();

        agent.setPosition(new PositionAgent(dirX, dirY, action.get_direction()));

        if (agent.getAgentType() == TypeOfAgent.PACMAN) {
            updateGameStateUponPacmanMove(agent, dirX, dirY);
        }
    }

    private void updateGameStateUponPacmanMove(Agent pacman, int x, int y) {
        if (maze.isFood(x, y)) {
            score += GUM_NB_POINT;
            maze.setFood(x, y, false);
            nbFood--;
        }
        if (maze.isCapsule(x, y)) {
            score += CAPSULE_NB_POINT;
            maze.setCapsule(x, y, false);
            nbCapsule--;
            frightenedModeStartTurn = this.turn;
            for (Agent ghost : ghosts) {
                ghost.setMouvementStrategy(new GhostsFrightenedModeStrategy(this));
                ghost.setAgentState(AgentState.SCARED);
            }
        }
    }

    private void resetGhostStrategies() {
        for (Agent ghost : ghosts) {
            ghost.setMouvementStrategy(new RandomStrategy(this));
            ghost.setAgentState(AgentState.ALIVE);
        }
    }

    private boolean isPacmanCollidingWithGhost(Agent pacman) {
        for (Agent ghost : ghosts) {
            if (ghost.getPosition().getX() == pacman.getPosition().getX() && ghost.getPosition().getY() == pacman.getPosition().getY()) {
                return true;
            }
        }
        return false;
    }
}
