package main.java.services.ScrollPane;

import javax.swing.*;
import java.awt.*;

public class ScrollPaneService {
    /**
     * Creates a scroll pane for the provided panel with default vertical scrolling and no horizontal scrolling.
     * @param contentPanel The panel to be wrapped in a scroll pane.
     * @return The configured JScrollPane.
     */
    public static JScrollPane createScrollPane(JPanel contentPanel) {
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(0, 0));

        // Hide vertical scrollbar track and adjust scroll speed
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setBlockIncrement(50);

        return scrollPane;
    }
}

