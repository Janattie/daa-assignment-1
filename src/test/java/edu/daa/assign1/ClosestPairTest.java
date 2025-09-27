package edu.daa.assign1;

import edu.daa.assign1.algo.ClosestPair;
import edu.daa.assign1.algo.ClosestPair.Pt;
import edu.daa.assign1.util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {

    @Test
    void matchesNaiveOnSmallN() {
        Random rnd = new Random(7);
        for (int n : new int[]{2,3,5,10,50,200,500,2000}) {
            Pt[] pts = randomPoints(n, rnd);
            double fast = ClosestPair.distance(pts, new Metrics());
            double slow = ClosestPair.naive(pts);
            assertEquals(slow, fast, 1e-9, "n=" + n);
        }
    }

    @Test
    void handlesDuplicatesAndGrid() {

        Pt[] dup = { new Pt(1,1), new Pt(1,1), new Pt(2,2) };
        assertEquals(0.0, ClosestPair.distance(dup, new Metrics()), 1e-12);

        Pt[] grid = new Pt[9];
        int k = 0;
        for (int x = 0; x < 3; x++)
            for (int y = 0; y < 3; y++)
                grid[k++] = new Pt(x, y);
        assertEquals(1.0, ClosestPair.distance(grid, new Metrics()), 1e-12);
    }

    @Test
    void largeNFastOnly() {
        Random rnd = new Random(123);
        int n = 100_000;
        Pt[] pts = randomPoints(n, rnd);
        double d = ClosestPair.distance(pts, new Metrics());
        assertTrue(d >= 0.0);
    }

    private Pt[] randomPoints(int n, Random rnd) {
        Pt[] a = new Pt[n];
        for (int i = 0; i < n; i++) {
            double x = rnd.nextDouble() * 1e6;
            double y = rnd.nextDouble() * 1e6;
            a[i] = new Pt(x, y);
        }
        return a;
    }
}
