package NetWork.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import Controller.ControllerPacmanGame;


public class Server {
    private ServerSocket serverSocket;
    private ControllerPacmanGame controllerPacmanGame;

    public Server(int portNumber, ControllerPacmanGame controllerPacmanGame) throws IOException {
        this.serverSocket = new ServerSocket(portNumber);
        this.controllerPacmanGame = controllerPacmanGame;
    }

    public void listen() throws IOException {
        while (true) {
            Socket clientSocket = this.serverSocket.accept();
            System.out.println("Un client viens de se connecter !");
            new ClientHandler(clientSocket, this.controllerPacmanGame).start();
        }
    }

    public InetAddress getInetAddress() {
        return this.serverSocket.getInetAddress();
    }
}

