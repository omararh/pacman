package Model.Game;
import Vue.ViewCommand;

import java.io.Serializable;
import java.util.Observable;

public abstract class Game extends Observable implements Runnable, Serializable {

    protected int turn;
    protected int maxTurn;
    protected boolean isRunning = false;
    protected Thread thread;
    protected long nbTurnsBySecond;
    protected GameState currentState;

    public Game(int maxTurn) {
        this.maxTurn = maxTurn;
        this.nbTurnsBySecond = 1000/ ViewCommand.slider_init;
        this.turn = 0;
        this.init();
    }

    public int getTurn() {
        return turn;
    }

    public GameState getCurrentState(){
        return currentState;
    }


    public void setTurnsBySecond(int nbTurns) {
        this.nbTurnsBySecond = nbTurns;
    }

    protected abstract void initializeGame();

    public void init(){
        turn = 0;
        this.isRunning = false;
        this.initializeGame();
        this.currentState = GameState.START;
        setChanged();
        notifyObservers(turn);
    }

    protected abstract void takeTurn();

    protected abstract boolean gameContinue();

    protected abstract void gameOver();

    /*
     * when it adds a turn the observable should notify all the observers
     */
    public void step() {
        if(this.turn < this.maxTurn && this.gameContinue()){
            turn++;
            this.takeTurn();
            this.currentState = GameState.RUN;
        }else{
            this.isRunning = false;
            this.currentState = GameState.GAME_OVER;
            this.gameOver();
        }

        setChanged();
        notifyObservers(turn);
    }

    public void pause() {
        this.isRunning = false;
        setChanged();
        notifyObservers(turn);
    }

    @SuppressWarnings("BusyWait")
    public void run() {
        while (this.isRunning) {
            this.step();
            try {
                Thread.sleep(this.nbTurnsBySecond);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void launch() {
        this.isRunning = true;
        this.thread = new Thread(this);
        thread.start();
    }
}
