package Test;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import NetWork.Client.Client;

public class MainClient {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Port Number : ");
        String portNumber = scanner.nextLine();

        System.out.println("\n IP address : ");
        String ipAddress = scanner.nextLine();

        Client clientA = new Client(ipAddress, Integer.parseInt(portNumber));

        while (true) {
            System.out.println("choose one of { w | s | a | d } (or 'quit' to leave the Game) :");
            String input = scanner.nextLine();

            if (Objects.equals(input, "quit")) {
                break;
            }

            clientA.sendKey(input);
        }

        clientA.close();
        scanner.close();
    }
}
