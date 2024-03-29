package Controller;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class KeyboardEventListener {
    JFrame window;
    ControllerPacmanGame pacmanGameController;

    public KeyboardEventListener(JFrame window, ControllerPacmanGame pacmanGameController) {
        this.window = window;
        this.pacmanGameController = pacmanGameController;

        window.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {
                pacmanGameController.handleKeyboardMovement(e.getKeyChar(), true, null);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pacmanGameController.handleKeyboardMovement(e.getKeyChar(), false, null);
            }
        });
    }
}