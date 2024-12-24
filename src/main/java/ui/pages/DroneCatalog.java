package main.java.ui.pages;

import main.java.ui.components.CardTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DroneCatalog extends JPanel {
    /**
     * The DroneCatalog class is a JPanel... (TODO)
     */
    public DroneCatalog() {
        // TODO: Implement the Drone Catalog

        // *** THIS IS AN EXAMPLE OF HOW THE CardTemplate CAN BE USED ***
        // Use BorderLayout for main arrangement
        super(new BorderLayout());

        // Add CardTemplate instances to the center panel using FlowLayout
        JPanel cardPanel = new JPanel(new GridLayout(0, 1, 10, 10));

        // Create multiple CardTemplates for testing
        for (int i = 1; i <= 10; i++) {
            JPanel testPanel = new JPanel(new FlowLayout());
            testPanel.add(new JButton("Card " + i + " Button"));
            testPanel.add(new JLabel("Card " + i + " Label"));

            // Suppose each CardTemplate is 250 wide
            CardTemplate card = new CardTemplate(
                    "Test Title #" + i,
                    "Test Subtitle #" + i,
                    testPanel
            );
            cardPanel.add(card);
        }

        // Add a resize listener to adjust the number of columns
        cardPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = cardPanel.getWidth();

                // Each CardTemplate is ~250 wide, plus we have a 10px gap (GridLayout hGap)
                // We'll assume some extra spacing; adjust as needed
                int cardTotalWidth = 250 + 10; // 250 for card + 10 for right gap
                // Compute how many columns can fit
                int columns = Math.max(1, panelWidth / cardTotalWidth);

                // Reconfigure layout with that many columns
                GridLayout layout = (GridLayout) cardPanel.getLayout();
                layout.setColumns(columns);

                // Force layout update
                cardPanel.revalidate();
            }
        });

        // Make it scrollable (vertical only)
        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(0, 0));

        // Hide the vertical scrollbar track, but allow mouse-wheel scrolling
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.setWheelScrollingEnabled(true);

        // Scroll speed adjustments (optional)
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setBlockIncrement(50);

        add(scrollPane, BorderLayout.CENTER);
    }
}