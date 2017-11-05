public class TestBoard {


    public static void testGoalBoard() {
        int[][] blocks = {
                {1,2,3},
                {4,5,6},
                {7,8,0}
        };
        Board board = new Board(blocks);
        assert(board.isGoal());
        assert(!board.twin().isGoal());
        System.out.println("Board is goal test Passed");
    }

    public static void testBoardSanity () {
        int[][] blocks = {
                {8,1,3},
                {4,0,2},
                {7,6,5}
        };
        Board board = new Board(blocks);

        // check hamming values
        int hamming = board.hamming();
        System.out.println("Hamming is : " + hamming);
        assert(hamming == 5);
        System.out.println("Test Hamming Priority passed");


        // check manhattan values
        int manhattan = board.manhattan();
        System.out.println("Manhattan is : " + manhattan);
        assert (manhattan == 10);
        System.out.println("Test Manhattan Priority passed");

    }

    private static void testNeighborsAndTwin() {
        int[][] blocks = {
                {8,1,3},
                {4,0,2},
                {7,6,5}
        };
        int[][] blocks1 = {
                {8,1,3},
                {4,2,0},
                {7,6,5}
        };
        Board board = new Board(blocks);
        Board board1 = new Board(blocks1);
        Board twinBoard = board.twin();
        int twinN = twinBoard.dimension();
        int n = board.dimension();
        assert (n == twinN);
        System.out.println("Orig. Board: \n"+board);
        System.out.println("Twin Board: \n"+board.twin());


        // check goal
        assert(false == board.isGoal());
        System.out.println("Board is not goal: Passed");

        System.out.println("Neighbors board: ");
        for (Board b: board.neighbors()) {
            System.out.println(b);
        }
        System.out.println("Neighbors board1: ");
        for (Board b: board1.neighbors()) {
            System.out.println(b);
        }
        System.out.println("Twin and Neighbors test passed !!");
    }

    private static void testBoardEquals() {
        int[][] blocks1 = {
                {8,1,3},
                {4,2,0},
                {7,6,5}
        };
        int[][] blocks2 = {
                {8,1,3},
                {4,2,0},
                {7,6,5}
        };
        Board b1 = new Board(blocks1);
        Board b2 = new Board(blocks2);
        assert(b1.equals(b2));
        System.out.println("Test Board Equals Passed");
    }

    private static void testBoardNeigbors2() {
        int[][] blocks2 = {
                {1,0,8},
                {4,2,3},
                {7,6,5}
        };
        Board b1 = new Board(blocks2);
        System.out.println("Neigbors 2: \n");
        for(Board b : b1.neighbors()) {
            System.out.println(b);
        }
    }


    public static void main(String[] args) {
        testGoalBoard();
        testBoardSanity();
        testNeighborsAndTwin();
        testBoardEquals();
        testBoardNeigbors2();
    }
}
