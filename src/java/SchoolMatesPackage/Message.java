
package SchoolMatesPackage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Message {

    private int id;
    private int senderID;
    private int receiverID;
    private String message;
    private String title;
    private String timeSent;
    private int messageRead; // 0 - not read, 1 - read

    public Message() {
    }

    public Message(int senderID, int receiverID, String message, String title, String timeSent) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.message = message;
        this.title = title;
        this.timeSent = timeSent;
        this.messageRead = 0;

        this.id = 0; //initialising to zero to show it is a new message. 
        //The proper ID will be assigned when the object is persisted into the database
    }

    /**
     * Persist method that inserts messages into the database
     *
     *
     * @throws SQLException
     */
    public void persist() throws SQLException { //just inserting
        DBAccessor db = new DBAccessor();
        Connection c;
        try {
            c = db.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Connection failed..."), e);
        }//Connection established

        PreparedStatement q = c.prepareStatement("INSERT INTO message "
                + "(sender_id, receiver_id, message, title, time_sent, message_read)"
                + " VALUES(?,?,?,?,?,?)");
        q.setInt(1, senderID);
        q.setInt(2, receiverID);
        q.setString(3, message);
        q.setString(4, title);
        q.setString(5, timeSent);
        q.setInt(6, messageRead);


        q.executeUpdate();
        
         if (id == 0) {
            //get the newly created ID and store it in this bean
            q = c.prepareStatement("SELECT * FROM message ORDER BY id DESC Limit 1");
            ResultSet result = q.executeQuery();
            if (result.next()) {
                id = result.getInt(1);
            }

        }
        c.close();
    }
    /**
     * Sets the message status as READ
     * @throws SQLException 
     */
    public void setAsRead() throws SQLException{
        DBAccessor db = new DBAccessor();
        Connection c;
        try {
            c = db.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Connection failed..."), e);
        }//Connection established

        //updating database
        PreparedStatement q = c.prepareStatement("UPDATE message "
                + "SET message_read = 1 WHERE id =?");
        q.setInt(1, id);
        q.executeUpdate();
        c.close();
        
        //updating beans
        messageRead = 1;
    }
    /**
     * Lists all messages sent by this senderID
     * @param receiverID
     * @return ArrayList of sent Messages
     * @throws SQLException 
     */
    public static ArrayList<Message> listAllSentMessages(int senderID) throws SQLException{
        ArrayList<Message> list = new ArrayList<Message>();
        
        
        DBAccessor db = new DBAccessor();
        Connection c;
        try {
            c = db.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Connection failed..."), e);
        }//Connection established

        PreparedStatement q = c.prepareStatement("SELECT * from message WHERE sender_id=? ORDER BY message.id DESC");
        q.setInt(1, senderID);
        
        ResultSet results = q.executeQuery();
        
        while(results.next()){
            Message msg = new Message();
            msg.id = results.getInt("id");
            msg.senderID = results.getInt("sender_id");
            msg.receiverID = results.getInt("receiver_id");
            msg.message = results.getString("message");
            msg.title = results.getString("title");
            msg.timeSent = results.getString("time_sent");
            msg.messageRead = results.getInt("message_read");
            
            list.add(msg);
        }
        c.close();
        if (list.isEmpty()){
            return null;
        }
        return list;
    }
    /**
     * Lists all messages received by this receiverID
     * @param receiverID
     * @return ArrayList of received Messages
     * @throws SQLException 
     */
    public static ArrayList<Message> listAllReceivedMessages(int receiverID) throws SQLException{
        ArrayList<Message> list = new ArrayList<Message>();
        
        
        DBAccessor db = new DBAccessor();
        Connection c;
        try {
            c = db.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Connection failed..."), e);
        }//Connection established

        PreparedStatement q = c.prepareStatement("SELECT * from message WHERE receiver_id=? ORDER BY message.id DESC");
        q.setInt(1, receiverID);
        
        ResultSet results = q.executeQuery();
        
        while(results.next()){
            Message msg = new Message();
            msg.id = results.getInt("id");
            msg.senderID = results.getInt("sender_id");
            msg.receiverID = results.getInt("receiver_id");
            msg.message = results.getString("message");
            msg.title = results.getString("title");
            msg.timeSent = results.getString("time_sent");
            msg.messageRead = results.getInt("message_read");
            
            list.add(msg);
        }
        c.close();
        if (list.isEmpty()){
            return null;
        }
        return list;
    }
    
    /**
     * Lists all senders of the messages received by this receiverID
     * @param receiverID
     * @return ArrayList of Student objects (senders)
     * @throws SQLException 
     */
    public static ArrayList<Student> listAllSenders(int receiverID) throws SQLException{
        ArrayList<Student> list = new ArrayList<Student>();
        
        
        DBAccessor db = new DBAccessor();
        Connection c;
        try {
            c = db.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Connection failed..."), e);
        }//Connection established

        PreparedStatement q = c.prepareStatement("SELECT student.id, student.name, student.surname, student.school_surname FROM student, message WHERE message.sender_id=student.id AND message.receiver_id=? ORDER BY message.id DESC");
        q.setInt(1, receiverID);
        
        ResultSet results = q.executeQuery();
        Student student = new Student();
        while(results.next()){
            
            student.setID(results.getInt("id"));
            student.setName(results.getString("name"));
            student.setSurname(results.getString("surname"));
            student.setSchoolSurname(results.getString("school_surname"));
            
            
            list.add(student);
        }
        c.close();
        if (list.isEmpty()){
            return null;
        }
        return list;
    }
    
    /**
     * Lists all receivers of the messages sent by this senderID
     * @param receiverID
     * @return ArrayList of Student objects (receivers)
     * @throws SQLException 
     */
    public static ArrayList<Student> listAllReceivers(int senderID) throws SQLException{
        ArrayList<Student> list = new ArrayList<Student>();
        
        
        DBAccessor db = new DBAccessor();
        Connection c;
        try {
            c = db.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Connection failed..."), e);
        }//Connection established

        PreparedStatement q = c.prepareStatement("SELECT student.id, student.name, student.surname, student.school_surname FROM student, message WHERE message.receiver_id=student.id AND message.sender_id=? ORDER BY message.id DESC");
        q.setInt(1, senderID);
        
        ResultSet results = q.executeQuery();
        Student student = new Student();
        while(results.next()){
            
            student.setID(results.getInt("id"));
            student.setName(results.getString("name"));
            student.setSurname(results.getString("surname"));
            student.setSchoolSurname(results.getString("school_surname"));
            
            
            list.add(student);
        }
        c.close();
        if (list.isEmpty()){
            return null;
        }
        return list;
    }
    
    /**
     * Checks if the message had been read
     * @return true if the message had been read, else false
     */
    public boolean isRead(){
        if (messageRead==1){
            return true;
        }else{
            return false;
        }
    }
    
        
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public int getMessageRead() {
        return messageRead;
    }

    public void setMessageRead(int messageRead) {
        this.messageRead = messageRead;
    }
    
    
}
