// - *Main*: This class will represent the Main.

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Main {
    public static void main(String[] args) {
        Client[] clients = new Client[5]; // Create 5 clients
        for (int i = 0; i < clients.length; i++) {
            clients[i] = new Client();
        }

        ClientChat[] ClientChats = new ClientChat[5];
        for (int i = 0; i < ClientChats.length; i++) {
            ClientChats[i] = new ClientChat(clients); // Pass the array of clients to each ClientChat
            ClientChats[i].start();
        }
    }
}
