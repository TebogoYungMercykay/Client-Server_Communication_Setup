// - *Main*: This is the Testing Main for the Server.

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Main {
    public static void main(String[] args) {
        // Creating 5 Initial Clients On the Server
        Client[] clients = new Client[5];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = new Client();
        }

        // The Number of messages Each Client Shall Send to Other Clients
        int numMessages = 5;
        Server ClientServer = new Server(clients, numMessages);
        ClientServer.start();
    }
}
