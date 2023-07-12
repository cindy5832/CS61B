package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> a = new AListNoResizing<>();
        a.addLast(4);
        a.addLast(5);
        a.addLast(6);
        a.removeLast();
        BuggyAList<Integer> b = new BuggyAList<>();
        b.addLast(4);
        b.addLast(5);
        b.addLast(6);
        b.removeLast();
        boolean same = true;
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i))) {
                same = false;
                break;
            }
        }
        assertTrue(same);
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> bug = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                bug.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("size: " + size);
            }
            // optional test
//            if (L.size() > 0) {
//                L.getLast();
//                L.removeLast();
//                bug.getLast();
//                bug.removeLast();
//            }
        }
    }
}
