import java.util.Random;

public class TestDeque {

    private static Random randGen = new Random();

    private static void testAddRemoveFirst() {
        System.out.println("Test Remove First");
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(10);
        deque.addFirst(9);
        System.out.println("Total size "+deque.size());
        int size = deque.size();
        for (int i = 0; i < size ; i++) {
            System.out.print(deque.removeFirst()+" ");
        }
        System.out.println();
    }

    private static void testAddFirstRemoveLast() {
        System.out.println("Test Remove Last");
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(10);
        deque.addFirst(9);
        System.out.println("Total size "+deque.size());
        int size = deque.size();
        for (int i = 0; i < size ; i++) {
            System.out.print(deque.removeLast()+" ");
        }
        System.out.println();
    }


    private static void testAddRemoveRandom() {
        System.out.println("Test Remove Last");
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(10);
        deque.addFirst(9);
        deque.addLast(19);
        deque.addLast(21);
        deque.addFirst(10);
        System.out.println("Total size "+deque.size());
        while(!deque.isEmpty()) {
            if (randGen.nextInt() % 2 == 0) {
                System.out.println("Remove Last : "+deque.removeLast()+" ");
            }
            else {
                System.out.println("Remove First : "+deque.removeFirst()+" ");
            }
        }
        System.out.println();
    }

    private static void testEmptyQueue() {
        Deque<Integer> deque = new Deque<>();
        // add first remove last
        deque.addFirst(1);
        deque.removeLast();
        assert(deque.isEmpty());
        // add last remove first
        deque.addLast(10);
        deque.removeFirst();
        assert(deque.isEmpty());
        System.out.println("Test Empty Dequeue Passed");
    }


    public static void main (String[] args) {
        testAddRemoveFirst();
        testAddFirstRemoveLast();
        testAddRemoveRandom();
        testEmptyQueue();
    }
}
