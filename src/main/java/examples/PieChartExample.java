package main.java.examples;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.jthemedetecor.OsThemeDetector;
import main.java.ui.components.PieChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Example of a pie chart using PieChartPanel.
 */
public class PieChartExample {
    /**
     * Main method to run the example.
     */
    public static void main(String[] args) {
        // Set up FlatLaf look and feel
        FlatLaf.registerCustomDefaultsSource("main.java.ui.themes");

        OsThemeDetector detector = OsThemeDetector.getDetector();
        boolean isDarkThemeUsed = detector.isDark();
        if (isDarkThemeUsed) {
            FlatDarkLaf.setup();
        } else {
            FlatLightLaf.setup();
        }

        JFrame frame = new JFrame("Pie Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);

        // Create slices with colors and labels
        PieChartPanel.Slice slice1 = new PieChartPanel.Slice(22, Color.GREEN,  "Online");
        PieChartPanel.Slice slice2 = new PieChartPanel.Slice(5, Color.ORANGE, "Issue");
        PieChartPanel.Slice slice3 = new PieChartPanel.Slice(13, Color.RED,    "Offline");

        // Add them to the PieChartPanel
        PieChartPanel pieChartPanel = new PieChartPanel(Arrays.asList(slice1, slice2, slice3));
        frame.add(pieChartPanel);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
