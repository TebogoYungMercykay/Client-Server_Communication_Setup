// This class will represent a client in the network.

@SuppressWarnings({ "unchecked", "rawtypes", "generics" })
public class Client {
    private OptimisticListSynchronization messages;
    private TTASLock lock;
    String clientName;

    public Client(String clientName) {
        this.clientName = clientName;
        this.messages = new OptimisticListSynchronization<>();
        this.lock = new TTASLock();
    }

    public void write(String message, String recipient, String sender) {
        System.out.println("(SEND) [" + Thread.currentThread().getName() + "]: { sender:[" + sender + "] , recipient:[" + recipient + "]}");
        lock.lock();
        try {
            while (!messages.write(Thread.currentThread(), message, recipient, sender)){};
        } finally {
            lock.unlock();
        }
    }

    public void read() {
        lock.lock();
        try {
            while (!messages.read(Thread.currentThread())){};
        } finally {
            lock.unlock();
        }
    }

    public String getClientName() {
        return this.clientName;
    }
}
