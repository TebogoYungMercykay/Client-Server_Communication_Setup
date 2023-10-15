import java.util.Random;
import java.util.concurrent.locks.Lock;

@SuppressWarnings({ "unchecked", "rawtypes" })

public class ReaderThread extends Thread {
    private String recipient;
    private Server server;
    Lock lock;

    public ReaderThread(Client client, Server server, Lock l) {
        this.lock = l;
        this.recipient = client.clientName;
        this.server = server;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            Message myMessage = this.server.getMessage(recipient);
            if (myMessage != null) {
                System.out.print("(RECEIVE) [" + myMessage.getName() + "] " + myMessage.toString() + "\n");
            }
        } finally {
            lock.unlock();
        }
        try {
            Random rand = new Random();
            int sleepTime = rand.nextInt(801) + 200;
            Thread.sleep(sleepTime);
        } catch(Exception exception){
            exception.printStackTrace();
        }
    }
}