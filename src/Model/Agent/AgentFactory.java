    package Model.Agent;

    import java.util.ArrayList;
    import java.util.List;

    public class AgentFactory {
        public static PacmanAgent createPacman(PositionAgent position) {
            return new PacmanAgent(position, 0);
        }
        public static List<GhostAgent> createGhosts(ArrayList<PositionAgent> positions) {
            List<GhostAgent> ghosts = new ArrayList<>();

            for(PositionAgent position : positions) {
                ghosts.add(new GhostAgent(position));
            }

            return ghosts;
        }
    }
