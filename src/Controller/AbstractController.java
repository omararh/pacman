package Controller;
import Model.Game.Game;
import Vue.ViewCommand;

public abstract class AbstractController {
    protected Game game;
    public AbstractController(Game game) {
        this.game = game;
        ViewCommand viewCommand = new ViewCommand(this, game);
    }
    public void setTurnsBySecond(int time) {
        this.game.setTurnsBySecond(1000/time);
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
