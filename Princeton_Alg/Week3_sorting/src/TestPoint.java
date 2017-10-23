import java.util.Comparator;

public class TestPoint {


    private static void testPointComparator() {
        Point p0 = new Point(2 ,3 );
        Point p1 = new Point(1,5);
        Point p2 = new Point (3, 6);
        Point p3 = new Point(2,9);
        Comparator<Point> comp = p0.slopeOrder();
        assert (comp.compare(p1,p2) < 0);
        assert (comp.compare(p3, p2) > 0);
        System.out.println("Test Point Comparator passed !!");
    }

    private static void testHorizontalLine() {
        Point p0 = new Point(19000,10000);
        Point p1 = new Point(18000,10000);
        Point p2 = new Point(19000,10000);
        Point p3 = new Point(32000,10000);
        Point p4 = new Point(1234, 5678);
        double slope_0_1 = p0.slopeTo(p1);
        double slope_0_4 = p0.slopeTo(p4);
        assert (slope_0_1 != slope_0_4);
        System.out.println(slope_0_1+" "+slope_0_4);
        System.out.println("Test Points in same Horizontal Line passed !!!");
    }


    private static void testPointSanity() {
        Point p0 = new Point(1,1);
        Point p1 = new Point(2,2);
        Point p2 = new Point(2,1);
        Point p3 = new Point(3,1);

        double slope01 = p0.slopeTo(p1);
        double slope02 = p0.slopeTo(p2);
        double slope21 = p1.slopeTo(p2);
        double slopeDup = p2.slopeTo(p2);

        assert (slope01 > 0);
        assert(slope02 == 0.0);
        assert(slope21 == Double.POSITIVE_INFINITY);
        assert(slopeDup == Double.NEGATIVE_INFINITY);
        System.out.println("Test Point Sanity Passed !!! ");
    }


    public static void main(String[] args) {
        testPointSanity();
        testPointComparator();
        testHorizontalLine();
    }
}
