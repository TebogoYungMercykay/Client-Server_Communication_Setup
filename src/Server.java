// - *Server*: This class will represent the server.
import java.util.Map;
import java.util.HashMap;

public class Server {
    private FineGrainList<Map<String, Client>> clientMessages;
    private AndersonLock lock;

    public Server() {
        this.clientMessages = new FineGrainList(new HashMap<>());
        this.lock = new AndersonLock();
    }

    public void setMessage(String clientName, Message message) {
        lock.lock();
        try {
            clientMessages.getUnderlying().put(clientName, message);
        } finally {
            lock.unlock();
        }
    }

    public Message getMessage(String clientName) {
        lock.lock();
        try {
            return clientMessages.getUnderlying().get(clientName);
        } finally {
            lock.unlock();
        }
    }
}