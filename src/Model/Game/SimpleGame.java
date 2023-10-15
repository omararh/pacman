package Model.Game;

import Model.Game.Game;

public class SimpleGame extends Game {

    public  SimpleGame(int maxTurn) {
        super(maxTurn);
    }
    @Override
    protected void initializeGame() {
        System.out.println("Game is initialized ! ");
    }
    @Override
    protected void takeTurn() {
        System.out.println("Tour " + turn + " du jeu en cours " + nbTurnsBySecond);
    }
    @Override
    protected boolean gameContinue() {
        return true;
    }
}
