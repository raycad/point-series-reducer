package main.com.sdt.gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DrawingPanel extends JPanel {
    private List<double[]> pointList;
    private boolean showCoordinates = false;

    public void setPointList(List<double[]> pointList) {
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
        for (double[] p : pointList) {
            x = (int) (p[0] - size / 2);
            y = (int) (p[1] - size / 2);
            g.drawOval(x, y, size, size);

            if (showCoordinates)
                g.drawString(String.format("(%d, %d)", (int) p[0], (int) p[1]), (int) (x - 5 * size / 2), (int) (p[1] - size));
        }

        // Draw lines
        double[] sp, ep;
        g.setColor(Color.BLUE);
        for (int i = 0; i < pointCount - 1; i++) {
            sp = pointList.get(i);
            ep = pointList.get(i+1);
            g.drawLine((int)sp[0], (int)sp[1], (int)ep[0], (int)ep[1]);
        }
    }
}
