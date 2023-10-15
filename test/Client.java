// This class will represent a client in the network.

@SuppressWarnings({ "unchecked", "rawtypes", "generics" })
public class Client {
    private MessageSynchronization messages;
    private TTASLock lock;
    String clientName;

    public Client(String clientName) {
        this.clientName = clientName;
        this.messages = new MessageSynchronization<>();
        this.lock = new TTASLock();
    }

    public void write(String message, String recipient, String sender) {
        System.out.println("(SEND) [" + Thread.currentThread().getName() + "]: { sender:[" + sender + "] , recipient:[" + recipient + "]}");
        lock.lock();
        try {
            messages.write(Thread.currentThread(), message, recipient, sender);
        } finally {
            lock.unlock();
        }
    }

    public void read(String recipient) {
        lock.lock();
        try {
            messages.read(recipient);
        } finally {
            lock.unlock();
        }
    }

    public String getClientName() {
        return this.clientName;
    }
}
