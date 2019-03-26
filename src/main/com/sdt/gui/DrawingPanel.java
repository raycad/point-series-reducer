package main.com.sdt.gui;
import main.com.sdt.seriesreducer.Point;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DrawingPanel extends JPanel {
    private List<Point> pointList;
    private boolean showCoordinates = false;

    public void setPointList(List<Point> pointList) {
        this.pointList = pointList;
    }

    public void setShowCoordinates(boolean showCoordinates) {
        this.showCoordinates = showCoordinates;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (pointList == null)
            return;

        // Draw the polyline
        int pointCount = pointList.size();
        int size = 10;

        int x, y;
        // Draw points
        g.setColor(Color.RED);
        for (Point p : pointList) {
            x = (int) (p.getX() - size / 2);
            y = (int) (p.getY() - size / 2);
            g.drawOval(x, y, size, size);

            if (showCoordinates)
                g.drawString(String.format("(%d, %d)", (int) p.getX(), (int) p.getY()), (int) (x - 5 * size / 2), (int) (p.getY() - size));
        }

        // Draw lines
        Point sp, ep;
        g.setColor(Color.BLUE);
        for (int i = 0; i < pointCount - 1; i++) {
            sp = pointList.get(i);
            ep = pointList.get(i+1);
            g.drawLine((int)sp.getX(), (int)sp.getY(), (int)ep.getX(), (int)ep.getY());
        }
    }
}
