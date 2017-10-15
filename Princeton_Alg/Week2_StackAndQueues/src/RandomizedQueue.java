import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    /* declare head tail to help add and remove
   *  item faster from both ends of the deque
   * */
    private Node<Item> head = null;
    private Node<Item> tail = null;
    private int size = 0;

    public RandomizedQueue() {

    }                           // construct an empty deque

    public boolean isEmpty() { return size == 0; }// is the deque empty?

    public int size() { return size; } // return the number of items on the deque

    public void enqueue(Item item) {

        // make sure item is not null
        if (item == null){ throw new IllegalArgumentException("Item cannot be nukk"); }

        if (isEmpty()) {
            initQueue(item);
        }
        else {
            // add item to current list
            Node<Item> newNode = new Node<>();
            newNode.data = item;
            newNode.next = null;
            newNode.prev = tail;
            tail.next = newNode; // connect to new head
            tail = newNode;
        }
        size++;
    }

    /**
     * return a random data without removing it
     * @return
     */
    public Item sample() {
        if (isEmpty()) { throw new NoSuchElementException("Queue is empty. Should Check empty current"); }
        int selIndex = selectRandomIndex();
        return getDataAtIndex(selIndex);
    }

    private Item getDataAtIndex(int index) {
        Node<Item> iter = head;
        for (int i = 0; i < index ; i++) {
            iter = iter.next;
        }
        return iter.data;
    }


    public Item dequeue() {
        if (isEmpty()) { throw new NoSuchElementException("Queue is empty. Should Check empty current"); }
        int selIndex = selectRandomIndex();
        return removeItemAtIndex(selIndex);
    }                // remove and return the item from the front

    private Item removeItemAtIndex(int index) {
        Node<Item> iter = head;
        // special case of len 1
        if (size == 1){
            Item ret = head.data;
            head.data = null;
            head = null;
            tail = null;
            size--;
            return ret;
        }

        //case remove first item
        if (index == 0) {
            Node<Item> next = head.next;
            head.prev = null;
            head.next = null;
            Item data = head.data;
            head = next;
            size--;
            return data;
        }

        //case remove tail
        if (index == size - 1) {
            Node<Item> prev = tail.prev;
            prev.next = null;
            tail.prev = null;
            Item data = tail.data;
            tail = prev;
            size--;
            return data;
        }

        // any item in the middle
        for (int i = 0; i < index ; i++) {
            iter = iter.next;
        }
        Node<Item> prev = iter.prev;
        prev.next = iter.next;
        iter.next.prev = prev;
        iter.prev = null;
        iter.next = null;
        Item ret = iter.data;
        iter.data = null;
        size--;
        return ret;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }       // return an iterator over items in order from front to end

    public static void main(String[] args) {

    }   // unit testing (optional)


    private int selectRandomIndex() {
        if (size == 0) return -1;
        return StdRandom.uniform(size);
    }

    private class Node<Item> {
        Node<Item> next;
        Node<Item> prev;
        Item data;
    }

    /**
     * helper to avoid repeat code to initialize deque on addfirst and addlast
     * @param item
     */
    private void initQueue(Item item ){
        head = new Node<>();
        head.data = item;
        head.next = null;
        head.prev = null;
        tail = head;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int[] traverseOder = StdRandom.permutation(size);
        private int currIdx = 0;

        @Override
        public boolean hasNext() {
            if (traverseOder.length == 0) { return false; }
            if (currIdx == traverseOder.length) { return  false; }
            return true;
        }

        @Override
        public Item next() {
            if (currIdx >= traverseOder.length) { throw new NoSuchElementException("Iterator is in wrong state"); }
            return getDataAtIndex(traverseOder[currIdx++]);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported operation");
        }
    }



}