package main.java.services.ScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ScrollPaneService {

    /**
     * Creates a scroll pane for the provided panel with default
     * vertical scrolling and no horizontal scrolling.
     *
     * @param contentPanel The panel to be wrapped in a scroll pane.
     * @return The configured JScrollPane.
     */
    public static JScrollPane createScrollPane(JPanel contentPanel) {
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(0, 0));

        // Hide vertical scrollbar track and adjust scroll speed
        scrollPane.getVerticalScrollBar()
                .setPreferredSize(new Dimension(0, 0));
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setBlockIncrement(50);

        // Attach auto-scroll behavior
        AutoScrollHandler autoScrollHandler = new AutoScrollHandler(scrollPane);
        autoScrollHandler.attachListeners();

        return scrollPane;
    }

    /**
     * Inner class to handle middle-click auto-scroll (vertical only).
     */
    private static class AutoScrollHandler {
        private final JScrollPane scrollPane;
        private final Timer scrollTimer;
        private boolean autoScrolling = false;
        private Point mousePoint = null; // where the mouse currently is

        // Adjust these as desired for “smoothness”
        private static final int TIMER_DELAY = 16; // ~60 FPS
        private static final int SCROLL_FACTOR = 5;

        public AutoScrollHandler(JScrollPane scrollPane) {
            this.scrollPane = scrollPane;

            // Timer to update scrolling while autoScroll is active
            this.scrollTimer = new Timer(TIMER_DELAY, e -> performAutoScroll());
        }

        public void attachListeners() {
            // Mouse press/release sets auto-scrolling on/off
            scrollPane.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isMiddleMouseButton(e)) {
                        autoScrolling = true;
                        mousePoint = e.getPoint();
                        scrollTimer.start();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (SwingUtilities.isMiddleMouseButton(e)) {
                        autoScrolling = false;
                        scrollTimer.stop();
                    }
                }
            });

            // Mouse motion updates the stored position
            scrollPane.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    if (autoScrolling) {
                        mousePoint = e.getPoint();
                    }
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    if (autoScrolling) {
                        mousePoint = e.getPoint();
                    }
                }
            });
        }

        /**
         * Continuously invoked by the timer to adjust scrolling
         * based on how far the mouse is from the center.
         */
        private void performAutoScroll() {
            if (!autoScrolling || mousePoint == null) {
                return;
            }
            // The vertical center of the visible area
            int centerY = scrollPane.getViewport().getHeight() / 2;
            // Distance from center determines scroll speed
            int distanceFromCenter = mousePoint.y - centerY;
            int speed = distanceFromCenter / SCROLL_FACTOR;

            // Only scroll if there's an actual offset
            if (speed != 0) {
                JScrollBar vBar = scrollPane.getVerticalScrollBar();
                int currentValue = vBar.getValue();
                vBar.setValue(currentValue + speed);
            }
        }
    }
}
