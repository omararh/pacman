package Model.Agent;

public class Agent {
    protected PositionAgent position;
    protected boolean isAlive;
    protected PositionAgent initialPosition;


    public Agent(PositionAgent position) {
        this.position = position;
        this.isAlive = true;
        this.initialPosition = position;
    }
    public void setPosition(PositionAgent position) {
        this.position = position;
    }

    public PositionAgent getPosition() {
        return position;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public PositionAgent getInitialPosition() {
        return initialPosition;
    }
}
