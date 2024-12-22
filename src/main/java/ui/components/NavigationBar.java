package main.java.ui.components;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class NavigationBar extends JComponent {

    private JButton selectedButton;

    public NavigationBar(MainPanel mainPanel) {
        setLayout(new GridLayout(1, 2, 0, 0));

        // "Catalog" button
        JButton catalogButton = createButton("Catalog", mainPanel, MainPanel.PAGE_CATALOG);
        add(catalogButton);

        // "Dashboard" button
        JButton dashboardButton = createButton("Dashboard", mainPanel, MainPanel.PAGE_DASHBOARD);
        add(dashboardButton);

        // By default, select the first button or any desired button.
        setSelectedButton(catalogButton);
    }

    private JButton createButton(String text, MainPanel mainPanel, String page) {
        JButton button = new JButton(text);

        // Make the button transparent
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        // Set Button Dimensions
        button.setPreferredSize(new Dimension(50, 30));

        // Listener to switch pages and mark the button as selected
        button.addActionListener(e -> {
            mainPanel.showPage(page);
            setSelectedButton(button);
        });

        return button;
    }

    private void setSelectedButton(JButton button) {
        // Remove border from previously selected button (if any)
        if (selectedButton != null) {
            selectedButton.setBorderPainted(false);
            selectedButton.setBorder(null);
        }

        // Set new selected button
        selectedButton = button;

        // Give the newly selected button a visible bottom border
        updateButtonBorderColor();
    }

    public void updateButtonBorderColor() {
        if (selectedButton != null) {
            Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, UIManager.getColor("Button.foreground"));
            selectedButton.setBorder(bottomBorder);
            selectedButton.setBorderPainted(true);
        }
    }
}