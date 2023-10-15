// This class will represent a client in the network.

@SuppressWarnings({ "unchecked", "rawtypes", "generics" })
public class Client {
    private MessageSynchronization messages;
    String clientName;

    public Client(String clientName) {
        this.clientName = clientName;
        this.messages = new MessageSynchronization<>();
    }

    public void write(String message, String recipient) {
        messages.write(Thread.currentThread(), message, recipient, this.clientName);
    }

    public void read() {
        messages.read(this.clientName);
    }

    public String getClientName() {
        return this.clientName;
    }
}
