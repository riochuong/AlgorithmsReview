import edu.princeton.cs.algs4.Queue;


public class Board {
    // construct a board from an n-by-n array of blocks
    private int[][] mBlocks;
    private int n = 0;
    private int mHamming = 0;
    private int mManhattan = 0;
    private boolean mIsGoal = false;
    private int blankRow = 0;
    private int blankCol = 0;

    public Board(int[][] blocks) {
        if (blocks.length == 0 || blocks[0].length == 0) throw new IllegalArgumentException("Board cannot be empty");
        int numCols = blocks.length;
        int numRows = blocks[0].length;
        if (numCols != numRows) throw new IllegalArgumentException("number of rows must be same as number of columns");
        // copy array here
        n = numCols;
        mBlocks = blocks;//new int[this.n][this.n];
        boolean foundBlank = false;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                //mBlocks[i][j] = blocks[i][j];
                if (mBlocks[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    foundBlank = true;
                    break;
                }
                if (foundBlank) break;
            }
        }
        // calculate distances here
        mHamming = calcHammingDistance();
        mManhattan = calcManhattan();
        mIsGoal = isBoardGoal();
    }

    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return n;
    }                 // board dimension n

    // number of blocks out of place
    public int hamming() {
        return mHamming;
    }

    /**
     * calculate hamming distance
     *
     * @return hamming distance
     */
    private int calcHammingDistance() {
        int pos = 1;
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // skip empty block
                if (mBlocks[i][j] != 0 && mBlocks[i][j] != pos) {
                    hamming++;
                }
                // if pos reached one before max go back to zeros
                pos = (pos == (n * n - 1)) ? 0 : pos + 1;
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return mManhattan;
    }


    private int calcManhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = mBlocks[i][j];
                // quick hack to make this follow the fomula
                if (val == 0) continue;
                // calculate expected collumn and row
                int expRow = (val - 1) / n;
                int expCol = (val - 1) % n;
                if (expRow < 0) throw new IllegalStateException("goal row cannot be negative " + expRow);
                if (expCol > n) throw new IllegalStateException("goal row col cannot be bigger than size: " + expCol);
                //System.out.println("adding manhattan dist. of " + val + " is " + hammingAdd);
                manhattan += Math.abs(expRow - i) + Math.abs(expCol - j);
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return mIsGoal;
    }

    private boolean isBoardGoal() {
        int val = 1;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (i == (n - 1) && j == (n - 1)) {
                    if (mBlocks[i][j] != 0) return false;
                } else if (mBlocks[i][j] != val) {
                    return false;
                }
                val++;
            }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        // make a board copy
        int[][] twinBoardBlocks = new int[this.n][this.n];
        int count = 0;
        int r0 = 0;
        int c0 = 0;
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                twinBoardBlocks[i][j] = this.mBlocks[i][j];
                // check and swap item here
                if (count < 2 && mBlocks[i][j] != 0) {
                    count++;
                    // if we found non-zeros blocks
                    if (count == 2) {
                        int temp = twinBoardBlocks[i][j];
                        twinBoardBlocks[i][j] = twinBoardBlocks[r0][c0];
                        twinBoardBlocks[r0][c0] = temp;
                    } else {
                        r0 = i;
                        c0 = j;
                    }
                }
            }

        }

        // construct new board and return it
        return new Board(twinBoardBlocks);
    }

    // does this board equal y?
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board otherBoard = (Board) other;
        if (otherBoard.dimension() != this.dimension()) return false;
        // check each element of the board
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if (otherBoard.mBlocks[i][j] != this.mBlocks[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<>();
        // check all four neighbors
        int rowAbove = blankRow - 1;
        int rowBelow = blankRow + 1;
        int leftCol = blankCol - 1;
        int rightCol = blankCol + 1;
        // check each possibility neighbors
        if (rowAbove >= 0) {
            int[][] newNeighborBoard = copyBlockArr(mBlocks);
            swap(newNeighborBoard, blankRow, blankCol, rowAbove, blankCol);
            queue.enqueue(new Board(newNeighborBoard));
        }
        if (rowBelow <= (this.n - 1)) {
            int[][] newNeighborBoard = copyBlockArr(mBlocks);
            swap(newNeighborBoard, blankRow, blankCol, rowBelow, blankCol);
            queue.enqueue(new Board(newNeighborBoard));
        }
        if (leftCol >= 0) {
            int[][] newNeighborBoard = copyBlockArr(mBlocks);
            swap(newNeighborBoard, blankRow, blankCol, blankRow, leftCol);
            queue.enqueue(new Board(newNeighborBoard));
        }
        if (rightCol <= (this.n - 1)) {
            int[][] newNeighborBoard = copyBlockArr(mBlocks);
            swap(newNeighborBoard, blankRow, blankCol, blankRow, rightCol);
            queue.enqueue(new Board(newNeighborBoard));
        }
        return queue;
    }

    /* helper to swap to element */
    private void swap(int[][] arr, int r0, int c0, int r1, int c1) {
        int temp = arr[r0][c0];
        arr[r0][c0] = arr[r1][c1];
        arr[r1][c1] = temp;
    }

    private int[][] copyBlockArr(int[][] blocks) {
        int[][] tempNewBlocks = new int[blocks.length][blocks[0].length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                tempNewBlocks[i][j] = blocks[i][j];
            }
        }
        return tempNewBlocks;
    }


    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                sb.append(mBlocks[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}