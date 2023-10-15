package Model.Agent;

public abstract class Agent {
    protected PositionAgent position;
    protected boolean isAlive;
    public Agent(PositionAgent position) {
        this.position = position;
        this.isAlive = true;
    }
    public void setPosition(PositionAgent position) {
        this.position = position;
    }
}
