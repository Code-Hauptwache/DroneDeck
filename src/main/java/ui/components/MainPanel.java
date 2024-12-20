package main.java.ui.components;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MainPanel() {
        // Set the layout manager
        setLayout(new BorderLayout());

        // Add padding to the panel
        setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // Add the Navigation Bar
        NavigationBar navigationBar = new NavigationBar();
        add(navigationBar, BorderLayout.NORTH);

        // Initialize CardLayout and cardPanel
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add placeholder content for Catalog and Dashboard pages
        JPanel catalogPanel = new CatalogPanel();
        JPanel dashboardPanel = new DashboardPanel();

        cardPanel.add(catalogPanel, "Catalog");
        cardPanel.add(dashboardPanel, "Dashboard");

        add(cardPanel, BorderLayout.CENTER);
    }

    public void showPage(String pageName) {
        cardLayout.show(cardPanel, pageName);
    }
}
