package NetWork.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import Controller.PacmanGameActions;
import java.util.Arrays;



public class ClientHandler extends Thread {
    private Socket clientSocket;
    private PacmanGameActions pacmanGameActions;
    private static int cp = 1;
    private int id;

    public ClientHandler(Socket socket, PacmanGameActions pacmanGameActions) {
        this.clientSocket = socket;
        this.pacmanGameActions = pacmanGameActions;
        this.id = cp++;

        System.out.println("The clientHandler id is --> " + this.id);
    }

    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String sentKey;
            while ((sentKey = input.readLine()) != null) {
                boolean belongs = Arrays.asList(new String[]{"w", "s", "a", "d"}).contains(sentKey);

                if(!belongs){
                    System.out.println("\u001B[31m" + "Illegal key choose one of { w | s | a | d } !!!" + "\u001B[0m");
                    continue;
                }
                System.out.println("chosen key ----> |" + sentKey + "|");

                this.pacmanGameActions.handleKeyboardMovement(sentKey.charAt(0), true, this.id);
            }
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
