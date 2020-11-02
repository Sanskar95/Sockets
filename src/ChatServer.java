import java.io.*;
import java.util.*;
import java.net.*;
import java.util.stream.Collectors;

public class ChatServer {

    public static List<ClientHandler> clientHandlers = new ArrayList<>();

    private void start(int port) throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("ChatServer is listening on port " + port);

        while(true) {
            Socket clientSocket = serverSocket.accept();
            String clientName = "client" + (clientHandlers.size() + 1);

            ClientHandler clientHandler = new ClientHandler(clientName, clientSocket, this);
            clientHandlers.add(clientHandler);

            new Thread(clientHandler).start();

            System.out.println("New client connected");
            String registeredClients = clientHandlers.stream().map(ClientHandler::getClientName)
                    .collect(Collectors.joining(", "));
            System.out.println("Registered clients: " + registeredClients);

            System.out.println("ChatServer is listening on port " + port);
        }
    }

    void broadcast(String message, String sendingClient) throws IOException {

        String clientsForBroadcast = clientHandlers.stream()
                .filter(ch -> !ch.getClientName().equals(sendingClient))
                .map(ClientHandler::getClientName)
                .collect(Collectors.joining(", "));
        System.out.printf("Broadcast message \"%s\" to %s%n", message, clientsForBroadcast);

        for (ClientHandler clientHandler : clientHandlers) {
            if (!clientHandler.getClientName().equals(sendingClient)) {
                clientHandler.getDataOutputStream().writeUTF(String.format("%s: %s", sendingClient, message));
            }
        }
    }

    public static void main(String[] args) throws IOException {

        if(args.length != 1) {
            System.out.println("One argument required: <port-number>");
            System.out.println("Syntax: java ChatServer <port-number>");
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);

        new ChatServer().start(port);
    }
}