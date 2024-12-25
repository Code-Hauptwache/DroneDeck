package main.java.ui;

import main.java.ui.components.NorthPanel;
import main.java.ui.pages.DroneCatalog;
import main.java.ui.pages.DroneDashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * The main panel of the application.
 * This panel contains the north panel (navigation bar, search bar, and theme switcher),
 * the content panel (which displays the main content of the application), and the pagination controls.
 * The main panel is responsible for adjusting the padding of the content panel based on the width of the main panel.
 */
public class MainPanel extends JPanel {
    private final int maxContentWidth = 800;
    private final int minLeftRightPadding = 100;
    private final int topAndBottomMainPanelPadding = 10;
    private final int northBottomPadding = 10;

    // Enum for page names
    public enum Page {
        CATALOG, DASHBOARD
    }

    // Center panel that will hold the pages
    private final JPanel centerPanel;
    private final CardLayout cardLayout;
    private Page currentPage;

    public MainPanel() {
        super(new BorderLayout());

        // Set an initial border (in case we never get resized)
        setBorder(BorderFactory.createEmptyBorder(
                topAndBottomMainPanelPadding,
                minLeftRightPadding,
                topAndBottomMainPanelPadding,
                minLeftRightPadding
        ));

        // Recalculate padding on resize
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustPadding();
            }
        });

        // Create a CardLayout panel to hold the pages
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);

        // Instantiate the pages
        JPanel droneCatalogPanel = createCatalogPanel();
        JPanel droneDashboardPanel = createDashboardPanel();

        // Add the pages to the center panel
        centerPanel.add(droneCatalogPanel, Page.CATALOG.name());
        centerPanel.add(droneDashboardPanel, Page.DASHBOARD.name());

        // Add the north panel (with navigation, search, theme switcher) with bottom padding
        JPanel northWrapper = new JPanel(new BorderLayout());
        northWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, northBottomPadding, 0));
        northWrapper.add(new NorthPanel(this), BorderLayout.CENTER);
        add(northWrapper, BorderLayout.NORTH);

        // Add the center panel
        add(centerPanel, BorderLayout.CENTER);

        // Show the dashboard page by default
        showPage(Page.DASHBOARD);
    }

    /**
     * Show one of the pages in the center panel.
     * @param page The page to show (e.g., Page.CATALOG or Page.DASHBOARD)
     */
    public void showPage(Page page) {
        cardLayout.show(centerPanel, page.name());
        currentPage = page;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    /**
     * Create a panel holding the DroneCatalog page.
     */
    private JPanel createCatalogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new DroneCatalog(), BorderLayout.CENTER);
        return panel;
    }

    /**
     * Create a panel holding the DroneDashboard page.
     */
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new DroneDashboard(), BorderLayout.CENTER);
        return panel;
    }

    private void adjustPadding() {
        int panelWidth = getWidth();
        int extraWidth = panelWidth - maxContentWidth;

        int horizontalPadding = (extraWidth > 0) ? extraWidth / 2 : minLeftRightPadding;
        horizontalPadding = Math.max(horizontalPadding, minLeftRightPadding);

        setBorder(BorderFactory.createEmptyBorder(
                topAndBottomMainPanelPadding,
                horizontalPadding,
                topAndBottomMainPanelPadding,
                horizontalPadding
        ));

        revalidate();
        repaint();
    }
}