package Model.Agent;

public class GhostAgent extends Agent{
    protected PositionAgent firstPosition;
    public GhostAgent(PositionAgent position) {
        super(position);
        this.firstPosition = position;
    }
}
