import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class PercolationTest {
    private int[][] getState(int N, Percolation p) {
        int[][] state = new int[5][5];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = open + full;
            }
        }
        return state;
    }
    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(5);
        int[][] openSites = {
                {0, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };
        int[][] expectedState = {
                {0, 3, 0, 0, 0},
                {3, 3, 0, 0, 0},
                {3, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 1, 0, 0, 0}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void testBackWash() {
        int N = 3;
        Percolation p = new Percolation(3);
        p.open(0,0);
        p.open(1,0);
        p.open(2,0);
        p.open(2,2);
        System.out.println(p.isFull(2,2));
    }

    @Test
    public void test() {
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(5 * 5);
        uf.union(12, 13);
        uf.union(13, 14);
        uf.union(14, 19);
        System.out.println(uf.count());
    }

}
