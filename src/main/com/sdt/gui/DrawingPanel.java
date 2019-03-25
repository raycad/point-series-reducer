package main.com.sdt.gui;
import main.com.sdt.seriesreducer.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawingPanel extends JPanel {
    private ArrayList<Point> pointList;

    public void setPointList(ArrayList<Point> pointList) {
        this.pointList = pointList;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (pointList == null)
            return;

        System.out.println("Drawing Point Series Size = " + pointList.size());

        // Draw the polyline
        int pointCount = pointList.size();
        Point sp, ep;
        for (int i = 0; i < pointCount - 1; i++) {
            sp = pointList.get(i);
            ep = pointList.get(i+1);
            g.drawLine((int)sp.getX(), (int)sp.getY(), (int)ep.getX(), (int)ep.getY());
        }
    }
}
