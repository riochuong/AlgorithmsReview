import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    /* declare head tail to help add and remove
    *  item faster from both ends of the deque
    * */
    private Node<Item> head = null;
    private Node<Item> tail = null;
    private int size = 0;

    public Deque() {

    }                           // construct an empty deque

    public boolean isEmpty() { return size == 0; }// is the deque empty?

    public int size() { return size; } // return the number of items on the deque

    public void addFirst(Item item) {

        // make sure item is not null
        if (item == null){ throw new IllegalArgumentException("Item cannot be nukk"); }

        if (isEmpty()) {
            initDeque(item);
        }
        else {
            // add item to current list
            Node<Item> newNode = new Node<>();
            newNode.data = item;
            newNode.next = head;
            newNode.prev = null; // new head
            head.prev = newNode; // connect to new head
            head = newNode;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null){ throw new IllegalArgumentException("Item cannot be nukk"); }

        if (isEmpty()){
            initDeque(item);
        } else {
            Node <Item> newNode = new Node<>();
            // make tail to point to the new node and reassign tail next to new tail
            newNode.data = item;
            newNode.next = null;
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }           // add the item to the end

    public Item removeFirst() {
        if (isEmpty()) { throw new NoSuchElementException("Deque is empty. Should Check empty current"); }
        Item res = null;
        Node<Item> removedNode = head;
        // dercrease the size
        if (size == 1) {
            head = null;
            tail = null;
            removedNode.next = null;
            removedNode.prev = null;
            res = removedNode.data;
        }
        else{
            head = removedNode.next;
            removedNode.next = null;
            head.prev = null;
            res = removedNode.data;
        }
        size--;
        return res;
    }                // remove and return the item from the front

    public Item removeLast()  {
        if (isEmpty()) { throw new NoSuchElementException("Deque is empty. Should Check empty current"); }
        Item res = null;
        Node<Item> removedNode = tail;
        // dercrease the size
        if (size == 1) {
            head = null;
            tail = null;
            removedNode.next = null;
            removedNode.prev = null;
            res = removedNode.data;
        }
        else{
            tail = removedNode.prev;
            tail.next = null;
            removedNode.prev = null;
            res = removedNode.data;
        }
        size--;
        return res;
    }               // remove and return the item from the end

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }       // return an iterator over items in order from front to end

    public static void main(String[] args) {

    }   // unit testing (optional)


    private class Node<Item> {
        Node<Item> next;
        Node<Item> prev;
        Item data;
    }

    /**
     * helper to avoid repeat code to initialize deque on addfirst and addlast
     * @param item
     */
    private void initDeque(Item item ){
        head = new Node<>();
        head.data = item;
        head.next = null;
        head.prev = null;
        tail = head;
    }

    private class DequeIterator implements Iterator<Item> {

        private Node<Item> current = head;

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Item next() {
            if (current == null) { throw new NoSuchElementException("no more element in the deque"); }
            Item item = current.data;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported operation");
        }
    }

}