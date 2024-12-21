package main.java.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * The main panel of the application.
 * It contains the navigation bar, content panel, and pagination controls.
 * The navigation bar is at the top, the content panel is in the center,
 * and the pagination controls are at the bottom.
 */
public class MainPanel extends JPanel {
    public MainPanel() {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Add padding to the panel
        setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // Add the Navigation Bar (North)
        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar, BorderLayout.NORTH);

        // TODO: Add content panel (Center)

        // TODO: Add pagination controls (South)
    }
}
