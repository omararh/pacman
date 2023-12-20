package Model.Agent;

import Model.Game.PacmanGame;
import Model.Strategies.MouvementStrategy;

public class Agent {
    private PositionAgent position;
    private boolean isAlive;
    private PositionAgent initialPosition;
    private TypeOfAgent agentType;
    private MouvementStrategy mouvementStrategy;

    public Agent(PositionAgent position, TypeOfAgent agentType) {
        this.position = position;
        this.isAlive = true;
        this.initialPosition = position;
        this.agentType = agentType;
    }
    public void setPosition(PositionAgent position) {
        this.position = position;
    }

    public PositionAgent getPosition() {
        return position;
    }

    public TypeOfAgent getAgentType() {
        return agentType;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public PositionAgent getInitialPosition() {
        return initialPosition;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setMouvementStrategy(MouvementStrategy mouvementStrategy) {
        this.mouvementStrategy = mouvementStrategy;
    }

    public AgentAction decideNextAction(PacmanGame pacmanGame) {
        return this.mouvementStrategy.getAction(pacmanGame, this);
    }
}
