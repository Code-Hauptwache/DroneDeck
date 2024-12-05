package main.java.ui.components;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        setLayout(new BorderLayout());

        // Create a label to display drone data
        JLabel label = new JLabel("Drone Data will be displayed here", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        // Create a button to switch pages
        JButton button = new JButton("Go to Drone Page");
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.addActionListener(e -> showDronePage());
        add(button, BorderLayout.SOUTH);
    }

    private void showDronePage() {
        // Logic to switch to the drone page
        JOptionPane.showMessageDialog(this, "Switching to Drone Page...");
    }
}