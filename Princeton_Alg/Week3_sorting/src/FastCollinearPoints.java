import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {
    private static final int MIN_SEG_COUNT = 4;
    private List<LineSegment> segmentStore = new ArrayList<>();


    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        int size = points.length;
        Point[] pointsDup = new Point[points.length];

        // copy references of the array
        for (int i = 0; i < points.length; i++) {
            pointsDup[i] = points[i];
        }

        // check to see which scenarios we are in
        if (size < MIN_SEG_COUNT) {
            return;
        }
        else {
            // go through each points to find segments

            // sort array by distance first to avoid duplicate
            Arrays.sort(points);
            for (int i = 0; i < points.length; i++) {
                Point p = points[i];
                Comparator<Point> comparator = p.slopeOrder();
                // sort the array with the new comparator
                Arrays.sort(pointsDup, i, size, comparator);
                assert (p == pointsDup[i]);
                // go through three points with the slide windows
                LineSegment currSegment = null;
                // the first item should be the point p which we dont need to compare
                int j = i + 1;
                while (j <= size - 3) {
                    Point q = pointsDup[j];
                    Point r = pointsDup[j + 1];
                    Point s = pointsDup[j + 2];
                    // check if the point is collinear
                    if (isColinear(p, q, r, s)) {
                        // System.out.println(p+" "+q+" "+r+" "+s);
                        segmentStore.add(findSegment(p, q, r, s));
                        j += 3;
                    }
                    else {
                        j++;
                    }

                }
            }

        }
    }

    private boolean isColinear(Point p, Point q, Point r, Point s) {
        double slopePq = p.slopeTo(q);
        double slopePr = p.slopeTo(r);
        double slopePs = p.slopeTo(s);
        return (slopePq == slopePr) && (slopePq == slopePs);
    }

    /**
     * find a largest segment between collinear points
     * Assume all points here are collinear
     *
     * @param p
     * @param q
     * @param r
     * @param s
     * @return
     */
    private LineSegment findSegment(Point p, Point q, Point r, Point s) {
        Point[] points = new Point[]{p, q, r, s};
        Arrays.sort(points);
        return new LineSegment(points[0], points[points.length - 1]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentStore.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[segmentStore.size()];
        segmentStore.toArray(segments);
        return segments;
    }


}