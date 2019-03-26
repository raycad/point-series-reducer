package main.com.sdt.gui;

import main.com.sdt.seriesreducer.Point;
import main.com.sdt.seriesreducer.PointSeriesReducer;

import javax.swing.*;
import java.awt.*;
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
    private JTextField toleranceTextField;
    private JCheckBox showCoordinatesCb;

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

                refreshView();
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
                refreshView();
            }
        });

        showCoordinatesCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                boolean isCoorShown = showCoordinatesCb.isSelected();
                pointsCreationPanel.setShowCoordinates(isCoorShown);
                pointsReductionPanel.setShowCoordinates(isCoorShown);

                refreshView();
            }
        });

        initView();
    }

    private void initView() {
        pointsCreationPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 400, 1000));
        pointsCreationPanel.setPointList(pointsCreationList);

        pointsReductionPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 400, 1000));
        pointsReductionPanel.setPointList(pointsReductionList);

        toleranceTextField.setText("30");

        refreshCreationLabel();
        refreshReductionLabel();
    }

    private void refreshCreationLabel() {
        pointsCreationLabel.setText("Click here to draw a curve... (" + pointsCreationList.size() + " points)");
    }

    private void refreshReductionLabel() {
        pointsReductionLabel.setText("Curve after reducing points... (" + pointsReductionList.size() + " points)");
    }

    private void reducePoints() {
        // Get tolerance value
        double tolerance = Double.parseDouble(toleranceTextField.getText());

        pointsReductionList.clear();
        pointSeriesReducer.ramerDouglasPeucker(pointsCreationList, tolerance, pointsReductionList);
    }

    private void refreshView() {
        reducePoints();

        // Refresh labels
        refreshCreationLabel();
        refreshReductionLabel();

        // Redraw the Reduction Panel
        rootPanel.repaint();
    }
}
