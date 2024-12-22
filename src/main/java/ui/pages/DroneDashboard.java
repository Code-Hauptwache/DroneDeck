package main.java.ui.pages;

import javax.swing.*;
import java.awt.*;

public class DroneDashboard extends JPanel {
    /**
     * The DroneDashboard class is a JPanel... (TODO)
     */
    public DroneDashboard() {
        // TODO: Implement the Drone Dashboard

        // This is a placeholder in orange for the drone dashboard
        super(new BorderLayout());
        JLabel label = new JLabel("Drone Dashboard (TODO)", SwingConstants.CENTER);
        label.setForeground(Color.ORANGE);
        add(label, BorderLayout.CENTER);
    }
}
