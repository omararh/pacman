package Model;

import Vue.ViewCommand;

public class SimpleGame extends Game {

    public  SimpleGame(int maxTurn, long time) {
        super(maxTurn, time);
    }

    @Override
    protected void initializeGame() {
        System.out.println("Game is initialized ! ");
    }

    @Override
    protected void takeTurn() {
        System.out.println("Tour " + turn + " du jeu en cours " + time);
    }

    @Override
    protected boolean gameContinue() {
        return true;
    }

    @Override
    protected void gameOver() {
        System.out.println("GameOver !");
    }
}
