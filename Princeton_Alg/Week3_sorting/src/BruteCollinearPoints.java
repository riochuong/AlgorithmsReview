import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private static final int MIN_COL_POINTS = 4;
    private List<LineSegment> segmentStore = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {

        int segmentCount = 0;
        /**
         * if we have less than three points then nothing to do
         */
        if (points.length < MIN_COL_POINTS) {
            return;
        }
        else{
            // brute force try all points
            int size = points.length;
            // create array container for segments

            //System.out.println("max possible collinears "+segmentStore.length);
            for (int i = 0; i <= size - 4; i++) {
                for (int j = i + 1; j <= size - 3; j++) {
                    for (int k = j + 1; k <= size - 2; k++) {
                        for (int l = k + 1; l <= size - 1 ; l++) {

                            // treat p0 as the origin
                            Point p = points[i];
                            Point q = points[j];
                            Point r = points[k];
                            Point s = points[l];
                            // check if they are collinear
                            if (isColinear(p,q,r,s)) {
                                //System.out.println(i+" "+j+" "+k+" "+l);
                                segmentStore.add(findSegment(p,q,r,s));
                            }
                        }
                    }
                }
            }
        }

    }

    private boolean isColinear (Point p, Point q, Point r, Point s) {
        double slope_p_q = p.slopeTo(q);
        double slope_p_r = p.slopeTo(r);
        double slope_p_s = p.slopeTo(s);
        boolean res =  (slope_p_q == slope_p_r) && (slope_p_q == slope_p_s);
        if (res){
            //System.out.println("Slopes "+slope_p_q+" "+slope_p_r+" "+slope_p_s);
            //System.out.println("p: "+p+" q: "+q+" r: "+r+" s:"+s);
            return true;
        }
        return false;
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


    // finds all line segments containing 4 points
    public int numberOfSegments() {
        return segmentStore.size();
    }

    // the number of line segments
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[segmentStore.size()];
        segmentStore.toArray(segments);
        return segments;
    }

}
