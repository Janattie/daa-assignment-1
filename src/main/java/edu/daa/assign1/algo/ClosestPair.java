package edu.daa.assign1.algo;

import edu.daa.assign1.util.Metrics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ClosestPair {

    public static class Pt {
        public final double x, y;
        public Pt(double x, double y) { this.x = x; this.y = y; }
    }


    public static double distance(Pt[] pts, Metrics m) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;

        List<Pt> Px = new ArrayList<>(pts.length);
        for (Pt p : pts) Px.add(p);
        Px.sort(Comparator.<Pt>comparingDouble(p -> p.x).thenComparingDouble(p -> p.y));

        List<Pt> Py = new ArrayList<>(pts.length);
        Py.addAll(Px);
        Py.sort(Comparator.<Pt>comparingDouble(p -> p.y).thenComparingDouble(p -> p.x));

        double best2 = solve(Px, Py, m);
        return Math.sqrt(best2);
    }


    private static double solve(List<Pt> Px, List<Pt> Py, Metrics m) {
        int n = Px.size();
        if (n <= 3) {
            if (m != null) m.enter();
            double best2 = brute2(Px, m);
            if (m != null) m.exit();
            return best2;
        }

        if (m != null) m.enter();

        int midIdx = n / 2;
        Pt midPt = Px.get(midIdx);
        double midX = midPt.x;


        List<Pt> Qx = Px.subList(0, midIdx);
        List<Pt> Rx = Px.subList(midIdx, n);


        List<Pt> Qy = new ArrayList<>(Qx.size());
        List<Pt> Ry = new ArrayList<>(Rx.size());
        for (Pt p : Py) {
            if (p.x < midX || (p.x == midX && Qy.size() < Qx.size())) Qy.add(p); else Ry.add(p);
        }

        double dl2 = solve(Qx, Qy, m);
        double dr2 = solve(Rx, Ry, m);
        double d2 = Math.min(dl2, dr2);


        List<Pt> Sy = new ArrayList<>();
        double d = Math.sqrt(d2);
        for (Pt p : Py) {
            if (Math.abs(p.x - midX) <= d) Sy.add(p);
        }


        for (int i = 0; i < Sy.size(); i++) {
            Pt a = Sy.get(i);
            for (int j = i + 1; j < Sy.size() && j <= i + 7; j++) {
                Pt b = Sy.get(j);
                double dx = a.x - b.x, dy = a.y - b.y;
                double dist2 = dx*dx + dy*dy;
                if (m != null) m.incCmp();
                if (dist2 < d2) { d2 = dist2; d = Math.sqrt(d2); }
            }
        }

        if (m != null) m.exit();
        return d2;
    }


    private static double brute2(List<Pt> pts, Metrics m) {
        double best2 = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.size(); i++) {
            Pt a = pts.get(i);
            for (int j = i + 1; j < pts.size(); j++) {
                Pt b = pts.get(j);
                double dx = a.x - b.x, dy = a.y - b.y;
                double dist2 = dx*dx + dy*dy;
                if (m != null) m.incCmp();
                if (dist2 < best2) best2 = dist2;
            }
        }
        return best2;
    }


    public static double naive(Pt[] pts) {
        double best2 = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x - pts[j].x, dy = pts[i].y - pts[j].y;
                double d2 = dx*dx + dy*dy;
                if (d2 < best2) best2 = d2;
            }
        }
        return Math.sqrt(best2);
    }
}
