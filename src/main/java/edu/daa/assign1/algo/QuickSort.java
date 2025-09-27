package edu.daa.assign1.algo;

import edu.daa.assign1.util.Metrics;
import java.util.concurrent.ThreadLocalRandom;


public class QuickSort {
    private static final int CUTOFF = 24;

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length <= 1) return;
        qs(a, 0, a.length - 1, m);

        insertion(a, 0, a.length - 1, m);
    }

    private static void qs(int[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            if (hi - lo + 1 <= CUTOFF) { insertion(a, lo, hi, m); return; }

            int pivotIdx = lo + ThreadLocalRandom.current().nextInt(hi - lo + 1);
            int mid = partition(a, lo, hi, pivotIdx, m);


            if (m != null) m.enter();
            if ((mid - 1) - lo < hi - (mid + 1)) {
                qs(a, lo, mid - 1, m);
                if (m != null) m.exit();
                lo = mid + 1;
            } else {
                qs(a, mid + 1, hi, m);
                if (m != null) m.exit();
                hi = mid - 1;
            }
        }
    }


    private static int partition(int[] a, int lo, int hi, int pivotIndex, Metrics m) {
        int pivot = a[pivotIndex];
        swap(a, pivotIndex, hi, m);
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (m != null) m.incCmp();
            if (a[j] < pivot) { swap(a, i, j, m); i++; }
        }
        swap(a, i, hi, m);
        return i;
    }

    private static void insertion(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            int x = a[i]; int j = i - 1;
            while (j >= lo) {
                if (m != null) m.incCmp();
                if (a[j] <= x) break;
                a[j + 1] = a[j];
                if (m != null) m.incMove(1);
                j--;
            }
            a[j + 1] = x;
            if (m != null) m.incMove(1);
        }
    }

    private static void swap(int[] a, int i, int j, Metrics m) {
        if (i == j) return;
        int t = a[i]; a[i] = a[j]; a[j] = t;
        if (m != null) m.incMove(3);
    }
}
