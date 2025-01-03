package main.java.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.jthemedetecor.OsThemeDetector;
import main.java.services.ApiTokenStore.ApiTokenStoreService;
import main.java.ui.TokenPanels.InputTokenPanel;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Objects;

public class DroneDeck {

    /**
     * The main entry point of the application.
     * It sets up the FlatLaf look and feel, loads a Google Font,
     * creates the main frame, and makes it visible.
     *
     * @param args the command line arguments
     *             (not used in this application)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DroneDeck::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Set up FlatLaf look and feel
        setupFlatLaf();

        // Check if an API token is available
        if (!ApiTokenStoreService.IsTokenAvailable()) {
            // Request the API token from the user
            requestToken();
        }
    }

    private static void setupFlatLaf() {
        // Set up FlatLaf look and feel
        FlatLaf.registerCustomDefaultsSource("main.java.ui.themes");

        final OsThemeDetector detector = OsThemeDetector.getDetector();
        final boolean isDarkThemeUsed = detector.isDark();
        if (isDarkThemeUsed) {
            //The OS uses a dark theme
            FlatDarkLaf.setup();
        } else {
            //The OS uses a light theme
            FlatLightLaf.setup();
        }

        // Load Google Font
        try (InputStream is = DroneDeck.class.getResourceAsStream("/Lato-Bold.ttf")) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(is)).deriveFont(16f);
            UIManager.put("defaultFont", font);
        }
        catch (Exception e) {
            // TODO: Handle exception properly

            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static void setupMainPanel() {
        // Create the frame
        JFrame frame = new JFrame("DroneDeck");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load the logo image
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(DroneDeck.class.getResource("/DroneDeck_Logo.png")));
        Image scaledLogo = logoIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        frame.setIconImage(scaledLogo);

        // Create and add the main panel
        MainPanel mainPanel = new MainPanel();
        frame.add(mainPanel, BorderLayout.CENTER);

        // Call pack() so that components are laid out properly
        frame.pack();

        // Enforce the minimum size (the user cannot shrink the window below this)
        frame.setMinimumSize(new Dimension(1100, 900));

        // Center the frame on the screen and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void requestToken() {
        // Create the frame
        JFrame frame = new JFrame("DroneDeck - API Token");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Load the logo image
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(DroneDeck.class.getResource("/DroneDeck_Logo.png")));
        Image scaledLogo = logoIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
        frame.setIconImage(scaledLogo);

        // Create and add the token panel
        InputTokenPanel tokenPanel = new InputTokenPanel(e -> {

            setupMainPanel();

            frame.setVisible(false);

            SwingUtilities.invokeLater(frame::dispose);

        });
        frame.getContentPane().add(tokenPanel, BorderLayout.CENTER);

        // Call pack() so that components are laid out properly
        frame.pack();

        // Enforce the minimum size (the user cannot shrink the window below this)
        frame.setMinimumSize(new Dimension(400, 300));

        // Center the frame on the screen and make it visible
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
