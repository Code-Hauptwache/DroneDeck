package main.java.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import main.java.ui.components.MainPanel;

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
        // Set up FlatLaf look and feel
        FlatLaf.registerCustomDefaultsSource("main.java.ui.themes");
        FlatDarkLaf.setup();

        // Load Google Font
        try (InputStream is = DroneDeck.class.getResourceAsStream("/Lato-Bold.ttf")){
            Font font = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(is)).deriveFont(14f);
            UIManager.put("defaultFont", font);
        }
        catch (Exception e) {
            // TODO: Handle exception properly

            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }

        // Create the main frame
        JFrame frame = new JFrame("DroneDeck");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Load the logo image
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(DroneDeck.class.getResource("/DroneDeck_Logo.png")));
        frame.setIconImage(logoIcon.getImage());

        // Add the main panel
        MainPanel mainPanel = new MainPanel();
        frame.add(mainPanel);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);
    }
}
