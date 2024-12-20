package main.java.ui.components;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton catalogButton;
    private JButton dashboardButton;

    public MainPanel() {
        setLayout(new BorderLayout());

        // Create navigation buttons
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new FlowLayout());

        catalogButton = new JButton("Catalog");
        catalogButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        catalogButton.addActionListener(e -> showCatalogPage());
        navigationPanel.add(catalogButton);

        dashboardButton = new JButton("Dashboard");
        dashboardButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dashboardButton.addActionListener(e -> showDashboardPage());
        navigationPanel.add(dashboardButton);

        add(navigationPanel, BorderLayout.NORTH);

        // Create content panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Add placeholder content for Catalog and Dashboard pages
        contentPanel.add(new JLabel("Catalog Page", SwingConstants.CENTER), "Catalog");
        contentPanel.add(new JLabel("Dashboard Page", SwingConstants.CENTER), "Dashboard");

        add(contentPanel, BorderLayout.CENTER);

        // Show Catalog page by default
        showCatalogPage();
    }

    private void showCatalogPage() {
        cardLayout.show(contentPanel, "Catalog");
        highlightButton(catalogButton);
    }

    private void showDashboardPage() {
        cardLayout.show(contentPanel, "Dashboard");
        highlightButton(dashboardButton);
    }

    private void highlightButton(JButton button) {
        catalogButton.setFont(catalogButton.getFont().deriveFont(Font.PLAIN));
        dashboardButton.setFont(dashboardButton.getFont().deriveFont(Font.PLAIN));
        button.setFont(button.getFont().deriveFont(Font.BOLD));
    }
}
