/**
 *
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private boolean[][] grid = null;
    private final int n;
    private int mTotalOpen = 0;
    private final WeightedQuickUnionUF unionSites;
    private boolean percolate = false;
    private final int virtualTopIdx = 0;
    private final int virtualBotIdx;


    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be greater than or equal to zero");
        // create n-by-n grid, with all sites blocked
        this.n = n;
        grid = new boolean[n + 1][n + 1];
        // initialized to all false
        for (int i = 1; i < n + 1; i++)
            for (int j = 1; j < n + 1; j++) {
                grid[i][j] = false;
            }
        // initialize union site with virtual top and bottom site
        this.unionSites = new WeightedQuickUnionUF(n * n + 2);
        virtualBotIdx = n * n + 1;
        // initialize to union virtual top and bottom to it item
        for (int i = 1; i < n + 1; i++) {
            int topIdx = calcElementPos(1, i);
            int botIdx = calcElementPos(n, i);
            unionSites.union(virtualBotIdx, botIdx);
            unionSites.union(virtualTopIdx, topIdx);
        }
    }

    /* calculate position of element which respect to the Union structure
   * return: >= 0 good index
   *         -1: not exist
   */
    private int calcElementPos(int row, int col) {
        if (row <= 0 || row >= n + 1) return -1;
        if (col <= 0 || col >= n + 1) return -1;
        return (row - 1) * n + (col);
    }

    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        if (row <= 0 || row >= n + 1)
            throw new IllegalArgumentException(" row number must be  positive and smaller than " + n);
        if (col <= 0 || col >= n + 1)
            throw new IllegalArgumentException(" row number must be  positive and smaller than " + n);

        // nothing to do here
        if (grid[row][col]) return;

        // mark the site to be opened
        grid[row][col] = true;
        int unionIndex = calcElementPos(row, col);
        if (unionIndex < 0)
            throw new IllegalArgumentException(" Invalid union index for valid item !! sth is really wrong here ");
        // union with neighbors if they are open
        // left
        int leftNRow = row;
        int leftNCol = col - 1;
        int leftUnionIndex = calcElementPos(leftNRow, leftNCol);
        // if requirements meet then union then
        if (leftUnionIndex >= 0 && grid[leftNRow][leftNCol]) {
            unionSites.union(unionIndex, leftUnionIndex);
        }
        // top
        int topNRow = row - 1;
        int topNCol = col;
        int topUnionIndex = calcElementPos(topNRow, topNCol);
        if (topUnionIndex >= 0 && grid[topNRow][topNCol]) {
            unionSites.union(unionIndex, topUnionIndex);
        }
        // right
        int rightNRow = row;
        int rightNCol = col + 1;
        int rightUnionIndex = calcElementPos(rightNRow, rightNCol);
        if (rightUnionIndex >= 0 && grid[rightNRow][rightNCol]) {
            unionSites.union(unionIndex, rightUnionIndex);
        }
        // down
        int downNRow = row + 1;
        int downNCol = col;
        int downUnionIndex = calcElementPos(downNRow, downNCol);
        if (downUnionIndex >= 0 && grid[downNRow][downNCol]) {
            unionSites.union(unionIndex, downUnionIndex);
        }
        // increase open site
        this.mTotalOpen += 1;
    }


    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        if (row <= 0 || row >= n + 1)
            throw new IllegalArgumentException(" row number must be  positive and smaller than " + n);
        if (col <= 0 || col >= n + 1)
            throw new IllegalArgumentException(" row number must be  positive and smaller than " + n);
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        // is site (row, col) full?
        // A full site is an open site that can be connected to an
        // open site in the top row via
        // a chain of neighboring (left, right, up, down) open sites
        if (row <= 0 || row >= n + 1)
            throw new IllegalArgumentException(" row number must be  positive and smaller than " + n);
        if (col <= 0 || col >= n + 1)
            throw new IllegalArgumentException(" row number must be  positive and smaller than " + n);

        // simple check if this site is not open then dont have to do any work
        if (!isOpen(row, col)) return false;

        // traverse top row to see if this connected to any
        int checkIndex = calcElementPos(row, col);
        if (row == n) {
            int component = unionSites.find(checkIndex);
            return unionSites.connected(virtualTopIdx, component);
        }
        return unionSites.connected(virtualTopIdx, checkIndex);
    }

    public int numberOfOpenSites() {
        return mTotalOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionSites.connected(virtualTopIdx, virtualBotIdx);
    }
}