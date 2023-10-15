// - *Server*: This class will represent the Client Chat Server.
import java.util.*;
import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Server extends Thread {
    private Map<String, Message> clientMessages;
    private AndersonLock lock;
    public int numMessages;
    String[] clientNames;

    public Server(String[] clientNames, int numMessages) {
        this.clientMessages = new HashMap<>();
        this.lock = new AndersonLock();
        this.numMessages = numMessages;
        this.clientNames = clientNames;
    }

    public String getRecipient(String clientName) {
        Random rand = new Random();
        String recipient = null;
        ArrayList<String> receiverList = new ArrayList<>(this.generateMoreSenders(2));
        if (!receiverList.isEmpty()) {
            int randomIndex = rand.nextInt(receiverList.size());
            recipient = receiverList.get(randomIndex);
            while (clientName.equals(recipient)) {
                // Making Sure the User doesn't send a Message to Oneself
                randomIndex = rand.nextInt(receiverList.size());
                recipient = receiverList.get(randomIndex);
            }
        }
        return recipient != null ? recipient : "John Doe";
    }

    public void setMessage(String clientName, Message message) {
        lock.lock();
        try {
            this.clientMessages.put(clientName, message);
            System.out.println("(SEND) " + Thread.currentThread().getName() + ": SUCCESSFUL");
        } finally {
            lock.unlock();
        }
    }

    public Message getMessage(String clientName) {
        lock.lock();
        try {
            Message messages = this.clientMessages.get(clientName);
            if (messages != null) {
                return messages;
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public Queue<String> convertListToQueue(List<String> list) {
        return new LinkedList<>(list);
    }

    public Queue<String> generateMoreSenders(int numTimesEach) {
        return this.convertListToQueue(getShuffledList(numTimesEach));
    }

    public Queue<String> generateReceiver(int numTimesEach) {
        return this.convertListToQueue(getShuffledList(numTimesEach));
    }

    public String getMessage() {
        String[] greetings = {
            "Hello, my friend! Hope you're having a good day.",
            "Hey buddy! Long time no see.",
            "Hi there! How have you been?",
            "Greetings, pal! What's new with you?",
            "Hey! It's always great to hear from you.",
            "Hello, friend! What's the good news today?",
            "Hi, mate! How's everything going?",
            "Hey there! Missed you a lot.",
            "Greetings! How's your day going so far?",
            "Hello! Hope everything is going well with you.",
            "Hi, friend! Let's catch up soon.",
            "Hey, buddy! Can't wait to hear about your day.",
            "Greetings, mate! Let's get together soon.",
            "Hello! Always a pleasure to see you.",
            "Hi there, friend! Looking forward to our next meet-up."
        };

        Random rand = new Random();
        int randomIndex = rand.nextInt(greetings.length);

        return greetings[randomIndex];
    }

    public List<String> getShuffledList(int numTimesEach) {
        String names[] = this.getServerClients();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < numTimesEach; i++) {
            list.addAll(Arrays.asList(names));
        }
        Collections.shuffle(list);
        return list;
    }

    public String[] getServerClients() {
        return this.clientNames;
    }

    public String generateRandomMessage(int length) {
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
