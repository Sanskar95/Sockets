import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ChatClient
{

    public static void main(String args[]) throws IOException {
        Scanner scn = new Scanner(System.in);
        Socket  socket = new Socket("127.0.0.1", 5000);
        System.out.println("Connected");

        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());


        Thread sendMessage = new Thread(() -> {
            while (true) {

                // read the message to deliver.
                String msg = scn.nextLine();

                try {
                    // write on the output stream
                    dataOutputStream.writeUTF(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread readMessage = new Thread(() -> {

            while (true) {
                try {
                    String incomingMessage = dataInputStream.readUTF();
                    System.out.println(incomingMessage);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        });
sendMessage.start();
readMessage.start();
    }
}

