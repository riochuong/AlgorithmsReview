public class TestSolver {

    private static void testSolvableSimpleCase() {
        int[][] blocks = {
                {0,1,3},
                {4,2,5},
                {7,8,6}
        };
        Solver solver = new Solver(new Board(blocks));
        System.out.println("Solvable: "+solver.isSolvable());
        assert (solver.isSolvable());
        System.out.println("MinMoves: "+solver.moves());
        System.out.println("Solution: \n");
        if (solver.isSolvable()) {
            for (Board b: solver.solution()){
                System.out.println(b);
            }
        }
    }

    private static void testUnsolvableCast() {
        int[][] blocks = {
                {2,2,3},
                {4,5,6},
                {8,7,0}
        };
        Solver solver = new Solver(new Board(blocks));
        System.out.println("Solvable: "+solver.isSolvable());
        assert(!solver.isSolvable());
        assert(solver.moves() < 0);
        assert(solver.solution() == null);

    }

    public static void main(String[] args) {
        //testSolvableSimpleCase();
        testUnsolvableCast();
    }
}
