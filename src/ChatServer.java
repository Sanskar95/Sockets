
import java.io.*;
import java.util.*;
import java.net.*;

public class ChatServer
{
    public   static   List<ClientHandler>  clientHandlers = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        ServerSocket ss = new ServerSocket(5000);
        Socket clientSocket;

        while (true)
        {
            clientSocket = ss.accept();

            System.out.println("New client is : " + clientSocket);

            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());


            Integer clientIndex= clientHandlers.size()+1;
            ClientHandler clientHandler = new ClientHandler("client" + clientIndex, dataInputStream, dataOutputStream, clientSocket);

            Thread thread = new Thread(clientHandler);
            System.out.println("Adding this client to active client list");
            clientHandlers.add(clientHandler);
            clientHandlers.forEach(clientHandler1 -> System.out.println(clientHandler1.getClientName()));
            thread.start();


        }
    }
}

