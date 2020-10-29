import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler implements Runnable
{
    private String clientName;
   private  final DataInputStream dataInputStream;
   private final DataOutputStream dataOutputStream;
    private Socket clientSocket;

    public ClientHandler(String clientName, DataInputStream dataInputStream, DataOutputStream dataOutputStream,  Socket clientSocket) {
        this.clientName = clientName;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        while (true)
        {
            try
            {
                String received = dataInputStream.readUTF();
                System.out.println(received);

                if(received.equals("logout")){
                    this.clientSocket.close();
                    break;
                }
                String messageToSend = received.split("-")[1];
                String recipient = received.split("-")[0];


                for (ClientHandler clientHandler : ChatServer.clientHandlers)
                {
                    if (clientHandler.getClientName().equals(recipient))
                    {
                        clientHandler.getDataOutputStream().writeUTF(this.getClientName()+" : "+messageToSend);
                        break;
                    }
                }
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
