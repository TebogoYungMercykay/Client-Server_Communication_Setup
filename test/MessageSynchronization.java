import java.util.*;
// This is used for managing the list of messages in each client or in the server. This approach checks for conflicts at the time of committing an operation rather than when it is started.

@SuppressWarnings({ "unchecked", "rawtypes" })
public class MessageSynchronization<T> {
    // The Map for Storing the Messages for Clients
    private Map<String, Message<T>> clientMessages;
    private TTASLock lock;

    public MessageSynchronization() {
        this.clientMessages = new HashMap<>();
        this.lock = new TTASLock();
    }

    public void write(T item, String messageContent, String recipient, String sender) {
        lock.lock();
        try {
            this.clientMessages.put(recipient, new Message<>(item, messageContent, recipient, sender));
            System.out.println("(SEND) " + Thread.currentThread().getName() + ": SUCCESSFUL");
        } finally {
            lock.unlock();
        }
    }

    public void read(String clientName) {
        lock.lock();
        try {
            Message<T> message = this.clientMessages.get(clientName);
            if (message != null) {
                System.out.println("(RECEIVE) [" + message.getName() + "] " + message.toString());
            }
        } finally {
            lock.unlock();
        }
    }
}
