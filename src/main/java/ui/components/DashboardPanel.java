package main.java.ui.components;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setLayout(new BorderLayout());

        // Add placeholder content for the Dashboard page
        JLabel placeholderLabel = new JLabel("Dashboard Page", SwingConstants.CENTER);
        add(placeholderLabel, BorderLayout.CENTER);
    }
}
