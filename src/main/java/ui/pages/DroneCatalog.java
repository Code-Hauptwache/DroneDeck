package main.java.ui.pages;

import main.java.services.ScrollPane.ScrollPaneService;
import main.java.ui.components.DroneCatalogCard;
import main.java.ui.dtos.DroneDto;

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
        DroneDto[] fakeDTO = new DroneDto[] {
                new DroneDto(1, "Drone 1", "DJI", "IS", 314, 400, 50.0, 12.34, 56.78, "1234567890", 52.0, "SEN", 60.0, "2024-12-15T17:00:52.588123+01:00", 732.0, 54.0, 250.0, "2024-12-15T17:00:52.588123+01:00"),
                new DroneDto(2, "Drone 2", "DJI", "IS", 314, 4000, 32.0, 12.34, 56.78, "13454sdf34", 22.0, "SEN", 40.0, "2024-12-16T11:00:52.588123+01:00", 336.0, 43.0, 200.0, "2024-12-16T11:00:52.588123+01:00"),
                new DroneDto(3, "Drone 3", "Parrot", "OK", 200, 300, 45.0, 10.0, 50.0, "9876543210", 48.0, "USA", 55.0, "2024-12-17T10:00:52.588123+01:00", 500.0, 50.0, 220.0, "2024-12-17T10:00:52.588123+01:00"),
                new DroneDto(4, "Drone 4", "Yuneec", "OK", 250, 350, 40.0, 11.0, 52.0, "1122334455", 50.0, "CAN", 58.0, "2024-12-18T09:00:52.588123+01:00", 600.0, 52.0, 230.0, "2024-12-18T09:00:52.588123+01:00"),
                new DroneDto(5, "Drone 5", "Autel", "OK", 300, 450, 55.0, 13.0, 60.0, "2233445566", 53.0, "UK", 62.0, "2024-12-19T08:00:52.588123+01:00", 700.0, 55.0, 240.0, "2024-12-19T08:00:52.588123+01:00"),
                new DroneDto(6, "Drone 6", "Skydio", "OK", 280, 420, 52.0, 12.5, 58.0, "3344556677", 51.0, "AUS", 60.0, "2024-12-20T07:00:52.588123+01:00", 680.0, 53.0, 235.0, "2024-12-20T07:00:52.588123+01:00"),
                new DroneDto(7, "Drone 7", "Hubsan", "OK", 260, 390, 48.0, 11.5, 54.0, "4455667788", 49.0, "GER", 57.0, "2024-12-21T06:00:52.588123+01:00", 660.0, 51.0, 230.0, "2024-12-21T06:00:52.588123+01:00"),
                new DroneDto(8, "Drone 8", "Walkera", "OK", 240, 360, 45.0, 10.5, 50.0, "5566778899", 47.0, "FRA", 55.0, "2024-12-22T05:00:52.588123+01:00", 640.0, 49.0, 225.0, "2024-12-22T05:00:52.588123+01:00"),
        };

        // Add the fake DroneDashboardCard to the cardPanel
        for (DroneDto dto : fakeDTO) {
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
        JScrollPane scrollPane = ScrollPaneService.createScrollPane(cardPanel);

        add(scrollPane, BorderLayout.CENTER);
    }
}