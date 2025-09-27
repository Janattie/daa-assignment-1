package edu.daa.assign1.algo;

import edu.daa.assign1.util.Metrics;

public class MergeSort {
    private static final int CUTOFF = 24;

    public static void sort(int[] a, Metrics m) {
        if (a == null || a.length <= 1) return;
        int[] buf = new int[a.length];
        if (m != null) m.incAlloc(a.length);
        sort(a, 0, a.length - 1, buf, m);
    }

    private static void sort(int[] a, int lo, int hi, int[] buf, Metrics m) {
        if (hi - lo + 1 <= CUTOFF) { insertion(a, lo, hi, m); return; }

        if (m != null) m.enter();
        int mid = lo + ((hi - lo) >>> 1);
        sort(a, lo, mid, buf, m);
        sort(a, mid + 1, hi, buf, m);

        if (m != null) m.incCmp();
        if (a[mid] <= a[mid + 1]) { if (m != null) m.exit(); return; }

        merge(a, lo, mid, hi, buf, m);
        if (m != null) m.exit();
    }

    private static void merge(int[] a, int lo, int mid, int hi, int[] buf, Metrics m) {
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            if (m != null) m.incCmp();
            if (a[i] <= a[j]) buf[k++] = a[i++]; else buf[k++] = a[j++];
            if (m != null) m.incMove(1);
        }
        while (i <= mid) { buf[k++] = a[i++]; if (m != null) m.incMove(1); }
        while (j <= hi)  { buf[k++] = a[j++]; if (m != null) m.incMove(1); }
        for (int t = lo; t <= hi; t++) a[t] = buf[t];
        if (m != null) m.incMove(hi - lo + 1);
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
}
