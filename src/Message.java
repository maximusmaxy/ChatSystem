
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class is the message protocol used by the client and server.
 * @author Max
 */
public class Message implements Serializable{
    
    //message type constants
    public static final int MESSAGE = 1;
    public static final int IMAGE = 2;
    public static final int CONNECT = 3;
    public static final int DISCONNECT = 4;
    
    //private fields
    private int type;
    private String message;
    private Object contents;

    //constructor
    public Message(int type, String message, Object contents) {
        this.type = type;
        this.message = message;
        this.contents = contents;
    }
    
    //getters and setters
    public int getType() {
        return type;
    }
    
    public String getMessage() {
        return message;
    }
    
    public Object getContents() {
        return contents;
    }
}
