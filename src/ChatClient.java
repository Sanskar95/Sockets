import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ChatClient {

    private void start(String host, int port) throws IOException {

        Socket  socket = new Socket(host, port);
        System.out.printf("Connected to %s:%d%n", host, port);

        new ReadThread(socket).start();
        new WriteThread(socket).start();
    }

    public static void main(String[] args) throws IOException {

        if(args.length != 2) {
            System.out.println("Two arguments required: <host-address> <port-number>");
            System.out.println("Syntax: java ChatClient <host-address> <port-number>");
            System.exit(0);
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);

        new ChatClient().start(host, port);
    }

    private static class ReadThread extends Thread {

        private final Socket socket;

        public ReadThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                DataInputStream in = new DataInputStream(socket.getInputStream());

                while(true) {
                    String incomingMessage = in.readUTF();
                    System.out.println(incomingMessage);
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class WriteThread extends Thread {

        private final Socket socket;

        private final Scanner scanner = new Scanner(System.in);

        public WriteThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                while(true) {
                    String messageToSend = scanner.nextLine();
                    out.writeUTF(messageToSend);
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}

