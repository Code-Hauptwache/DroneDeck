package main.java.services.ScrollPane;

import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class that creates a JScrollPane with an overlay scrollbar and
 * provides middle-click auto-scrolling functionality.
 */
public class ScrollPaneService {

    private static final Logger logger = Logger.getLogger(ScrollPaneService.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ScrollPaneService.class);

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

        // Create a timer for hiding the scrollbar
        Timer[] hideTimer = new Timer[1];

        // Add AdjustmentListener for scroll events
        vBar.addAdjustmentListener(e -> {
            JScrollBar vBar1 = (JScrollBar) e.getAdjustable();
            Dimension originalSize = new Dimension(10, vBar1.getHeight());

            // Show the scrollbar with a smooth transition
            smoothResize(vBar1, new Dimension(10, originalSize.height));

            // Cancel the previous timer and restart
            if (hideTimer[0] != null && hideTimer[0].isRunning()) {
                hideTimer[0].stop();
            }

            // Set a timer to hide the scrollbar after a delay
            hideTimer[0] = new Timer(1500, _ -> {
                smoothResize(vBar1, new Dimension(0, originalSize.height)); // Transition to hidden width
            });
            hideTimer[0].setRepeats(false); // Execute only once
            hideTimer[0].start();
        });

        // Add middle-click auto-scrolling
        AutoScrollHandler autoScrollHandler = new AutoScrollHandler(scrollPane);
        autoScrollHandler.attachListeners();

        return scrollPane;
    }

    /**
     * Smoothly resizes a JScrollBar to the target size over a specified duration.
     *
     * @param scrollBar  The JScrollBar to resize.
     * @param targetSize The target size for the scrollbar.
     */
    private static void smoothResize(JScrollBar scrollBar, Dimension targetSize) {
        new Thread(() -> {
            Dimension currentSize = scrollBar.getPreferredSize();
            int steps = 60; // Number of animation steps
            int delay = 300 / steps; // Delay per step in milliseconds

            for (int i = 1; i <= steps; i++) {
                int width = currentSize.width + (targetSize.width - currentSize.width) * i / steps;
                SwingUtilities.invokeLater(() -> {
                    scrollBar.setPreferredSize(new Dimension(width, currentSize.height));
                    scrollBar.revalidate();
                    scrollBar.repaint();
                });
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, "Thread interrupted while resizing scrollbar.", e);
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
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
