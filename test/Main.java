// - *Main*: This is the Testing Main for the Server.

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Main {
    public static void main(String[] args) {
        // The Current Number of Clients
        int numClients = 5;

        // The Current Number of Messages each Client Sends
        int numMessages = 10;

        // Creating an Array of Client Names
        String[] serverClients = new String[numClients];
        serverClients[0] = "Thabo";
        serverClients[1] = "Ntando";
        serverClients[2] = "Luke";
        serverClients[3] = "Scott";
        serverClients[4] = "Michael";

        // Creating 5 Initial Clients On the Server
        Client[] clients = new Client[numClients];
        for (int i = 0; i < numClients; i++) {
            clients[i] = new Client(serverClients[i]);
        }

        Server serverObject = new Server(clients, serverClients, numMessages);
        serverObject.start();
    }
}
