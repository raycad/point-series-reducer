package main.com.sdt.seriesreducer;

import java.util.List;

public class PointSeriesReducer {
    private static final double sqr(double x) {
        return Math.pow(x, 2);
    }

    private static final double distanceBetweenPoints(Point p1, Point p2) {
        return sqr(p1.getX() - p2.getX()) + sqr(p1.getY() - p2.getY());
    }

    private static final double distanceBetweenPoints(double vx, double vy, double wx, double wy) {
        return sqr(vx - wx) + sqr(vy - wy);
    }

    private static final double distanceToSegmentSquared(Point p, Point sp, Point ep) {
        final double l2 = distanceBetweenPoints(sp, ep);
        if (l2 == 0)
            return distanceBetweenPoints(p, sp);
        final double t = ((p.getX() - sp.getX()) * (ep.getX() - sp.getX()) + (p.getY() - sp.getY()) * (ep.getY() - ep.getY())) / l2;
        if (t < 0)
            return distanceBetweenPoints(p, sp);
        if (t > 1)
            return distanceBetweenPoints(p, ep);
        return distanceBetweenPoints(p.getX(), p.getY(), (sp.getX() + t * (ep.getX() - sp.getX())), (sp.getY() + t * (ep.getY() - sp.getY())));
    }

    private static final double perpendicularDistance(Point p, Point sp, Point ep) {
        return Math.sqrt(distanceToSegmentSquared(p, sp, ep));
    }

    public static final void douglasPeucker(List<Point> list, int s, int e, double epsilon, List<Point> resultList) {
        // Find the point with the maximum distance
        double dmax = 0;
        int index = 0;

        final int start = s;
        final int end = e-1;
        for (int i = start + 1; i < end; i++) {
            final Point p = list.get(i);
            // Start point
            final Point sp = list.get(start);
            // End point
            final Point ep = list.get(end);
            // Point

            final double d = perpendicularDistance(p, sp, ep);
            if (d > dmax) {
                index = i;
                dmax = d;
            }
        }
        // If max distance is greater than epsilon, recursively simplify
        if (dmax > epsilon) {
            // Recursive call
            douglasPeucker(list, s, index, epsilon, resultList);
            douglasPeucker(list, index, e, epsilon, resultList);
        } else {
            if ((end-start)>0) {
                resultList.add(list.get(start));
                resultList.add(list.get(end));
            } else {
                resultList.add(list.get(start));
            }
        }
    }
}
