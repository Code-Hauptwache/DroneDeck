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
 * This panel contains:
 *  - The north panel (navigation bar, search bar, and theme switcher).
 *  - A JLayeredPane, which holds:
 *       1. A "cardPanel" (using a CardLayout) for the main pages (catalog, dashboard).
 *       2. An optional overlay panel (added dynamically above the main pages).
 *  - Pagination controls (not shown in this example).
 * <p>
 * The main panel is also responsible for adjusting padding around the content,
 * based on the total width of the application window.
 */
public class MainPanel extends JPanel {
    private final int maxContentWidth = 1200;
    private final int minLeftRightPadding = 100;
    private final int topAndBottomMainPanelPadding = 10;
    private final int northBottomPadding = 10;

    // Enum for page names
    public enum Page {
        CATALOG, DASHBOARD
    }

    // A JLayeredPane allows us to place an "overlay" above other panels.
    private final JLayeredPane layeredPane;

    // The panel that displays your main pages via CardLayout
    private final JPanel cardPanel;
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

        // 1) Create a JLayeredPane to hold both your main content (cardPanel) and overlays
        layeredPane = new JLayeredPane() {
            @Override
            public void doLayout() {
                // Make sure every child in the layered pane fills its entire area
                for (int i = 0; i < getComponentCount(); i++) {
                    getComponent(i).setBounds(0, 0, getWidth(), getHeight());
                }
            }
        };

        // 2) Create the cardPanel (with CardLayout) that holds your main pages
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Instantiate the pages
        JPanel droneCatalogPanel = createCatalogPanel();
        JPanel droneDashboardPanel = createDashboardPanel();

        // Add the pages to the cardPanel
        cardPanel.add(droneCatalogPanel, Page.CATALOG.name());
        cardPanel.add(droneDashboardPanel, Page.DASHBOARD.name());

        // By default, show the DASHBOARD
        showPage(Page.DASHBOARD);

        // 3) Add the cardPanel to the layeredPane at the DEFAULT layer
        layeredPane.add(cardPanel, JLayeredPane.DEFAULT_LAYER);

        // 4) Add a north panel (header/navigation) with some padding below it
        JPanel northWrapper = new JPanel(new BorderLayout());
        northWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, northBottomPadding, 0));
        northWrapper.add(new NorthPanel(this), BorderLayout.CENTER);
        add(northWrapper, BorderLayout.NORTH);

        // 5) Add the layeredPane to the center of MainPanel
        add(layeredPane, BorderLayout.CENTER);
    }

    /**
     * Show one of the pages in the cardPanel.
     * @param page The page to show (e.g., Page.CATALOG or Page.DASHBOARD)
     */
    public void showPage(Page page) {
        cardLayout.show(cardPanel, page.name());
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

    /**
     * Adjusts the padding around the main panel based on its width.
     */
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

    /**
     * Exposes the JLayeredPane so other components can add overlays above the main pages.
     */
    public JLayeredPane getMainLayeredPane() {
        return layeredPane;
    }
}
