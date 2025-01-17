package main.java.ui.components;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.jthemedetecor.OsThemeDetector;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class PieChartExample {
    public static void main(String[] args) {
        // Set up FlatLaf look and feel
        FlatLaf.registerCustomDefaultsSource("main.java.ui.themes");

        final OsThemeDetector detector = OsThemeDetector.getDetector();
        final boolean isDarkThemeUsed = detector.isDark();
        if (isDarkThemeUsed) {
            FlatDarkLaf.setup();
        } else {
            FlatLightLaf.setup();
        }

        JFrame frame = new JFrame("Pie Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        PieChartPanel.Slice slice1 = new PieChartPanel.Slice(30, Color.GREEN, "Online");
        PieChartPanel.Slice slice2 = new PieChartPanel.Slice(20, Color.ORANGE, "Issue");
        PieChartPanel.Slice slice3 = new PieChartPanel.Slice(50, Color.RED, "Offline");

        PieChartPanel pieChartPanel = new PieChartPanel(Arrays.asList(slice1, slice2, slice3));
        frame.add(pieChartPanel);

        frame.setVisible(true);
    }
}