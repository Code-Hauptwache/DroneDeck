package main.java.ui.pages;

import main.java.ui.components.CardTemplate;
import main.java.ui.components.DroneDashboardCard;
import main.java.ui.dtos.DroneDashboardDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class DroneDashboard extends JPanel {
    /**
     * The DroneDashboard class is a JPanel... (TODO)
     */
    public DroneDashboard() {
        // TODO: Implement the Drone Dashboard

        // *** THIS IS AN EXAMPLE OF HOW THE CardTemplate AND DroneDashboardCard CAN BE USED ***
        // Use BorderLayout for main arrangement
        super(new BorderLayout());

        // Horizontal and vertical gaps for the GridLayout
        int horizontalGap = 30;
        int verticalGap = 30;

        // This is a placeholder in orange for the graphical components of the drone dashboard
        JLabel label = new JLabel("Graphical Components (TODO)", SwingConstants.CENTER);
        label.setForeground(Color.ORANGE);
        label.setPreferredSize(new Dimension(0, 300 + horizontalGap));
        label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.ORANGE));
        add(label, BorderLayout.NORTH);

        // Add CardTemplate instances to the center panel using GridLayout
        JPanel cardPanel = new JPanel(new GridLayout(0, 1, horizontalGap, verticalGap));

        // Create a fake DroneDashboardDto
        DroneDashboardDto fakeDTO = new DroneDashboardDto(
                "Drone 1",
                "DJI",
                "IS",
                400,
                400,
                50.0,
                12.34,
                56.78,
                "1234567890",
                52.0,
                "SEN",
                60.0,
                "2024-12-15T17:00:52.588123+01:00",
                732.0,
                54.0,
                250.0,
                "2024-12-15T17:00:52.588123+01:00"
        );

        // Add the fake DroneDashboardCard to the cardPanel
        cardPanel.add(new DroneDashboardCard(fakeDTO), BorderLayout.WEST);

        // Create multiple CardTemplates for testing
        for (int i = 1; i <= 19; i++) {
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
                int cardTotalWidth = 250 +  horizontalGap; // 250 for card + 10 for right gap
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
