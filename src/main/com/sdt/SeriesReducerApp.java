package main.com.sdt;

import main.com.sdt.gui.PointSeriesReducerView;

import javax.swing.*;

public class SeriesReducerApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Series Reducer Demo");
        frame.setContentPane(new PointSeriesReducerView().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
