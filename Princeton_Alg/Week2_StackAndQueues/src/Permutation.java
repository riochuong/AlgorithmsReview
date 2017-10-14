import edu.princeton.cs.algs4.StdIn;

public class Permutation {


    public static void main(String [] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while(!StdIn.isEmpty()) {
               queue.enQueue(StdIn.readString());
        }
        // uniformly print item out
        for (int i = 0; i < k; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
