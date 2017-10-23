import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import javax.sound.sampled.Line;
import java.util.Arrays;

public class BruteCollinearPoints {
    private Point [] points;
    private LineSegment[] segments;
    private static final int MIN_COL_POINTS = 4;


    public BruteCollinearPoints(Point[] points) {
        this.points = points;
        int segmentCount = 0;
        /**
         * if we have less than three points then nothing to do
         */
        if (this.points.length < MIN_COL_POINTS) {
            segments = new LineSegment[0];
        }
        else{
            // brute force try all points
            int size = this.points.length;
            // create array container for segments
            LineSegment[] segmentStore = new LineSegment[maxSegmentsPoss(this.points.length)];
            System.out.println("max possible collinears "+segmentStore.length);
            for (int i = 0; i <= size - 4; i++) {
                for (int j = i + 1; j <= size - 3; j++) {
                    for (int k = j + 1; k <= size - 2; k++) {
                        for (int l = k + 1; l <= size - 1 ; l++) {

                            // treat p0 as the origin
                            Point p = this.points[i];
                            Point q = this.points[j];
                            Point r = this.points[k];
                            Point s = this.points[l];
                            System.out.println(i+" "+j+" "+k+" "+l);
                            // check if they are collinear
                            if (isColinear(p,q,r,s)) {
                                segmentStore[segmentCount++] = findSegment(p,q,r,s);
                            }
                        }
                    }
                }
            }
            // copy to segments
            this.segments = new LineSegment[segmentCount];
            for (int i = 0; i < segmentCount ; i++) {
                segments[i] = segmentStore[i];
            }
        }

    }

    private boolean isColinear (Point p, Point q, Point r, Point s) {
        double slope_p_q = p.slopeTo(q);
        double slope_p_r = p.slopeTo(r);
        double slope_p_s = p.slopeTo(s);
        return (slope_p_q == slope_p_r) &&(slope_p_q == slope_p_s);
    }

    /**
     * find a largest segment between collinear points
     * Assume all points here are collinear
     * @param p
     * @param q
     * @param r
     * @param s
     * @return
     */
    private LineSegment findSegment(Point p, Point q, Point r, Point s) {
        Point [] points = new Point[] {p, q, r ,s};
        Arrays.sort(points);
        return new LineSegment(points[0], points[points.length - 1]);
    }

    private int maxSegmentsPoss(int size) {
        if (size > 0){
            int total = 1;
            int j = size;
            for (int i = 0; i < 4 ; i++) {
                total *=  j;
                j--;
            }
            return total;
        }

        return 0;
    }


    // finds all line segments containing 4 points
    public int numberOfSegments() {
        return this.segments.length;
    }

    // the number of line segments
    public LineSegment[] segments() {
        return this.segments;
    }

    /**
     * test client borrow from lecture
     * @param args
     */
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
