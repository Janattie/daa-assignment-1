package edu.daa.assign1;

import edu.daa.assign1.algo.QuickSort;
import edu.daa.assign1.util.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {
    @Test
    void sortsRandomAndAdversarial() {
        Random r = new Random(123);
        for (int n : new int[]{0,1,2,5,50,1000,20000}) {
            int[] a = r.ints(n).toArray();
            int[] b = a.clone(); java.util.Arrays.sort(b);
            Metrics m = new Metrics();
            QuickSort.sort(a, m);
            assertArrayEquals(b, a, "not sorted for n=" + n);
        }

        int[] sorted = java.util.stream.IntStream.range(0, 20000).toArray();
        int[] rev = java.util.stream.IntStream.iterate(19999, i -> i - 1).limit(20000).toArray();
        int[] eq = new int[20000];

        QuickSort.sort(sorted, new Metrics());
        QuickSort.sort(rev, new Metrics());
        QuickSort.sort(eq, new Metrics());

        assertTrue(isSorted(sorted) && isSorted(rev) && isSorted(eq));
    }

    @Test
    void stackDepthTypicallyLogN() {
        int n = 1 << 16; // 65536
        int[] a = java.util.stream.IntStream.range(0, n).toArray();
        Metrics m = new Metrics();
        QuickSort.sort(a, m);
        int bound = 2 * (int)Math.floor(Math.log(n)/Math.log(2)) + 10;
        assertTrue(m.getMaxDepth() <= bound, "depth=" + m.getMaxDepth() + " bound=" + bound);
    }

    private boolean isSorted(int[] x) {
        for (int i = 1; i < x.length; i++) if (x[i-1] > x[i]) return false;
        return true;
    }
}
