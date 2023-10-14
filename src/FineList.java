@SuppressWarnings({ "unchecked", "rawtypes" })
public class FineList<T> {
    private Node<T> head;

    public FineList() {
        head = new Node(Integer.MIN_VALUE, -1, 0);
        head.next = new Node(Integer.MAX_VALUE, -1, 0);
    }

    public boolean add(T item, int person, long time) {
        Node predecessor = head, current = predecessor.next;
        int key = item.hashCode();
        try {
            predecessor = head;
            predecessor.lock.lock();
            current = predecessor.next;
            current.lock.lock();
            while (current.key < key) {
                predecessor.lock.unlock();
                predecessor = current;
                current = current.next;
                current.lock.lock();
            }
            if (key == current.key) {
                return false;
            } else {
                Node node = new Node(item, person, time);
                node.next = current;
                predecessor.next = node;
                System.out.println(node.getName() + ": ADD ([" + person + "],[" + time + "])");
                return true;
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
        try {
            predecessor = head;
            predecessor.lock.lock();
            current = predecessor.next;
            current.lock.lock();
            while (current.key <= key) {
                if (item == current.item) {
                    predecessor.next = current.next;
                    return true;
                }
                predecessor.lock.unlock();
                predecessor = current;
                current = current.next;
                current.lock.lock();
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
