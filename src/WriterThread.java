// - **A Writer**: Sends new messages, destined for a specific client, to the server and logs the message in the clients chat.

public class WriterThread extends Thread {
    private Client client;
    private String message;
    private String recipient;
    private String sender;

    public WriterThread(Client client, String message, String recipient, String sender) {
        this.client = client;
        this.message = message;
        this.recipient = recipient;
        this.sender = sender;
    }

    @Override
    public void run() {
        client.write(message, recipient, sender);
    }
}