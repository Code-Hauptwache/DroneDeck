package main.java.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * The StartupLoadingScreen class is a JPanel
 * that displays a loading message while the application is starting up.
 */
public class StartupLoadingScreen extends JPanel {
    public StartupLoadingScreen() {
        setLayout(new BorderLayout());
        JLabel loadingLabel = new JLabel("Loading, please wait...", SwingConstants.CENTER);
        add(loadingLabel, BorderLayout.CENTER);
    }
}