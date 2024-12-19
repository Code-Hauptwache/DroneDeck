package main.java.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import main.java.ui.components.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.util.Objects;

public class DroneDeck {

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
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }

        // Create the main frame
        JFrame frame = new JFrame("DroneDeck");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Load the logo image
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(DroneDeck.class.getResource("/DroneDeck_LogoTemp.png")));
        frame.setIconImage(logoIcon.getImage());

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Add Main Panel
        MainPanel mainPanel = new MainPanel();
        frame.add(mainPanel);

        // Make the frame visible
        frame.setVisible(true);
    }
}