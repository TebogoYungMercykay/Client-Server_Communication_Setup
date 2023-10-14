@SuppressWarnings({ "unchecked", "rawtypes" })
public class OptemisticList<T> {
    private Node<T> head;

    public OptemisticList() {
        head = new Node(Integer.MIN_VALUE, -1, 0);
        head.next = new Node(Integer.MAX_VALUE, -1, 0);
    }

    private boolean validate(Node predecessor, Node current) {
        Node node = head;
        while (node.key <= predecessor.key) {
            if (node == predecessor) {
                return predecessor.next == current;
            }
            node = node.next;
        }
        return false;
    }

    public boolean add(T item, int person, long time) {
        Node predecessor = head, current = predecessor.next;
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
                    Node node = new Node(item, person, time);
                    node.next = current;
                    predecessor.next = node;
                    System.out.println(node.getName() + ": ADD ([" + person + "],[" + time + "])");
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
        Node predecessor = head, current = head.next;
        int key = item.hashCode();
        printList();
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

    private void printList() {
        Node current = head.next;
        String output = "List: ";
        if (current.person != -1) {
            output += "[" + current.tName + " (P-" + current.person + ", " + ((current.time - (System.currentTimeMillis() - current.startTime) > 0) ? (current.time - (System.currentTimeMillis() - current.startTime)) : 0) + "ms)]";
        }
        while (current.next != null) {
            current = current.next;
            if (current.person != -1) {
                output += " -> [" + current.tName + " (P-" + current.person + ", " + ((current.time - (System.currentTimeMillis() - current.startTime) > 0) ? (current.time - (System.currentTimeMillis() - current.startTime)) : 0) + "ms)]";
            }
        }
        System.out.println(output);
    }
}
