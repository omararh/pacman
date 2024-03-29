package Test;

import Controller.ControllerPacmanGame;
import Model.Game.PacmanGame;
import NetWork.Server.Server;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws Exception {

        // the originalClassic layout is the default used layout for the maze
        final String mazeDefaultLayout = "src/Layouts/originalClassic.lay";
        //Maze maze = new Maze(mazeDefaultLayout);

        PacmanGame pacmanGame = new PacmanGame(mazeDefaultLayout, 500);
        ControllerPacmanGame controllerPacmanGame = new ControllerPacmanGame(pacmanGame);


        // Server configuration
        System.out.println("chosen Port Number : ");
        Scanner scanner = new Scanner(System.in);
        String portNumber = scanner.nextLine();

        Server serverSocket = new Server(Integer.parseInt(portNumber), controllerPacmanGame);
        System.out.println("Server is running ... ...");

        serverSocket.listen();
    }
}