package main.java.ui.components;

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
    private final int topBottomPadding = 20;

    // Card names
    public static final String PAGE_CATALOG = "DroneCatalog";
    public static final String PAGE_DASHBOARD = "DroneDashboard";

    // Center panel that will hold the pages
    private final JPanel centerPanel;
    private final CardLayout cardLayout;
    private String currentPage;

    public MainPanel() {
        super(new BorderLayout());

        // Set an initial border (in case we never get resized)
        setBorder(BorderFactory.createEmptyBorder(
                topBottomPadding,
                minLeftRightPadding,
                topBottomPadding,
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
        centerPanel.add(droneCatalogPanel, PAGE_CATALOG);
        centerPanel.add(droneDashboardPanel, PAGE_DASHBOARD);

        // Add the north panel (with navigation, search, theme switcher)
        // Note: pass `this` so the NavigationBar can call `showPage(...)`.
        add(new NorthPanel(this), BorderLayout.NORTH);

        // Add the center panel
        add(centerPanel, BorderLayout.CENTER);

        currentPage = PAGE_CATALOG; // Default page

        // TODO: Add pagination controls (South) if needed
    }

    /**
     * Show one of the pages in the center panel.
     * @param pageName The name of the page (e.g., "DroneCatalog" or "DroneDashboard")
     */
    public void showPage(String pageName) {
        cardLayout.show(centerPanel, pageName);
        currentPage = pageName;
    }

    public String getCurrentPage() {
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
                topBottomPadding,
                horizontalPadding,
                topBottomPadding,
                horizontalPadding
        ));

        revalidate();
        repaint();
    }
}

