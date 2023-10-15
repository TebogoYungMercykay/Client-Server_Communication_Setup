import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// This class will represent a message sent or received by a client.

public class Message {
    private String sender;
    private String recipient;
    private String content;
    public int key;
    public Lock lock = new ReentrantLock();
    String threadName;

    public Message(String message, String recipient, String sender) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = message;
        this.threadName = Thread.currentThread().getName();
        this.key = hashCode();
    }

    public Message() {
        this.sender = "Jane Doe";
        this.recipient = "John Doe";
        this.content = "Hey There Hero, You've Been Out A Long Time!!";
        this.key = hashCode();
        this.threadName = Thread.currentThread().getName();
    }

    @Override
    public String toString() {
        return "Message: { sender: " + sender + ", recipient: " + recipient + ", content: " + content + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        // Casting
        Message message = (Message) obj;
        return Objects.equals(sender, message.sender) && Objects.equals(recipient, message.recipient) && Objects.equals(content, message.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, recipient, content);
    }

    // Getters and Setters

    public String getSender() {
        return this.sender;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public String getContent() {
        return this.content;
    }

    public int getKey() {
        return this.key;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
