package Model.Agent;

import Model.Game.PacmanGame;
import Model.Agent.Strategies.MouvementStrategy;

public class Agent {
    private PositionAgent position;
    private PositionAgent initialPosition;
    private TypeOfAgent agentType;
    private MouvementStrategy mouvementStrategy;
    private AgentState agentState;
    private int pacmanId;

    private static int cp = 1;

    public Agent(PositionAgent position, TypeOfAgent agentType) {
        this.position = position;
        this.initialPosition = position;
        this.agentType = agentType;
        this.agentState = AgentState.ALIVE;

        if(agentType == TypeOfAgent.PACMAN)
            this.pacmanId = cp++;
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

    public AgentState getAgentState() {
        return this.agentState;
    }

    public PositionAgent getInitialPosition() {
        return initialPosition;
    }

    public MouvementStrategy getMouvementStrategy() {
        return mouvementStrategy;
    }

    public int getPacmanId() {
        return this.pacmanId;
    }

    public void setMouvementStrategy(MouvementStrategy mouvementStrategy) {
        this.mouvementStrategy = mouvementStrategy;
    }

    public void setAgentState(AgentState state) {
        this.agentState = state;
    }

    public AgentAction decideNextAction(PacmanGame pacmanGame) {
        return this.mouvementStrategy.getAction(pacmanGame, this);
    }
}
