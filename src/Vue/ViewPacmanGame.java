package Vue;
import Model.Game.PacmanGame;
import javax.swing.JFrame;

public class ViewPacmanGame extends JFrame {

    public ViewPacmanGame(PacmanGame pacmanGame){
        super("Pacman Game");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700, 700);
        PanelPacmanGame panelPacmanGame = new PanelPacmanGame(pacmanGame.getMaze());
        this.add(panelPacmanGame);
        this.setVisible(true);
    }
}
