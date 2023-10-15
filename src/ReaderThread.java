// - **A Reader**: Checks for any new message, on the server, for the client and logs the message in the clients chat.

public class ReaderThread extends Thread {
    private Client client;

    public ReaderThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        client.read();
    }
}