
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ChatClient implements Runnable {

    //private fields
    private Socket SOCK;
    private ObjectInputStream INPUT;
    private ObjectOutputStream OUT;
    private ChatClientFrame frame;

    /**
     * Constructor
     */
    public ChatClient(ChatClientFrame frame, Socket X) {
        this.frame = frame;
        this.SOCK = X;
        try {
            INPUT = new ObjectInputStream(SOCK.getInputStream());
            OUT = new ObjectOutputStream(SOCK.getOutputStream());
        } catch (Exception ex) {
            System.out.println("Error opening ports for socket " + X);
        }
    }

    /**
     * the threads update loop.
     */
    @Override
    public void run() {
        try {
            try {
                receive();
            } finally {
                if (!SOCK.isClosed()) {
                    SOCK.close();
                }
            }
        } catch (Exception X) {
            System.out.print("disconnected");
        }
    }

    /**
     * sends the connect message
     * @param username
     * @throws IOException 
     */
    public void CONNECT(String username) throws IOException {
        Message message = new Message(Message.CONNECT,
                username + " has joined the chat.", username);
        OUT.writeObject(message);
        OUT.flush();
    }

    /**
     * sends the disconnect message and closes the socket
     * @throws IOException 
     */
    public void DISCONNECT() throws IOException {

        Message message = new Message(Message.DISCONNECT,
                frame.getUsername() + " has disconnected.", null);
        OUT.writeObject(message);
        OUT.flush();
        JOptionPane.showMessageDialog(null, "You disconnected!");
        //System.exit(0);
        if (!SOCK.isClosed()) {
            SOCK.close();
        }
    }

    /**
     * receives a message and determines how to handle it
     * @throws Exception 
     */
    public void receive() throws Exception {
        Message message;
        while (true) {
            System.out.println("Recieving");
            message = (Message) INPUT.readObject();
            if (message == null) {
                return;
            }
            System.out.println("Recieved");
            switch (message.getType()) {
                case Message.MESSAGE:
                    receiveMessage((String) message.getMessage());
                    break;
                case Message.IMAGE:
                    receiveImage(message);
                    break;
                case Message.CONNECT:
                    receiveConnect(message);
                    break;
                case Message.DISCONNECT:
                    receiveDisconnect(message);
                    break;
            }
        }
    }

    /**
     * receive a basic message and output it to the conversation.
     * @param message 
     */
    private void receiveMessage(String message) {
        System.out.println("Appending: " + message);
        frame.appendMessage(message);
    }

    /**
     * receives and image and displays it on the screen.
     * @param message 
     */
    private void receiveImage(Message message) {
        frame.appendMessage(message.getMessage());
        Object[] contents = (Object[]) message.getContents();
        byte[] imgBytes = (byte[]) contents[0];
        ByteArrayInputStream bais = new ByteArrayInputStream(imgBytes);
        try {
            BufferedImage img = ImageIO.read(bais);
            frame.displayImage(img);
        } catch (IOException ex) {
            System.out.println("Error reading image.");
            ex.printStackTrace();
        }
    }

    /**
     * receives a connection message and updates user list.
     * @param message 
     */
    private void receiveConnect(Message message) {
        frame.appendMessage(message.getMessage());
        frame.getOnlineUsers().setListData((String[]) message.getContents());
    }

    /**
     * receives a disconnection message and updates user list.
     * @param message 
     */
    private void receiveDisconnect(Message message) {
        frame.appendMessage(message.getMessage());
        frame.getOnlineUsers().setListData((String[]) message.getContents());
    }

    /**
     * sends a message and outputs it to the conversation text area.
     * @param X 
     */
    public void SEND(String X) {
        Message message = new Message(Message.MESSAGE, frame.getUsername() + ": " + X, null);

        try {
            System.out.println("Sending: " + X);
            OUT.writeObject(message);
            OUT.flush();
            frame.getMessage().setText("");
        } catch (IOException iOException) {
            System.out.println("Error sending message: " + X);
        }
    }

    /**
     * Sends an image to the specific user
     * @param img
     * @param username
     * @param index 
     */
    public void SENDIMAGE(BufferedImage img, String username, int index) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", baos);
            Object[] contents = {baos.toByteArray(), index};
            Message message = new Message(Message.IMAGE, "Recieved image from " + username, contents);
            OUT.writeObject(message);
            OUT.flush();
        } catch (Exception ex) {
            System.out.println("Error sending image");
        }
    }
}
