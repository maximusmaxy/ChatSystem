
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatServerClientHandler implements Runnable {
    
    //private fields
    private Socket SOCK;
    private ObjectInputStream INPUT;

    /**
     * Constructor
     */
    public ChatServerClientHandler(Socket socket, ObjectInputStream input) {
        this.SOCK = socket;
        this.INPUT = input;
    }
    
    /**
     * Closes the connection and removes it from the static lists.
     * @throws Exception 
     */
    public void CloseConnection() throws Exception {
        if (!SOCK.isClosed()) {
            SOCK.close();
        }
        String user = null;
        for (int i = 0; i < ChatServer.ConnectionArray.size(); i++) {
            if (ChatServer.ConnectionArray.get(i) == SOCK) {
                ChatServer.ConnectionArray.remove(i);
                ChatServer.OutputStreams.remove(i);
                user = ChatServer.CurrentUsers.get(i);
                ChatServer.CurrentUsers.remove(i);
            }
        }
        
        Message message = new Message(Message.DISCONNECT, user + " disconnected.",
                getUsers());
        sendMessage(message);
    }

    /**
     * update loop for receiving messages
     */
    @Override
    public void run() {
        try {
            try {
                while (true) {

                    Message message;

                    message = (Message) INPUT.readObject();
                    if (message == null) {
                        return;
                    }

                    switch (message.getType()) {
                        case Message.MESSAGE:
                            sendMessage(message);
                            break;
                        case Message.CONNECT:
                            ChatServer.CurrentUsers.add((String) message.getContents());
                            Message newMessage = new Message(Message.CONNECT,
                                    message.getMessage(), getUsers());
                            sendMessage(newMessage);
                            break;
                        case Message.DISCONNECT:
                            return;
                        case Message.IMAGE:
                            int index = (int) ((Object[]) message.getContents())[1];
                            ObjectOutputStream output = ChatServer.OutputStreams.get(index);
                            output.writeObject(message);
                            output.flush();
                            break;
                    }
                    System.out.println("Sent");
                } //close inner loop
            }//close inner try
            finally {
                CloseConnection();
            }
        }//close outer try
        catch (Exception X) {
            System.out.println("Connection closed.");
        }
    }
    
    /**
     * sends the message to all users on the server.
     * @param message
     * @throws Exception 
     */
    public void sendMessage(Message message) throws Exception {
        for (ObjectOutputStream output : ChatServer.OutputStreams) {
            output.writeObject(message);
            output.flush();
        }
    }
    
    public String[] getUsers() {
        String[] users = new String[ChatServer.CurrentUsers.size()];
        ChatServer.CurrentUsers.toArray(users);
        return users;
    }
}
