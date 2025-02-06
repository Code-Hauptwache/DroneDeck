package main.java.ui.components;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * A panel that displays a logo image and a loading progress bar.
 */
public class StartupLoadingScreen extends JPanel {
    private final JProgressBar progressBar;
    private final JLabel statusLabel;
    private Timer pulseTimer;

    /**
     * Creates a new StartupLoadingScreen panel.
     * This panel displays a logo image, a loading progress bar, and a status message.
     */
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
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setAlignmentX(CENTER_ALIGNMENT);
        progressBar.setMaximumSize(new Dimension(300, 25));

        // Add a status label below the progress bar
        statusLabel = new JLabel("Initializing...");
        statusLabel.setAlignmentX(CENTER_ALIGNMENT);
        Font currentFont = statusLabel.getFont();
        statusLabel.setFont(new Font(currentFont.getName(), Font.PLAIN, 14));

        // Add spacing and components to the center panel
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(logoLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(progressBar);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(statusLabel);
        centerPanel.add(Box.createVerticalGlue());

        // Start the pulse animation
        startPulseAnimation();

        // Add the center panel to the main panel
        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Updates the loading progress and status message.
     * @param progress The progress value (0-100)
     * @param status The status message to display
     */
    public void updateProgress(int progress, String status) {
        SwingUtilities.invokeLater(() -> {
            // Ensure progress stays within bounds
            int boundedProgress = Math.max(0, Math.min(100, progress));
            progressBar.setValue(boundedProgress);
            statusLabel.setText(status);
        });
    }

    /**
     * Sets the progress bar to indeterminate mode with a pulsing animation.
     */
    private void startPulseAnimation() {
        if (pulseTimer != null) {
            pulseTimer.stop();
        }

        progressBar.setIndeterminate(false);
        pulseTimer = new Timer(50, _ -> {
            int value = progressBar.getValue();
            if (value >= 100) {
                value = 0;
            }
            progressBar.setValue(value + 1);
        });
        pulseTimer.start();
    }

    /**
     * Stops the pulse animation.
     */
    public void stopPulseAnimation() {
        if (pulseTimer != null) {
            pulseTimer.stop();
        }
    }

    /**
     * Clean up resources when the loading screen is no longer needed.
     */
    public void cleanup() {
        stopPulseAnimation();
    }
}
