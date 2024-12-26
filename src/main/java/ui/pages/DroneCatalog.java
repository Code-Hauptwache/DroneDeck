package main.java.ui.pages;

import javax.swing.*;
import java.awt.*;

public class DroneCatalog extends JPanel {
    /**
     * The DroneCatalog class is a JPanel... (TODO)
     */
    public DroneCatalog() {
        // TODO: Implement the Drone Catalog

        // This is a placeholder in orange for the drone catalog
        super(new BorderLayout());
        JLabel label = new JLabel("Drone Catalog (TODO)", SwingConstants.CENTER);
        label.setForeground(Color.ORANGE);
        add(label, BorderLayout.CENTER);
    }
}