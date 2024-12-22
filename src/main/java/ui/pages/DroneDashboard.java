package main.java.ui.pages;

import javax.swing.*;
import java.awt.*;

public class DroneDashboard extends JPanel {
    public DroneDashboard() {
        super(new BorderLayout());
        JLabel label = new JLabel("Drone Dashboard", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
