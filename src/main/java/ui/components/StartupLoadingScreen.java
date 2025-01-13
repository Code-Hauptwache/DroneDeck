package main.java.ui.components;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * The StartupLoadingScreen class is a JPanel
 * that displays a loading message, logo image, and a progress bar
 * while the application is starting up.
 */
public class StartupLoadingScreen extends JPanel {
    public StartupLoadingScreen() {
        // Set layout for the panel
        setLayout(new BorderLayout());

        // Create a panel to hold the logo and loading components
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // Add the logo image to the center panel
        ImageIcon logoIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/DroneDeck_Logo.png")));
        Image scaledLogo = logoIcon.getImage().getScaledInstance(128, 128, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(CENTER_ALIGNMENT);

        // Add a loading progress bar below the logo
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setAlignmentX(CENTER_ALIGNMENT);
        progressBar.setMaximumSize(new Dimension(300, 25)); // Set size for the progress bar

        // Add spacing and components to the center panel
        centerPanel.add(Box.createVerticalGlue()); // Add vertical space
        centerPanel.add(logoLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add space between logo and progress bar
        centerPanel.add(progressBar);
        centerPanel.add(Box.createVerticalGlue()); // Add vertical space

        // Add the center panel to the main panel
        add(centerPanel, BorderLayout.CENTER);
    }
}
