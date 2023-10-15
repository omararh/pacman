package Model.Agent;

public class PacmanAgent extends Agent{
    public int nbPoints;
    public PacmanAgent(PositionAgent position, int nbPoints) {
        super(position);
        this.nbPoints = nbPoints;
    }
}
