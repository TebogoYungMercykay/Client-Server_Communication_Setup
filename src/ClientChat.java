// - *ClientChat*: This class will represent the ClientChat.
// import java.util.Map;
import java.util.Random;
// import java.util.HashMap;
// import java.util.List;
// import java.util.ArrayList;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ClientChat extends Thread {
    // private Map<String, List<Message>> clientMessages;
    // private AndersonLock lock;
    private volatile Client[] serverClients;
    public int person;

    public ClientChat(Client[] clients) {
        this.serverClients = clients;
        // this.clientMessages = new HashMap<>();
        // this.lock = new AndersonLock();
    }

    // public void setMessage(String clientName, Message message) {
    //     lock.lock();
    //     try {
    //         if (!clientMessages.containsKey(clientName)) {
    //             clientMessages.put(clientName, new ArrayList<>());
    //         }
    //         clientMessages.get(clientName).add(message);
    //     } finally {
    //         lock.unlock();
    //     }
    // }

    // public Message getMessage(String clientName) {
    //     lock.lock();
    //     try {
    //         List<Message> messages = clientMessages.get(clientName);
    //         if (messages != null && !messages.isEmpty()) {
    //             return messages.get(0); // Get the first message for simplicity
    //         }
    //         return null;
    //     } finally {
    //         lock.unlock();
    //     }
    // }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            for (Client myClient : serverClients) {
                long startTime = System.currentTimeMillis();
                String recipient = "Jane" + generateRandomMessage(4);
                String sender = "John" + generateRandomMessage(4);
                myClient.write(generateRandomMessage(10), recipient, sender);
                try {
                    long time = (int) Math.floor(Math.random() * (1000 + 1 - 100 + 1) + 100);
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myClient.read();
                if (System.currentTimeMillis() - startTime < 200) {
                    try {
                        Thread.sleep(200 - (System.currentTimeMillis() - startTime));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String generateRandomMessage(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String smallCharacters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder randomString = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                int index = random.nextInt(characters.length());
                char randomChar = characters.charAt(index);
                randomString.append(randomChar);
            } else {
                int index = random.nextInt(smallCharacters.length());
                char randomChar = smallCharacters.charAt(index);
                randomString.append(randomChar);
            }
        }

        return randomString.toString();
    }
}
