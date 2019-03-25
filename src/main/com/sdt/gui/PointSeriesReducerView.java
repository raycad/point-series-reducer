package main.com.sdt.gui;

import main.com.sdt.seriesreducer.Point;
import main.com.sdt.seriesreducer.PointSeriesReducer;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PointSeriesReducerView {
    public JPanel rootPanel;
    private DrawingPanel pointsReductionPanel;
    private DrawingPanel pointsCreationPanel;
    private JButton clearButton;
    private JButton reducePointsButton;
    private JLabel pointsCreationLabel;
    private JLabel pointsReductionLabel;

    private List<Point> pointsCreationList = new ArrayList<Point>();
    private List<Point> pointsReductionList = new ArrayList<Point>();

    private PointSeriesReducer pointSeriesReducer = new PointSeriesReducer();

    public PointSeriesReducerView() {
        pointsCreationPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                Point p = new Point(mouseEvent.getX(), mouseEvent.getY());
                pointsCreationList.add(p);

                refreshCreationLabel();

                // Redraw the Creation Panel
                rootPanel.repaint();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Clear the point series
                pointsCreationList.clear();
                pointsReductionList.clear();

                refreshCreationLabel();
                refreshReductionLabel();

                rootPanel.repaint();
            }
        });

        reducePointsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pointsReductionList.clear();
                pointSeriesReducer.douglasPeucker(pointsCreationList, 0, pointsCreationList.size(), 3.0d, pointsReductionList);

                refreshReductionLabel();
                // Redraw the Reduction Panel
                rootPanel.repaint();
            }
        });

        initView();
    }

    private void initView() {
        pointsCreationPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 400, 1000));
        pointsCreationPanel.setPointList(pointsCreationList);

        pointsReductionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 400, 1000));
        pointsReductionPanel.setPointList(pointsReductionList);

        refreshCreationLabel();
        refreshReductionLabel();
    }

    private void refreshCreationLabel() {
        pointsCreationLabel.setText("Click here to draw polyline... (" + pointsCreationList.size() + " points)");
    }

    private void refreshReductionLabel() {
        pointsReductionLabel.setText("Polyline after reducing points... (" + pointsReductionList.size() + " points)");
    }
}
