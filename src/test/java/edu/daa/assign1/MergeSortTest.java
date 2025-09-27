package edu.daa.assign1;

import edu.daa.assign1.algo.MergeSort;
import edu.daa.assign1.util.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {
    @Test
    void sortsRandomAndAdversarial() {
        Random r = new Random(7);

        for (int n : new int[]{0,1,2,5,31,1000,8000}) {
            int[] a = r.ints(n).toArray();
            int[] b = a.clone(); java.util.Arrays.sort(b);
            Metrics m = new Metrics();
            MergeSort.sort(a, m);
            assertArrayEquals(b, a, "not sorted for n=" + n);
        }

        int[] sorted = java.util.stream.IntStream.range(0, 5000).toArray();
        int[] rev = java.util.stream.IntStream.iterate(4999, i -> i - 1).limit(5000).toArray();
        int[] eq = new int[5000];
        MergeSort.sort(sorted, new Metrics());
        MergeSort.sort(rev, new Metrics());
        MergeSort.sort(eq, new Metrics());
        assertTrue(isSorted(sorted) && isSorted(rev) && isSorted(eq));
    }
    private boolean isSorted(int[] a) { for (int i=1;i<a.length;i++) if (a[i-1]>a[i]) return false; return true; }
}
