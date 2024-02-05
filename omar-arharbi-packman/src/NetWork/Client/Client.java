package NetWork.Client;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private String messageToSend;


    public Client(String host, int portNumber) throws IOException {
        // establish connexion with serverSocket
        this.socket = new Socket(host, portNumber);
        // initialize the PrintWriter
        this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
    }

    public void sendKey (String message) {
        this.messageToSend = message;
        this.out.println(this.messageToSend);
    }

    public void close() throws IOException {
        this.out.close();
        this.socket.close();
    }
}
