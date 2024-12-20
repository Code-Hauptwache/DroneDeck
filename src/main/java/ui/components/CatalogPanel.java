package main.java.ui.components;

import javax.swing.*;
import java.awt.*;

public class CatalogPanel extends JPanel {

    public CatalogPanel() {
        setLayout(new BorderLayout());

        // Add placeholder content for Catalog page
        JLabel placeholderLabel = new JLabel("Catalog Page", SwingConstants.CENTER);
        add(placeholderLabel, BorderLayout.CENTER);
    }
}
