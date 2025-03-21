package main.java.ui.components;

import main.java.ui.pages.DroneCatalog;
import main.java.ui.pages.DroneDashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * The MainPanel class is a JPanel
 * that holds the main content of the application.
 * It uses a CardLayout to switch between different pages.
 */
public class MainPanel extends JPanel {
    private final int minLeftRightPadding = 100;
    private final int topAndBottomMainPanelPadding = 10;

    public enum Page {
        CATALOG, DASHBOARD
    }

    private final JLayeredPane layeredPane;
    private final JPanel cardPanel;
    private final CardLayout cardLayout;
    private DroneCatalog droneCatalog; // Not final since it's initialized lazily
    private final DroneDashboard droneDashboard;
    private Page currentPage = Page.DASHBOARD; // Track current page, initialized to DASHBOARD

    /**
     * Creates a new MainPanel with a BorderLayout.
     * It contains a JLayeredPane that holds the main content panel
     * and any overlays that are added on top of it.
     */
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

        // 1) Create a JLayeredPane to hold both the primary content panel and overlays
        layeredPane = new JLayeredPane() {
            @Override
            public void doLayout() {
                // Make sure every child in the layered pane fills its entire area
                for (int i = 0; i < getComponentCount(); i++) {
                    getComponent(i).setBounds(0, 0, getWidth(), getHeight());
                }
            }
        };

        // 2) Build a master panel that will hold your north panel and the cardPanel
        JPanel masterPanel = new JPanel(new BorderLayout());

        // 3) Create the cardPanel (with CardLayout) that holds your main pages
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Initially only create the dashboard panel since it's shown first
        droneDashboard = DroneDashboard.getInstance();
        droneCatalog = null; // Will be created lazily when needed
        
        // Add the dashboard panel to the cardPanel
        cardPanel.add(createDashboardPanel(), Page.DASHBOARD.name());
        showPage(Page.DASHBOARD);

        // 4) Create a north panel (header/navigation)
        JPanel northWrapper = new JPanel(new BorderLayout());
        int northBottomPadding = 10;
        northWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, northBottomPadding, 0));
        northWrapper.add(new NorthPanel(this), BorderLayout.CENTER);

        // 5) Add the north panel and the card panel into the masterPanel
        masterPanel.add(northWrapper, BorderLayout.NORTH);
        masterPanel.add(cardPanel, BorderLayout.CENTER);

        // 6) Put the masterPanel in the DEFAULT layer of the layeredPane
        layeredPane.add(masterPanel, JLayeredPane.DEFAULT_LAYER);

        // 7) Finally, add the layeredPane to this MainPanel
        add(layeredPane, BorderLayout.CENTER);
    }

    /**
     * Show one of the pages in the cardPanel.
     */
    public void showPage(Page page) {
        if (page == Page.CATALOG && droneCatalog == null) {
            // Lazy initialization of catalog when first needed
            droneCatalog = DroneCatalog.getInstance();
            cardPanel.add(createCatalogPanel(), Page.CATALOG.name());
        }
        currentPage = page; // Update current page
        cardLayout.show(cardPanel, page.name());
    }

    /**
     * Create a panel holding the DroneCatalog page.
     */
    private JPanel createCatalogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(droneCatalog, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Create a panel holding the DroneDashboard page.
     */
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(droneDashboard, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Adjusts the padding around the main panel based on its width.
     */
    private void adjustPadding() {
        int panelWidth = getWidth();
        int maxContentWidth = 1200;
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

    public JLayeredPane getMainLayeredPane() {
        return layeredPane;
    }

    /**
     * Gets the currently displayed page.
     * @return The current Page enum value
     */
    public Page getCurrentPage() {
        return currentPage;
    }
}
