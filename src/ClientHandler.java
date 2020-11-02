import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler implements Runnable {

    public static final String EXIT_COMMAND = "exit";

    private final String clientName;
    private final Socket clientSocket;
    private final ChatServer chatServer;

    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ClientHandler(String clientName, Socket clientSocket, ChatServer chatServer) {
        this.clientName = clientName;
        this.clientSocket = clientSocket;
        this.chatServer = chatServer;
    }

    @Override
    public void run() {

        try {
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true) {
            try {
                String receivedMessage = dataInputStream.readUTF();

                /*if(receivedMessage.equalsIgnoreCase(EXIT_COMMAND)){
                    this.clientSocket.close();
                    break;
                }*/
                // Closing socket requires to inform server that client is not connected anymore.
                // Todo: Make server listen to exit messages

                chatServer.broadcast(receivedMessage, this.clientName);

            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getClientName() {
        return clientName;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }
}
