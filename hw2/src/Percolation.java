import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private WeightedQuickUnionUF ufPrecolation;
    private WeightedQuickUnionUF ufFullness;
    boolean[][] grid;
    private int N;
    private int openSpace = 0;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Number of percolations must be greater than 0");
        }
        this.N = N;
        openSpace = 0;
        grid = new boolean[N][N];
        ufPrecolation = new WeightedQuickUnionUF(N * N + 2); // virtual top site & virtual bottom site
        ufFullness = new WeightedQuickUnionUF(N * N + 1); // virtual top site
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IllegalArgumentException("row and col must be between 0 and N");
        }
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col] = true;
        openSpace++;

        int index = xyTo1D(row, col);
        if (row - 1 >= 0 && grid[row - 1][col]) {
            ufPrecolation.union(xyTo1D(row - 1, col), index); // up
            ufFullness.union(xyTo1D(row - 1, col), index); // up
        }
        if (row + 1 < N && grid[row + 1][col]) {
            ufPrecolation.union(index, xyTo1D(row + 1, col)); // down
            ufFullness.union(index, xyTo1D(row + 1, col)); // down
        }
        if (col - 1 >= 0 && grid[row][col - 1]) {
            ufPrecolation.union(index, xyTo1D(row, col - 1)); // left
            ufFullness.union(index, xyTo1D(row, col - 1)); // left
        }
        if (col + 1 < N && grid[row][col + 1]) {
            ufPrecolation.union(index, xyTo1D(row, col + 1)); // right
            ufFullness.union(index, xyTo1D(row, col + 1)); // right
        }
        if (row == N - 1) {
            ufPrecolation.union(index, N * N + 1);
        }
        if (row == 0) {
            ufPrecolation.union(index, N * N);
            ufFullness.union(index, N * N);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IllegalArgumentException("row and col must be between 0 and N\"");
        }
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IllegalArgumentException("row and col must be between 0 and N");
        }
        return ufFullness.connected(xyTo1D(row, col), N * N);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSpace;
    }

    // does the system percolate?
    public boolean percolates() {
        return ufPrecolation.connected(N * N, N * N + 1);
    }

    private int xyTo1D(int row, int col) {
        return row * N + col;
    }

}
