
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class ChatServer {

    //Globals
    public static ArrayList<Socket> ConnectionArray = new ArrayList<Socket>();
    public static ArrayList<String> CurrentUsers = new ArrayList<String>();
    public static ArrayList<ObjectOutputStream> OutputStreams = new ArrayList<ObjectOutputStream>();

    //-------------------------------------------------------------------------
    public static void main(String[] args) throws IOException {
        try {
            final int PORT = 1024;
            ServerSocket SERVER = new ServerSocket(PORT);
            System.out.println("Waiting for clients...");

            while (true) {
                Socket SOCK = SERVER.accept();
                ConnectionArray.add(SOCK);

                System.out.println("Client connected from: " + SOCK.getLocalAddress().getHostName());
                ObjectOutputStream output = new ObjectOutputStream(SOCK.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(SOCK.getInputStream());
                
                OutputStreams.add(output);

                ChatServerClientHandler CHAT = new ChatServerClientHandler(SOCK, input);
                Thread X = new Thread(CHAT);
                X.start();
                System.out.println("Client thread started.");
            }
        } catch (Exception X) {
            System.out.print(X);
            X.printStackTrace();
        }
    }
}
