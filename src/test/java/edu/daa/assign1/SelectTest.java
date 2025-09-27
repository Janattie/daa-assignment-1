package edu.daa.assign1;

import edu.daa.assign1.algo.Select;
import edu.daa.assign1.util.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class SelectTest {

    @Test
    void matchesArraysSortKth_onRandomTrials() {
        Random rnd = new Random(42);
        for (int trial = 0; trial < 100; trial++) {
            int n = 1000 + rnd.nextInt(4000);
            int[] a = rnd.ints(n).toArray();
            int k = rnd.nextInt(n);

            int[] b = a.clone();
            java.util.Arrays.sort(b);
            int expected = b[k];

            Metrics m = new Metrics();
            int got = Select.select(a, k, m);

            assertEquals(expected, got, "trial=" + trial + " k=" + k + " n=" + n);

            int bound = 10 * (int)Math.floor(Math.log(n) / Math.log(2)) + 50;
            assertTrue(m.getMaxDepth() <= bound, "depth=" + m.getMaxDepth());
        }
    }

    @Test
    void worksOnEdgeCases() {

        assertEquals(7, Select.select(new int[]{7}, 0, new Metrics()));
        assertEquals(2, Select.select(new int[]{2,2}, 0, new Metrics()));
        assertEquals(2, Select.select(new int[]{2,2}, 1, new Metrics()));


        int[] x = {5,5,5,5,5,5};
        assertEquals(5, Select.select(x.clone(), 0, new Metrics()));
        assertEquals(5, Select.select(x.clone(), 5, new Metrics()));


        int[] sorted = java.util.stream.IntStream.range(0, 1000).toArray();
        assertEquals(123, Select.select(sorted.clone(), 123, new Metrics()));

        int[] rev = java.util.stream.IntStream.iterate(999, i -> i - 1).limit(1000).toArray();
        assertEquals(123, Select.select(rev.clone(), 123, new Metrics()));
    }

    @Test
    void throwsOnBadInput() {
        assertThrows(IllegalArgumentException.class, () -> Select.select(new int[]{}, 0, new Metrics()));
        assertThrows(IllegalArgumentException.class, () -> Select.select(new int[]{1,2,3}, -1, new Metrics()));
        assertThrows(IllegalArgumentException.class, () -> Select.select(new int[]{1,2,3}, 3, new Metrics()));
    }
}
