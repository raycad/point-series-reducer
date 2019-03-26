package main.com.sdt.seriesreducer;

import java.util.ArrayList;
import java.util.List;

public class PointSeriesReducer {
    /**
     * @brief Calculate the distance from a point to a line
     * @param pt is the point to calculate the distance
     * @param lineStart is the start point of the line
     * @param lineEnd is the end point of the line
     * @return the distance
     */
    private static double perpendicularDistance(Point pt, Point lineStart, Point lineEnd) {
        double dx = lineEnd.getX() - lineStart.getX();
        double dy = lineEnd.getY() - lineStart.getY();

        // Normalize
        double mag = Math.hypot(dx, dy);
        if (mag > 0.0) {
            dx /= mag;
            dy /= mag;
        }
        double pvx = pt.getX() - lineStart.getX();
        double pvy = pt.getY() - lineStart.getY();

        // Get dot product (project pv onto normalized direction)
        double pvDot = dx * pvx + dy * pvy;

        // Scale line direction vector and subtract it from pv
        double ax = pvx - pvDot * dx;
        double ay = pvy - pvDot * dy;

        return Math.hypot(ax, ay);
    }

    /**
     * @brief Reduce the number of points used to define its shape/curve using Ramer Douglas Peucker algorithm
     * @ref https://en.wikipedia.org/wiki/Ramer%E2%80%93Douglas%E2%80%93Peucker_algorithm
     * @param pointList is the input points series need to be reduced
     * @param startIndex is the start point index in the pointList need to be reduced
     * @param endIndex is the end point index in the pointList need to be reduced
     * @param tolerance is the distance dimension ε with ε > 0
     * @param resultList is the output points series after reducing
     */
    public static void ramerDouglasPeucker(List<Point> pointList, int startIndex, int endIndex, double tolerance, List<Point> resultList) {
        if (pointList.size() < 2)
            return;

        // Find the point with the maximum distance from line between the start and end
        double dmax = 0.0;
        int dmaxIndex = startIndex;
        for (int i = startIndex + 1; i < endIndex; ++i) {
            double d = perpendicularDistance(pointList.get(i), pointList.get(startIndex), pointList.get(endIndex));
            if (d > dmax) {
                dmaxIndex = i;
                dmax = d;
            }
        }

        // If max distance is greater than tolerance, recursively simplify
        if (dmax > tolerance) {
            List<Point> recResults1 = new ArrayList<>();
            List<Point> recResults2 = new ArrayList<>();
            List<Point> pointsOfFirstLine = pointList.subList(startIndex, dmaxIndex + 1);
            List<Point> pointsOfLastLine = pointList.subList(dmaxIndex, endIndex + 1);
            ramerDouglasPeucker(pointsOfFirstLine, recResults1, tolerance);
            ramerDouglasPeucker(pointsOfLastLine, recResults2, tolerance);

            // Build the result list
            resultList.addAll(recResults1.subList(startIndex, recResults1.size() - 1));
            resultList.addAll(recResults2);
            if (resultList.size() < 2)
                throw new RuntimeException("Problem assembling output");
        } else {
            // Just return start and end points
            resultList.clear();
            resultList.add(pointList.get(startIndex));
            resultList.add(pointList.get(endIndex));
        }
    }

    /**
     * @brief Reduce the number of points used to define its shape/curve using Ramer Douglas Peucker algorithm
     * @param pointList is the input points series need to be reduced
     * @param tolerance is the distance dimension ε with ε > 0
     * @param resultList is the output points series after reducing
     */
    public static void ramerDouglasPeucker(List<Point> pointList, List<Point> resultList, double tolerance) {
        ramerDouglasPeucker(pointList, 0, pointList.size() - 1, tolerance, resultList);
    }
}
