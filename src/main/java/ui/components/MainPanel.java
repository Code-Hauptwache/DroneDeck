package main.java.ui.components;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel() {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Add padding to the panel
        setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // Add the Navigation Bar
        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar, BorderLayout.NORTH);
    }
}
