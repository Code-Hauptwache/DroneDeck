package main.java.examples;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.jthemedetecor.OsThemeDetector;
import main.java.DroneDeck;
import main.java.ui.components.EntryTimestampSelector;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Objects;

/**
 * Example class that demonstrates how to create a JFrame with an EntryTimestampSelector.
 */
public class EntryTimestampSelectorExample {

    /**
     * Main method to run the example.
     */
    public static void main(String[] args) {
        // Load Google Font
        try (InputStream is = DroneDeck.class.getResourceAsStream("/Lato-Bold.ttf")) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(is)).deriveFont(16f);
            UIManager.put("defaultFont", font);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to load the font. The application will use the default font.", "Font Load Error", JOptionPane.ERROR_MESSAGE);
        }

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

            // Add a wrapper panel
            JPanel wrapper = new JPanel();

            // Create an EntryTimestampSelector with 10 entries
            EntryTimestampSelector selector = new EntryTimestampSelector(99999);

            // Add the component to the frame
            wrapper.add(selector);
            frame.add(wrapper);

            // Set up frame size and location
            frame.setSize(400, 100);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
