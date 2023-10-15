// - **A Writer**: Sends new messages, destined for a specific client, to the server and logs the message in the clients chat.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class WriterThread extends Thread {
    private String sender;
    private Server server;
    Lock lock;

    public WriterThread(Client client, Server server, Lock l) {
        this.lock = l;
        this.sender = client.clientName;
        this.server = server;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            String randomMessage = this.getMessage();
            String recipient = this.server.getRecipient(sender);
            Message messageObject = new Message(Thread.currentThread(), randomMessage, recipient, this.sender);
            System.out.println("(SEND) [" + Thread.currentThread().getName() + "]: { sender:[" + this.sender + "] , recipient:[" + recipient + "]}");
            this.server.setMessage(this.server.getRecipient(this.sender), messageObject);
            try {
                long time = (int) Math.floor(Math.random() * (1000 + 1 - 100 + 1) + 100);
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
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
        return this.getShuffledList(greetings, 2)[2];
    }

    public String[] getShuffledList(String[] array, int numTimesEach) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < numTimesEach; i++) {
            list.addAll(Arrays.asList(array));
        }
        Collections.shuffle(list);
        return list.toArray(new String[0]);
    }
}