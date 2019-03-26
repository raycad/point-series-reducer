package main.com.sdt.seriesreducer;

import java.util.ArrayList;
import java.util.List;

public class PointSeriesReducer {
    /**
     * @brief Calculate the distance from a point to a line
     * @param pt is the point (an array of double x, y coordinates) to calculate the distance
     * @param lineStart is the start point (an array of double x, y coordinates) of the line
     * @param lineEnd is the end point (an array of double x, y coordinates) of the line
     * @return the distance
     */
    private static double perpendicularDistance(double[] pt, double[] lineStart, double[] lineEnd) {
        double dx = lineEnd[0] - lineStart[0];
        double dy = lineEnd[1] - lineStart[1];

        // Normalize
        double mag = Math.hypot(dx, dy);
        if (mag > 0.0) {
            dx /= mag;
            dy /= mag;
        }
        double pvx = pt[0] - lineStart[0];
        double pvy = pt[1] - lineStart[1];

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
    public static void ramerDouglasPeucker(List<double[]> pointList, int startIndex, int endIndex, double tolerance, List<double[]> resultList) {
        if (endIndex - startIndex == 1) { // 2 points
            resultList.add(pointList.get(startIndex));
            resultList.add(pointList.get(endIndex));
            return;
        } else if (endIndex - startIndex == 0) { // 1 point
            resultList.add(pointList.get(startIndex));
            return;
        }

        // Find the point with the maximum distance from line between the start and end
        double dmax = 0.0, d = 0.0;
        int dmaxIndex = startIndex;
        for (int i = startIndex + 1; i < endIndex; ++i) {
            d = perpendicularDistance(pointList.get(i), pointList.get(startIndex), pointList.get(endIndex));
            if (d > dmax) {
                dmaxIndex = i;
                dmax = d;
            }
        }

        // If max distance is greater than tolerance, recursively simplify
        if (dmax > tolerance) {
            List<double[]> recResults1 = new ArrayList<double[]>();
            List<double[]> recResults2 = new ArrayList<double[]>();
            List<double[]> pointsOfFirstLine = pointList.subList(startIndex, dmaxIndex + 1);
            List<double[]> pointsOfLastLine = pointList.subList(dmaxIndex, endIndex + 1);

            // Divide and conquer and call recursively
            ramerDouglasPeucker(pointsOfFirstLine, tolerance, recResults1);
            ramerDouglasPeucker(pointsOfLastLine, tolerance, recResults2);

            // Build the result list
            resultList.addAll(recResults1.subList(0, recResults1.size() - 1));
            resultList.addAll(recResults2);
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
     * @param tolerance is the distance dimension ε with ε > 0. Tolerance for GPS should be 0.015
     * @param resultList is the output points series after reducing
     */
    public static void ramerDouglasPeucker(List<double[]> pointList, double tolerance, List<double[]> resultList) {
        ramerDouglasPeucker(pointList, 0, pointList.size() - 1, tolerance, resultList);
    }
}
