package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        System.out.println(lst.toString());
        boolean changed = IntListExercises.squarePrimes(lst);
        System.out.println(lst.toString());
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
        System.out.println(changed);
    }

    @Test
    public  void test1(){
        IntList lst = IntList.of(2, 3, 6, 7, 13);
        System.out.println(lst);
        boolean changed = IntListExercises.squarePrimes(lst);
        System.out.println(lst);
        System.out.println(lst.toString());
        assertEquals("4 -> 9 -> 6 -> 49 -> 169", lst.toString());
        assertTrue(changed);
        System.out.println(changed);
    }
}
