package main.java.services.ScrollPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Class to handle middle-click auto-scroll (vertical only).
 */
public class AutoScrollHandler {
    private final JScrollPane scrollPane;
    private final Timer scrollTimer;
    private boolean autoScrolling = false;
    private Point mousePoint = null; // where the mouse currently is

    // Constants for auto-scrolling
    private static final int TIMER_DELAY = 16;
    private static final int SCROLL_FACTOR = 5;

    public AutoScrollHandler(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
        // Timer to continuously adjust scrolling
        this.scrollTimer = new Timer(TIMER_DELAY, _ -> performAutoScroll());
    }

    public void attachListeners() {
        // Mouse press/release toggles auto-scrolling
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
            vBar.setValue(vBar.getValue() + speed);
        }
    }
}
