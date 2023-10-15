// This class will represent a client in the network.
public class Client {
    private String name;
    private OptimisticListSynchronization<Message> messages;
    private Server server;
    private TTASLock lock;

    public Client(String name, Server server) {
        this.name = name;
        this.server = server;
        this.messages = new OptimisticListSynchronization<>();
        this.lock = new TTASLock();
    }

    public void write(Message message) {
        lock.lock();
        try {
            messages.add(message);
            System.out.println("(SEND) " + Thread.currentThread().getName() + ": SUCCESSFUL");
        } finally {
            lock.unlock();
        }
    }

    public void read() {
        lock.lock();
        try {
            Message message = server.getMessage(this.name);
            if (message != null) {
                System.out.println("(RECEIVE) " + Thread.currentThread().getName() + ": " + message.toString());
            }
        } finally {
            lock.unlock();
        }
    }

    public void start() {
        Thread writerThread = new Thread(() -> {
            while (true) {
                // Generate a random message and recipient
                Message message = new Message();
                // ... initialize the message ...

                write(message);

                // Sleep for a while
                try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        });

        Thread readerThread = new Thread(() -> {
            while (true) {
                read();

                // Sleep for a while
                try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        });

        writerThread.start();
        readerThread.start();
    }
}