@SuppressWarnings({ "unchecked", "rawtypes" })

public class OptimisticListSynchronization<T> {
    private Message<T> head;

    public OptimisticListSynchronization() {
        head = new Message(Integer.MIN_VALUE, "Random", "JP", "JK");
        head.next = new Message(Integer.MAX_VALUE, "Random", "JG", "JK");
    }

    private boolean validate(Message predecessor, Message current) {
        Message messageNode = head;
        while (messageNode.hashCodeKey <= predecessor.hashCodeKey) {
            if (messageNode == predecessor) {
                return predecessor.next == current;
            }
            messageNode = messageNode.next;
        }
        return false;
    }

    public boolean write(T item, String message, String recipient, String sender) {
        Message predecessor = head, current = predecessor.next;
        int hashCodeKey = item.hashCode();
        while (current.hashCodeKey <= hashCodeKey) {
            if (item == current.item) {
                break;
            }
            predecessor = current;
            current = current.next;
        }
        try {
            predecessor.lock.lock();
            current.lock.lock();
            if (validate(predecessor, current)) {
                if (hashCodeKey == current.hashCodeKey) {
                    return false;
                } else {
                    Message messageNode = new Message(item, message, recipient, sender);
                    messageNode.next = current;
                    predecessor.next = messageNode;
                    // Message Sent
                    System.out.println("(SEND) " + Thread.currentThread().getName() + ": SUCCESSFUL");
                    return true;
                }
            } else {
                return false;
            }
        } finally {
            predecessor.lock.unlock();
            current.lock.unlock();
        }
    }

    public boolean read(T item) {
        Message predecessor = head, current = head.next;
        int hashCodeKey = item.hashCode();
        printMessageList();
        while (current.hashCodeKey <= hashCodeKey) {
            if (item == current.item) {
                break;
            }
            predecessor = current;
            current = current.next;
        }
        try {
            predecessor.lock.lock();
            current.lock.lock();
            if (validate(predecessor, current)) {
                if (current.item == item) {
                    predecessor.next = current.next;
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } finally {
            current.lock.unlock();
            predecessor.lock.unlock();
        }
    }

    private void printMessageList() {
        Message current = head.next;
        String outputString = "Message List:\n";
        if (current.getContent() != "Random") {
            outputString += "(RECEIVE) [" + current.threadName + "] { recipient: " + current.getRecipient() + ", sender: " + current.getSender() + ", Content: " + current.getContent() + " }\n";
        }
        while (current.next != null) {
            current = current.next;
            if (current.getSender() != "JK") {
                outputString += "(RECEIVE) [" + current.threadName + "] { recipient: " + current.getRecipient() + ", sender: " + current.getSender() + ", Content: " + current.getContent() + " }\n";
            }
        }
        System.out.println(outputString);
    }
}
