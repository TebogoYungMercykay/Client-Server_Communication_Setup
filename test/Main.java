// - *Main*: This is the Testing Main for the Server.

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

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
        TTASLock[] myLocks = new TTASLock[numClients];
        for (int i = 0; i < numClients; i++) {
            myLocks[i] = new TTASLock();
        }

        Server serverObject = new Server(serverClients, numMessages);
        List<Thread> threads = new ArrayList<>();
        for (int j = 0; j < numMessages; j++) {
            for (int index : generateRandom(numClients)) {
                WriterThread myWriter = new WriterThread(clients[index], serverObject, myLocks[index]);
                threads.add(myWriter);
                ReaderThread myReader = new ReaderThread(clients[index], serverObject, myLocks[index]);
                threads.add(myReader);
            }
        }

        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
    }

    public static int[] generateRandom(int k) {
        int[] array = IntStream.range(0, k).toArray();
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            int randomIndex = rand.nextInt(array.length);
            int temp = array[i];
            array[i] = array[randomIndex];
            array[randomIndex] = temp;
        }
        return array;
    }
}
