package main.java.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainPanel extends JPanel {
    private final int maxContentWidth = 800;
    private final int minLeftRightPadding = 100;
    private final int topBottomPadding = 20;

    public MainPanel() {
        // Set the layout manager
        super(new BorderLayout());

        // Set an initial border (in case we never get resized)
        setBorder(BorderFactory.createEmptyBorder(
                topBottomPadding,
                minLeftRightPadding,
                topBottomPadding,
                minLeftRightPadding
        ));

        // Whenever this panel is resized, recalculate the padding
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustPadding();
            }
        });

        // Add the Navigation Bar (North)
        NavigationBar navigationBar = new NavigationBar();
        navigationBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(navigationBar, BorderLayout.NORTH);

        // TODO: Add content panel (Center)

        // TODO: Add pagination controls (South)
    }

    private void adjustPadding() {
        int panelWidth = getWidth();
        // Figure out how much "extra" space is left after maxContentWidth
        int extraWidth = panelWidth - maxContentWidth;

        // If extraWidth > 0, use half of it on each side as padding
        // Otherwise, just use the minimum padding
        int horizontalPadding = (extraWidth > 0) ? extraWidth / 2 : minLeftRightPadding;
        horizontalPadding = Math.max(horizontalPadding, minLeftRightPadding);

        // Update border with new dynamic left/right padding
        setBorder(BorderFactory.createEmptyBorder(
                topBottomPadding,
                horizontalPadding,
                topBottomPadding,
                horizontalPadding
        ));

        // Optional: revalidate and repaint if you want an immediate refresh
        revalidate();
        repaint();
    }
}

