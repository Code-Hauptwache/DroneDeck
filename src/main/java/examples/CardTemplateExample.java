package main.java.examples;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.jthemedetecor.OsThemeDetector;
import main.java.ui.components.CardTemplate;

import javax.swing.*;
import java.awt.*;

/**
 * Example class that demonstrates how to create a JFrame with a CardTemplate.
 */
public class CardTemplateExample {

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

        // Make sure we run on the Swing event dispatch thread
        SwingUtilities.invokeLater(() -> {
            // Create a simple frame
            JFrame frame = new JFrame("CardTemplate Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create a content component for the card
            JLabel contentLabel = new JLabel("This is the content of the card.");
            contentLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Create a CardTemplate with title, subtitle, and content
            CardTemplate card = new CardTemplate("Card Title", "Card Subtitle", contentLabel);

            // Add the card to the frame
            frame.getContentPane().add(card, BorderLayout.CENTER);

            // Set up frame size and location
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}