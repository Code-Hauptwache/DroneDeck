package main.java.ui.pages;

import main.java.entity.DroneTypeEntity;
import main.java.services.LocalSearch.LocalSearchService;
import main.java.services.LocalSearch.ILocalSearchService;
import main.java.ui.components.DroneCatalogCard;
import main.java.ui.dtos.DroneDto;
import main.java.services.ScrollPane.ScrollPaneService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class DroneCatalog extends JPanel {

    public DroneCatalog() {
        // Use BorderLayout for main arrangement
        super(new BorderLayout());

        // Horizontal and vertical gaps for the GridLayout
        int horizontalGap = 30;
        int verticalGap = 30;

        // Add CardTemplate instances to the center panel using GridLayout
        JPanel cardPanel = new JPanel(new GridLayout(0, 1, horizontalGap, verticalGap));

        // Load drone types from local file
        ILocalSearchService localSearchService = LocalSearchService.getCurrentInstance();
        List<DroneTypeEntity> droneTypes = localSearchService.getAllDroneTypes();

        // Add the DroneCatalogCard to the cardPanel
        for (DroneTypeEntity droneType : droneTypes) {
            DroneDto dto = new DroneDto(
                    droneType.id,
                    droneType.typename,
                    droneType.manufacturer,
                    "N/A", // Status
                    0, // Battery status
                    droneType.battery_capacity,
                    0, // Speed
                    0.0, // Longitude
                    0.0, // Latitude
                    "N/A", // Serial number
                    0.0, // Carriage weight
                    "N/A", // Carriage type
                    droneType.max_carriage,
                    "N/A", // Last seen
                    droneType.weight,
                    droneType.max_speed,
                    droneType.control_range,
                    "N/A" // Timestamp
            );
            cardPanel.add(new DroneCatalogCard(dto), BorderLayout.WEST);
        }

        // Add a resize listener to adjust the number of columns
        cardPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = cardPanel.getWidth();

                // Each CardTemplate is ~250 wide, plus we have a 10px gap (GridLayout hGap)
                // We'll assume some extra spacing; adjust as needed
                int cardTotalWidth = 250 + horizontalGap; // 250 for card + 10 for right gap
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