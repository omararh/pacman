package Controller;
import Model.SimpleGame;
public class GameController {
    public SimpleGame game;
    public GameController(SimpleGame game) {
        this.game = game;
    }
    public void step(){
        this.game.step();
    }
    public void restart(){
        this.game.init();
    }
    public void play() {
        this.game.launch();
    }
    public void pause() {
        this.game.pause();
    }
}
