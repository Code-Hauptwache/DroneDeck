package main.java.ui.pages;

import main.java.ui.components.CardTemplate;
import main.java.ui.components.DroneCatalogCard;
import main.java.ui.dtos.DroneCatalogCardDto;

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

        // *** THIS IS AN EXAMPLE OF HOW THE CardTemplate AND DroneCatalogCard CAN BE USED ***
        // Use BorderLayout for main arrangement
        super(new BorderLayout());

        // Horizontal and vertical gaps for the GridLayout
        int horizontalGap = 30;
        int verticalGap = 30;

        // Add CardTemplate instances to the center panel using GridLayout
        JPanel cardPanel = new JPanel(new GridLayout(0, 1, horizontalGap, verticalGap));

        // Create a fake DroneDashboardCardDto
        DroneCatalogCardDto[] fakeDTO = new DroneCatalogCardDto[] {
                new DroneCatalogCardDto("Drone 1", "DJI", 1060, 53, 5075, 1000, 500),
                new DroneCatalogCardDto("Drone 2", "Parrot", 1200, 65, 4150, 1200, 600),
                new DroneCatalogCardDto("Drone 3", "Yuneec", 900, 60, 4300, 1100, 550),
                new DroneCatalogCardDto("Drone 4", "Autel", 1100, 70, 4900, 1500, 700),
                new DroneCatalogCardDto("Drone 5", "Skydio", 950, 58, 4200, 1050, 525),
                new DroneCatalogCardDto("Drone 6", "DJI", 1000, 55, 5000, 1250, 625),
                new DroneCatalogCardDto("Drone 7", "Parrot", 1150, 68, 4100, 1300, 650)
        };

        // Add the fake DroneDashboardCard to the cardPanel
        for (DroneCatalogCardDto dto : fakeDTO) {
            cardPanel.add(new DroneCatalogCard(dto), BorderLayout.WEST);
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