package main.java.ui.components;

import main.java.ui.MainPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The NavigationBar class is a JComponent that displays a navigation bar with buttons for navigating the application.
 * The navigation bar highlights the selected button.
 */
public class NavigationBar extends JComponent {

    private static final List<NavigationBar> instances = new ArrayList<>();
    private JButton selectedButton;

    /**
     * Creates a new navigation bar.
     * The navigation bar contains buttons for navigating the application.
     * The navigation bar highlights the selected button.
     *
     * @param mainPanel the main panel of the application
     */
    public NavigationBar(MainPanel mainPanel) {
        instances.add(this);
        setLayout(new GridLayout(1, 2, 0, 0));

        // "Catalog" button
        JButton catalogButton = createButton("Catalog", mainPanel, MainPanel.Page.CATALOG);
        add(catalogButton);

        // "Dashboard" button
        JButton dashboardButton = createButton("Dashboard", mainPanel, MainPanel.Page.DASHBOARD);
        add(dashboardButton);

        // By default, select the dashboard button
        setSelectedButton(dashboardButton);
    }

    private JButton createButton(String text, MainPanel mainPanel, MainPanel.Page page) {
        JButton button = new JButton(text);

        // Make the button transparent
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);

        // Set Button Dimensions
        button.setPreferredSize(new Dimension(50, 30));

        // Listener to switch pages and mark the button as selected
        button.addActionListener(_ -> {
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

    /**
     * Updates the border color of the selected button.
     * This method is called in the theme switcher to update the border color of the selected button.
     */
    public void updateButtonBorderColor() {
        if (selectedButton != null) {
            Border bottomBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, UIManager.getColor("Label.foreground"));
            selectedButton.setBorder(bottomBorder);
            selectedButton.setBorderPainted(true);
        }
    }

    /**
     * Update the border color of all NavigationBar instances.
     * This method should be called whenever the theme changes.
     */
    public static void updateAllInstances() {
        for (NavigationBar navigationBar : instances) {
            navigationBar.updateButtonBorderColor();
        }
    }
}