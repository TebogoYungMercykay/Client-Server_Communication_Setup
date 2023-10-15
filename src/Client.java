// This class will represent a client in the network.

@SuppressWarnings({ "unchecked", "rawtypes", "generics" })
public class Client {
    private OptimisticListSynchronization messages;
    private TTASLock lock;

    public Client() {
        this.messages = new OptimisticListSynchronization<>();
        this.lock = new TTASLock();
    }

    public void write(String message, String recipient, String sender) {
        lock.lock();
        try {
            while (!messages.add(Thread.currentThread(), message, recipient, sender)){};
        } finally {
            lock.unlock();
        }
    }

    public void read() {
        lock.lock();
        try {
            while (!messages.remove(Thread.currentThread())){};
        } finally {
            lock.unlock();
        }
    }
}
