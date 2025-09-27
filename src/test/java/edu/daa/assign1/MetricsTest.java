package edu.daa.assign1;

import edu.daa.assign1.util.Metrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MetricsTest {
    @Test void countsWorkCorrectly() {
        Metrics m = new Metrics();
        m.incCmp(); m.incMove(3); m.incAlloc(10);
        assertEquals(1, m.getComparisons());
        assertEquals(3, m.getMoves());
        assertEquals(10, m.getAllocations());
    }
    @Test void depthTracksCorrectly() {
        Metrics m = new Metrics();
        m.enter(); m.enter(); m.exit(); m.exit();
        assertEquals(2, m.getMaxDepth());
    }
}
