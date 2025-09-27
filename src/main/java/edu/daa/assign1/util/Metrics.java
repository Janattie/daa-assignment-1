package edu.daa.assign1.util;

import java.util.concurrent.atomic.AtomicLong;

public class Metrics {
    private final AtomicLong comparisons = new AtomicLong();
    private final AtomicLong moves = new AtomicLong();
    private final AtomicLong allocations = new AtomicLong();
    private final AtomicLong maxDepth = new AtomicLong();
    private final ThreadLocal<Long> depth = ThreadLocal.withInitial(() -> 0L);

    public void reset() {
        comparisons.set(0); moves.set(0); allocations.set(0); maxDepth.set(0); depth.set(0L);
    }
    public void incCmp() { comparisons.incrementAndGet(); }
    public void incMove(long k) { moves.addAndGet(k); }
    public void incAlloc(long k) { allocations.addAndGet(k); }
    public long getComparisons() { return comparisons.get(); }
    public long getMoves() { return moves.get(); }
    public long getAllocations() { return allocations.get(); }
    public long getMaxDepth() { return maxDepth.get(); }

    public void enter() {
        long d = depth.get() + 1;
        depth.set(d);
        maxDepth.updateAndGet(prev -> Math.max(prev, d));
    }
    public void exit() { depth.set(depth.get() - 1); }
}
