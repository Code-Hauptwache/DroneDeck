package main.java.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import main.java.ui.components.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Objects;

public class DroneDeck {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DroneDeck::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Set up FlatLaf look and feel
        FlatLaf.registerCustomDefaultsSource("main.java.ui.themes");
        FlatDarkLaf.setup();

        // Load Google Font
        try (InputStream is = DroneDeck.class.getResourceAsStream("/Lato-Bold.ttf")) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(is)).deriveFont(14f);
            UIManager.put("defaultFont", font);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the frame
        JFrame frame = new JFrame("DroneDeck");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load and set the logo image as the window icon
        ImageIcon logoIcon = new ImageIcon(
                Objects.requireNonNull(DroneDeck.class.getResource("/DroneDeck_LogoTemp.png"))
        );
        frame.setIconImage(logoIcon.getImage());

        // Create and add the main panel
        MainPanel mainPanel = new MainPanel();
        frame.add(mainPanel, BorderLayout.CENTER);

        // Call pack() so that components are laid out properly
        frame.pack();

        // Enforce the minimum size (the user cannot shrink the window below this)
        frame.setMinimumSize(new Dimension(800, 600));

        // Center the frame on the screen and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
