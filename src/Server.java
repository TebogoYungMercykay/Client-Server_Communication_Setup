// - *Server*: This class will represent the Client Chat Server.
import java.util.*;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Server extends Thread {
    private Map<String, List<Message>> clientMessages;
    private AndersonLock lock;
    private volatile Client[] serverClients;
    public int numMessages;

    public Server(Client[] clients, int numMessages) {
        this.serverClients = clients;
        this.clientMessages = new HashMap<>();
        this.lock = new AndersonLock();
        this.numMessages = numMessages;
    }

    public void setMessage(String clientName, Message message) {
        lock.lock();
        try {
            if (!clientMessages.containsKey(clientName)) {
                clientMessages.put(clientName, new ArrayList<>());
            }
            clientMessages.get(clientName).add(message);
        } finally {
            lock.unlock();
        }
    }

    public Message getMessage(String clientName) {
        lock.lock();
        try {
            List<Message> messages = clientMessages.get(clientName);
            if (messages != null && !messages.isEmpty()) {
                return messages.get(0);
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void run() {
        Queue<String> mySenders = generateSender(this.numMessages + 1);
        Queue<String> myReceivers = generateReceiver(this.numMessages + 1);

        for (int i = 0; i < this.numMessages; i++) {
            for (Client myClient : serverClients) {
                String sender = mySenders.poll();
                String recipient = myReceivers.poll();
                while (sender == recipient) {
                    myReceivers.add(recipient);
                    recipient = myReceivers.poll();
                }
                if (sender != null && recipient != null) {
                    long startTime = System.currentTimeMillis();
                    String sentMessage = getMessage();
                    myClient.write(sentMessage, recipient, sender);
                    Message messageObject = new Message(Thread.currentThread(),sentMessage, recipient, sender);
                    setMessage(recipient, messageObject); // Add the message to the map
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
    }

    public static Queue<String> convertListToQueue(List<String> list) {
        return new LinkedList<>(list);
    }

    public static Queue<String> generateSender(int numTimesEach) {
        return convertListToQueue(getShuffledList(numTimesEach));
    }

    public static Queue<String> generateReceiver(int numTimesEach) {
        return convertListToQueue(getShuffledList(numTimesEach));
    }

    public static String getMessage() {
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

    public static List<String> getShuffledList(int numTimesEach) {
        String names[] = { "Thabo", "Ntando", "Luke", "Scott", "Michael" };
        List<String> list = new ArrayList<>();
        for (int i = 0; i < numTimesEach; i++) {
            list.addAll(Arrays.asList(names));
        }
        Collections.shuffle(list);
        return list;
    }

    public static String[] getServerClients() {
        String names[] = { "Thabo", "Ntando", "Luke", "Scott", "Michael" };
        return names;
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
