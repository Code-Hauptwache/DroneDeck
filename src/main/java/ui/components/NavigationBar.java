package main.java.ui.components;

import javax.swing.*;


public class NavigationBar extends JComponent {
    /**
     * Creates a new navigation bar.
     */
    public NavigationBar(MainPanel mainPanel) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(Box.createHorizontalGlue());

        // "Catalog" button
        JButton catalogButton = new JButton("Catalog");
        catalogButton.addActionListener(e -> mainPanel.showPage(MainPanel.PAGE_CATALOG));
        add(catalogButton);

        add(Box.createHorizontalStrut(10)); // spacing

        // "Dashboard" button
        JButton dashboardButton = new JButton("Dashboard");
        dashboardButton.addActionListener(e -> mainPanel.showPage(MainPanel.PAGE_DASHBOARD));
        add(dashboardButton);

        // Fill remaining space
        add(Box.createHorizontalGlue());
    }
}