import Controller.GameController;
import Model.Game;
import Model.SimpleGame;
import Vue.ViewCommand;
import Vue.ViewSimpleGame;

public class Test {
    public static void main(String[] args) {
        //long[] times = {500, 1000};

        //for (long time : times) {
        //    SimpleGame game = new SimpleGame(5, time);
     //    game.init();
         //   game.launch();
      //  }
        SimpleGame simpleGame = new SimpleGame(5, 500);
        GameController controller = new GameController(simpleGame);
        ViewCommand vue = new ViewCommand(controller, simpleGame);
        ViewSimpleGame simpleVue = new ViewSimpleGame(simpleGame);

    }
}