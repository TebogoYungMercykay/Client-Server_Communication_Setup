import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
@SuppressWarnings({ "unchecked", "rawtypes" })


public class OptimisticListSynchronization<T> {
    private Message<T> head;

    public OptemisticList() {
        head = new Message(Integer.MIN_VALUE, -1, 0);
        head.next = new Message(Integer.MAX_VALUE, -1, 0);
    }

    private boolean validate(Message predecessor, Message current) {
        Message node = head;
        while (node.key <= predecessor.key) {
            if (node == predecessor) {
                return predecessor.next == current;
            }
            node = node.next;
        }
        return false;
    }

    public boolean add(T item, int person, long time) {
        Message predecessor = head, current = predecessor.next;
        int key = item.hashCode();
        while (current.key <= key) {
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
                if (key == current.key) {
                    return false;
                } else {
                    Message node = new Message(item, person, time);
                    node.next = current;
                    predecessor.next = node;
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

    public boolean remove(T item) {
        Message predecessor = head, current = head.next;
        int key = item.hashCode();
        while (current.key <= key) {
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
}
