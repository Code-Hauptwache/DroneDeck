package main.java.ui;

import com.formdev.flatlaf.FlatDarkLaf;
import main.java.ui.components.MainPanel;

import javax.swing.*;
import java.util.Objects;

public class DroneDeck {

    public static void main(String[] args) {
        // Set up FlatLaf look and feel
        FlatDarkLaf.setup();

        // Create the main frame
        JFrame frame = new JFrame("DroneDeck");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Load the logo image
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(DroneDeck.class.getResource("/DroneDeck_LogoTemp.png")));
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