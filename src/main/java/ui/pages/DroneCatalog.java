package main.java.ui.pages;

import javax.swing.*;
import java.awt.*;

public class DroneCatalog extends JPanel {
    public DroneCatalog() {
        super(new BorderLayout());
        JLabel label = new JLabel("Drone Catalog", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);
    }
}
