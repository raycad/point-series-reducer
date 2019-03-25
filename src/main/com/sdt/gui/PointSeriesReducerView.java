package main.com.sdt.gui;

import main.com.sdt.seriesreducer.Point;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PointSeriesReducerView {
    private JButton reducePointsButton;
    public JPanel rootPanel;
    private DrawingPanel pointReductionPanel;
    private JButton clearButton;
    private DrawingPanel pointSelectionPanel;

    private ArrayList<Point> pointSeries = new ArrayList<Point>();

    public PointSeriesReducerView() {
        pointSelectionPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                Point p = new Point(mouseEvent.getX(), mouseEvent.getY());
                pointSeries.add(p);

                System.out.println("Point Series Size = " + pointSeries.size());
                pointSelectionPanel.invalidate();
            }
        });
//        pointSelectionPanel
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Clear the point series
                pointSeries.clear();
            }
        });

        initView();
        reducePointsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pointSelectionPanel.invalidate();
            }
        });
    }

    private void initView() {
        pointSelectionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 400, 1000));
        pointSelectionPanel.setPointList(pointSeries);

        pointReductionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 400, 1000));
    }

    private void refreshSelectionPanel() {

    }
}
