// - **A Writer**: Sends new messages, destined for a specific client, to the server and logs the message in the clients chat.

public class WriterThread extends Thread {
    private Client client;
    private String message;
    private String recipient;

    public WriterThread(Client client, String message, String recipient) {
        this.client = client;
        this.message = message;
        this.recipient = recipient;
    }

    @Override
    public void run() {
        client.write(message, recipient);
    }
}