package Model.Game;
import Vue.ViewCommand;
import java.util.Observable;

public abstract class Game extends Observable implements Runnable {
    protected int turn;
    protected int maxTurn;
    protected boolean isRunning = false;
    protected Thread thread;
    protected long nbTurnsBySecond;
    public int getTurn() {
        return turn;
    }
    public Game(int maxTurn) {
        this.maxTurn = maxTurn;
        this.nbTurnsBySecond = 1000/ ViewCommand.slider_init;
        this.turn = 0;
    }
    public void setTurnsBySecond(int nbTurns) {
        this.nbTurnsBySecond = nbTurns;
    }
    protected abstract void initializeGame();
    public void init(){
        turn = 0;
        this.isRunning = false;
        this.initializeGame();
        setChanged();
        notifyObservers(turn);
    }
    protected abstract void takeTurn();
    protected abstract boolean gameContinue();
    protected void gameOver(){
        this.isRunning = false;
    }
     /*
     * when it adds a turn the observable should notify all the observers
     */
    public void step() {
        boolean isFinished = this.gameContinue();
        if(this.turn < this.maxTurn && isFinished){
            turn++;
            setChanged();
            notifyObservers(turn);
            this.takeTurn();
            return;
        }

        this.isRunning = false;
        this.gameOver();
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
