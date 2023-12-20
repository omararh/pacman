    package Model.Agent;

    import java.util.ArrayList;
    import java.util.List;

    public class AgentFactory {

        public static Agent createPacman(PositionAgent position) {
            return new Agent(position, TypeOfAgent.PACMAN);
        }

        public static List<Agent> createGhosts(ArrayList<PositionAgent> positions) {
            List<Agent> ghosts = new ArrayList<>();

            for(PositionAgent position : positions) {
                ghosts.add(new Agent(position, TypeOfAgent.GHOST));
            }

            return ghosts;
        }
    }
