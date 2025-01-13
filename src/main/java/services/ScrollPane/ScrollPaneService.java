package main.java.services.ScrollPane;

import javax.swing.*;
import java.awt.*;

/**
 * Service class that creates a JScrollPane with an overlay scrollbar and
 * provides middle-click auto-scrolling functionality.
 */
public class ScrollPaneService {

    /**
     * Creates a scroll pane for the provided panel with an overlay
     * vertical scrollbar and no horizontal scrollbar.
     *
     * @param contentPanel The panel to be wrapped in a scroll pane.
     * @return The configured JScrollPane.
     */
    public static JScrollPane createScrollPane(JPanel contentPanel) {
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setViewportBorder(null);

        // Configure the scroll pane
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Use custom layout that overlays the vertical scrollbar
        scrollPane.setLayout(new OverlayScrollPaneLayout());

        // Set the vertical scrollbar width
        JScrollBar vBar = scrollPane.getVerticalScrollBar();

        // Adjust scroll speed
        scrollPane.setWheelScrollingEnabled(true);
        vBar.setUnitIncrement(16);
        vBar.setBlockIncrement(50);
        vBar.setPreferredSize(new Dimension(0, 0));

        // Add AdjustmentListener to print to console on scroll
        vBar.addAdjustmentListener(e -> {
            JScrollBar vBar1 = (JScrollBar) e.getAdjustable();
            Dimension originalSize = vBar1.getPreferredSize();

            // Set to default dimension
            vBar1.setPreferredSize(new Dimension(10, originalSize.height));
            vBar1.revalidate();
            vBar1.repaint();

            // Create a timer to reset the dimension after 2 seconds
            Timer timer = new Timer(2000, _ -> {
                vBar1.setPreferredSize(new Dimension(0, 0));
                vBar1.revalidate();
                vBar1.repaint();
            });
            timer.setRepeats(false); // Only execute once
            timer.start();
        });

        // Add middle-click auto-scrolling
        AutoScrollHandler autoScrollHandler = new AutoScrollHandler(scrollPane);
        autoScrollHandler.attachListeners();

        return scrollPane;
    }

    /**
     * Custom layout manager that places the scrollbar on top of
     * (overlaying) the content viewport instead of resizing it.
     */
    private static class OverlayScrollPaneLayout extends ScrollPaneLayout {
        @Override
        public void layoutContainer(Container parent) {
            if (!(parent instanceof JScrollPane scrollPane)) {
                super.layoutContainer(parent);
                return;
            }

            // Delegate to the default layout manager
            super.layoutContainer(parent);

            // Get the viewport, vertical scrollbar, and insets
            Insets insets = scrollPane.getInsets();
            if (viewport != null) {
                viewport.setBounds(
                        insets.left,
                        insets.top,
                        scrollPane.getWidth() - insets.left - insets.right,
                        scrollPane.getHeight() - insets.top - insets.bottom
                );
            }

            // Adjust the vertical scrollbar to overlay the viewport
            if (vsb != null) {
                int vsbWidth = vsb.getPreferredSize().width;
                vsb.setBounds(
                        scrollPane.getWidth() - insets.right - vsbWidth,
                        insets.top,
                        vsbWidth,
                        scrollPane.getHeight() - insets.top - insets.bottom
                );

                // Ensure the scrollbar is on top of the viewport
                scrollPane.setComponentZOrder(vsb, 0);
                scrollPane.setComponentZOrder(viewport, 1);
            }
        }
    }
}
