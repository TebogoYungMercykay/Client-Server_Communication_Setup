import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// This class will represent a message sent or received by a client.
@SuppressWarnings({ "unchecked", "rawtypes" })

public class Message<T> {
    public T item;
    private String sender;
    private String recipient;
    private String content;
    public int hashCodeKey;
    public Message next;
    public Lock lock = new ReentrantLock();
    String threadName;

    public Message(T item, String message, String recipient, String sender) {
        this.next = null;
        this.item = item;
        this.sender = sender;
        this.recipient = recipient;
        this.content = message;
        this.threadName = Thread.currentThread().getName();
        this.hashCodeKey = item.hashCode();
    }

    public Message() {
        this.next = null;
        this.item = null;
        this.sender = "Jane Doe";
        this.recipient = "John Doe";
        this.content = "Hey There Hero, You've Been Out A Long Time!!";
        this.threadName = Thread.currentThread().getName();
        this.hashCodeKey = hashCode();
    }

    @Override
    public String toString() {
        return "Message: { sender: " + sender + ", recipient: " + recipient + ", content: " + content + " }";
    }

    public String messageDetails() {
        return "(Received): { sender: " + sender + ", content: " + content + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        // Casting
        Message<T> message = (Message<T>) obj;
        return Objects.equals(sender, message.sender) && Objects.equals(recipient, message.recipient) && Objects.equals(content, message.content);
    }

    // @Override
    // public int hashCode() {
    //     return Objects.hash(sender, recipient, content);
    // }

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

    public int getHashCodeKey() {
        return this.hashCodeKey;
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

    public String getName() {
        return Thread.currentThread().getName();
    }
}
