import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

public class Solver {
    private int minMoves = 0;
    private boolean isSolvable = false;
    private Queue<Board> solutionQ = null;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<Board> minInitalPQ = new MinPQ<>(getManhattanComparator());
        MinPQ<Board> minTwinPQ = new MinPQ<>(getManhattanComparator());
        Board prevInitialBoard = null;
        Board prevTwinInitialBoard = null;
        // queue for solution
        Queue<Board> solnQ = new Queue<>();
        // get the twin board
        Board twinBoard = initial.twin();
        int initalMoves = 0;
        // insert initial board
        minInitalPQ.insert(initial);
        minTwinPQ.insert(twinBoard);
        boolean solFound = false;
        // insert all neighbors to min Pq
        while(!solFound){
            // check if the min board is solution
            Board initialNextMinBoard = minInitalPQ.delMin();
            Board twinNextMinBoard = minTwinPQ.delMin();
           // System.out.println("twin board: \n"+twinNextMinBoard);
            solnQ.enqueue(initialNextMinBoard);
            // if we have found solution
            if (initialNextMinBoard.isGoal()) {
                this.minMoves = initalMoves;
                this.isSolvable = true;
                solFound = true;
                solutionQ = solnQ;
            } else if (twinNextMinBoard.isGoal()) {
                this.minMoves = -1;
                this.isSolvable = false;
                solFound = true;
                solutionQ = null;
            }
            else{
                // keep checking for solution
                initalMoves++;
                minInitalPQ = new MinPQ<>(getManhattanComparator());
                minTwinPQ = new MinPQ<>(getManhattanComparator());
                // insert neighbors to inital board
                for(Board b : initialNextMinBoard.neighbors()) {
                    // skip b if it's the same as predecessor
                    if (prevInitialBoard != null && prevInitialBoard.equals(b)) continue;
                    minInitalPQ.insert(b);
                }
                // insert neighbor to twin boards
                for(Board b : twinNextMinBoard.neighbors()) {
                    if (prevTwinInitialBoard != null && prevTwinInitialBoard.equals(b)) continue;
                    minTwinPQ.insert(b);
                }
                prevInitialBoard = initialNextMinBoard;
                prevTwinInitialBoard = twinNextMinBoard;
            }
        }
    }

    private Comparator<Board> getHammingCompartor() {
        return (o1, o2) -> {
          if (o1.hamming() < o2.hamming()) return -1;
          if (o1.hamming() > o2.hamming()) return 1;
          return 0;
        };
    }

    private Comparator<Board> getManhattanComparator() {
        return (o1, o2) -> {
            if (o1.manhattan() < o2.manhattan()) return -1;
            if (o1.manhattan() > o2.manhattan()) return 1;
            return 0;
        };
    }
    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return minMoves;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solutionQ;
    }
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    } // solve a slider puzzle (given below)
}