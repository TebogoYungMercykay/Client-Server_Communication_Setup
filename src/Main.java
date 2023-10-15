// - *Main*: This class will represent the Main.

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Main {
    public static void main(String[] args) {
        Client[] clients = new Client[5]; // Create 5 clients
        for (int i = 0; i < clients.length; i++) {
            clients[i] = new Client();
        }

        Server ClientServer = new Server(clients);
        ClientServer.start();
    }
}
