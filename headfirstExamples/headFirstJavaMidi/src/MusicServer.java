import java.io.*;
import java.net.*;
import java.util.*;

public class MusicServer {
    ArrayList<ObjectOutputStream> clientOutputStreams;

    public static void main(String[] args) {
        new MusicServer().go();
    }

    public class ClientHandler implements Runnable {
        ObjectInputStream in;
        Socket clientSocket;

        public ClientHandler(Socket socket) {
            clientSocket = socket;
            try {
                in = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void run() {
            Object o2 = null;
            Object o1 = null;

            try {
                while ( (o1 = in.readObject()) != null ) {
                    o2 = in.readObject();
                    System.out.println("read two objects");
                    tellEveryone(o1, o2);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void go() {
        clientOutputStreams = new ArrayList<>();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4242);


            while (true) {
                Socket clientSocket = serverSocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                clientOutputStreams.add(out);

                Thread t = new Thread( new ClientHandler(clientSocket) );
                t.start();
                System.out.println("got connection");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tellEveryone( Object one, Object two ) {
        for (ObjectOutputStream out : clientOutputStreams) {
            try {
                out.writeObject(one);
                out.writeObject(two);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
