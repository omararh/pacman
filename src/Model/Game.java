package Model;

import java.util.Observable;

public abstract class Game extends Observable implements Runnable {

    protected int turn;
    protected int maxTurn;
    protected boolean isRunning = false;
    protected Thread thread;
    protected long time;
    public int getTurn() {
        return turn;
    }
    public int getMaxTurn(){
        return maxTurn;
    }
    public Game(int maxTurn, long time) {
        this.maxTurn = maxTurn;
        this.time = time;
        this.turn = 0;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    protected abstract void initializeGame();
    public void init(){
        turn = 0;
        this.isRunning = true;
        this.initializeGame();
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
                Thread.sleep(this.time);
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
