import java.util.Random;

public class TestRandomizedQueue {

    static Random ranGen = new Random();

    private static void testEnqueDequeuSeq(){
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
            System.out.println("Enqueue: "+i);
        }
        for (int i = 0; i < 10 ; i++) {
            int item = queue.dequeue();
            System.out.println("Deuque item: "+item);
        }
        assert queue.isEmpty();
        System.out.println("Test Sequential Enqueue Dequeue passed\n\n");
    }

    private static void testRandomEnqueuDeque() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < 1000 ; i++) {
            int op = ranGen.nextInt();
            if (!queue.isEmpty() && (op % 2 != 0)) {
                int item = queue.dequeue();
                System.out.println("Item: "+item);
            } else{
                queue.enqueue(i);
            }

            // randomly enqueue
            if (queue.isEmpty()){
                int randEnque = ranGen.nextInt(20);
                for (int j = 0; j < randEnque ; j++) {
                    queue.enqueue(j);
                }
            }

        }

        System.out.println("Test Random Enqueue Dequeue Passed");


    }

    private  static void testIterator() {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < 20 ; i++) {
            queue.enqueue(i);
        }
        for(Integer i : queue) {
            System.out.println("first loop: "+i);
            for (Integer j : queue){
                System.out.println("Second loop: "+j);
            }
        }
        System.out.println("Test Iterator passed !");

    }



    public static void main (String [] args) {
        testEnqueDequeuSeq();
        testRandomEnqueuDeque();
        testIterator();
    }
}
