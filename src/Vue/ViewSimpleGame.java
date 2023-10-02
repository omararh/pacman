package Vue;
import Model.Game;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

public class ViewSimpleGame implements Observer {
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    public Game game;

    public ViewSimpleGame(Game game) {
        this.game = game;
        this.game.addObserver(this);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Game");
        frame.setSize(new Dimension(700, 700));

        panel = new JPanel();
        label = new JLabel("current turn " + game.getTurn());

        panel.add(label);
        frame.add(panel);
        frame.setVisible(true);
    }

    private void setTurn() {
        label.setText("current turn " + game.getTurn());
    }


    @Override
    public void update(Observable o, Object arg) {
        setTurn();
    }
}
