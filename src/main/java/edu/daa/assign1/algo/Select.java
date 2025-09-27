package edu.daa.assign1.algo;

import edu.daa.assign1.util.Metrics;


public class Select {

    public static int select(int[] a, int k, Metrics m) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("empty array");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        return selectRange(a, 0, a.length - 1, k, m);
    }

    private static int selectRange(int[] a, int lo, int hi, int k, Metrics m) {
        while (true) {
            if (lo == hi) return a[lo];
            int pivot = medianOfMedians(a, lo, hi, m);
            int mid = partitionAroundValue(a, lo, hi, pivot, m);

            if (m != null) m.incCmp();
            if (k == mid) return a[mid];


            int leftSize = mid - lo;
            int rightSize = hi - mid;
            if (k < mid) {
                if (m != null) m.enter();
                if (leftSize <= rightSize) {

                    hi = mid - 1;
                    if (m != null) m.exit();
                } else {

                    hi = mid - 1;
                }
            } else {
                if (m != null) m.enter();
                if (rightSize <= leftSize) {
                    lo = mid + 1;
                    if (m != null) m.exit();
                } else {
                    lo = mid + 1;
                }
            }
        }
    }


    private static int medianOfMedians(int[] a, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        if (n <= 5) {
            insertion(a, lo, hi, m);
            return a[lo + n / 2];
        }
        int write = lo;
        for (int g = lo; g <= hi; g += 5) {
            int gLo = g;
            int gHi = Math.min(g + 4, hi);
            insertion(a, gLo, gHi, m);
            int med = gLo + (gHi - gLo) / 2;
            swap(a, write++, med, m);
        }

        int midIndex = lo + ( (write - lo) / 2 );
        return selectRange(a, lo, write - 1, midIndex, m);
    }


    private static int partitionAroundValue(int[] a, int lo, int hi, int pivotVal, Metrics m) {

        int pivotIndex = lo;
        while (pivotIndex <= hi) {
            if (m != null) m.incCmp();
            if (a[pivotIndex] == pivotVal) break;
            pivotIndex++;
        }
        swap(a, pivotIndex, hi, m);


        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (m != null) m.incCmp();
            if (a[j] < pivotVal) { swap(a, i, j, m); i++; }
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
