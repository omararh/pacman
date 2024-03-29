package Model.Agent;

import java.util.ArrayList;
import java.util.List;

public class AgentFactory {

    public static List<Agent> createPacmans(List<PositionAgent> positions) {
        List<Agent> pacmans = new ArrayList<>();
        for (PositionAgent position : positions) {
            pacmans.add(new Agent(position, TypeOfAgent.PACMAN));
        }
        return pacmans;
    }



    public static List<Agent> createGhosts(ArrayList<PositionAgent> positions) {
        List<Agent> ghosts = new ArrayList<>();

        for(PositionAgent position : positions) {
            ghosts.add(new Agent(position, TypeOfAgent.GHOST));
        }

        return ghosts;
    }
}
