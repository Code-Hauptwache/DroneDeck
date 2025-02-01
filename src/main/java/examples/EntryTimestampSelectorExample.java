package main.java.examples;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.jthemedetecor.OsThemeDetector;
import main.java.ui.components.EntryTimestampSelector;

import javax.swing.*;
import java.awt.*;

public class EntryTimestampSelectorExample {

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

        // Make sure we run on the Swing event dispatch thread
        SwingUtilities.invokeLater(() -> {
            // Create a simple frame
            JFrame frame = new JFrame("EntryTimestampSelector Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create an EntryTimestampSelector with 10 entries
            EntryTimestampSelector selector = new EntryTimestampSelector(100);

            // Add the component to the frame
            frame.getContentPane().add(selector, BorderLayout.CENTER);

            // Set up frame size and location
            frame.setSize(400, 100);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
