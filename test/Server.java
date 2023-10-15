// - *Server*: This class will represent the Client Chat Server.
import java.util.*;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Server extends Thread {
    private Map<String, List<Message>> clientMessages;
    private AndersonLock lock;
    private volatile Client[] serverClients;
    Queue<String> myReceivers;
    public int numMessages;
    String[] clientNames;

    public Server(Client[] clients, String[] clientNames, int numMessages) {
        this.serverClients = clients;
        this.clientMessages = new HashMap<>();
        this.lock = new AndersonLock();
        this.numMessages = numMessages;
        this.clientNames = clientNames;
        this.myReceivers = null;
    }

    public void run() {
        this.myReceivers = this.generateReceiver(this.numMessages + 1);

        for (int i = 0; i < this.numMessages; i++) {
            for (Client myClient : this.serverClients) {
                // Sender and Receiver Details
                String sender = myClient.getClientName();
                String recipient = this.myReceivers.poll();
                while (sender == recipient) {
                    // Making Sure the User doesn't send a Message to Oneself
                    this.myReceivers.add(recipient);
                    recipient = this.myReceivers.poll();
                }
                if (sender != null && recipient != null) {
                    // Setting the Start Time for the Request
                    long startTime = System.currentTimeMillis();
                    String sentMessage = this.getMessage();
                    // Sending the Message to the Recipient
                    myClient.write(sentMessage, recipient, sender);
                    Message messageObject = new Message(Thread.currentThread(), sentMessage, recipient, sender);
                    // Adding the Message to the Map for the Receiver
                    this.setMessage(recipient, messageObject);
                    try {
                        long time = (int) Math.floor(Math.random() * (1000 + 1 - 100 + 1) + 100);
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Reading the Message
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

        System.out.println("\nEveryone went Offline in the Server..\n");
        System.out.println("Server Highlights:");
        this.printAllReceivedMessages();
        System.out.println("\nServer is Offline..");
    }

    public void setMessage(String clientName, Message message) {
        lock.lock();
        try {
            if (!this.clientMessages.containsKey(clientName)) {
                this.clientMessages.put(clientName, new ArrayList<>());
            }
            this.clientMessages.get(clientName).add(message);
        } finally {
            lock.unlock();
        }
    }

    public Message getMessage(String clientName) {
        lock.lock();
        try {
            List<Message> messages = this.clientMessages.get(clientName);
            if (messages != null && !messages.isEmpty()) {
                return messages.get(0);
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void printAllReceivedMessages() {
        // Print the clientMessages map
        for (Map.Entry<String, List<Message>> entry : this.clientMessages.entrySet()) {
            System.out.println("Messages SENT to " + entry.getKey() + ":");
            for (Message message : entry.getValue()) {
                System.out.println(message.messageDetails());
            }
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
