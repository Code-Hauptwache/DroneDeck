package main.java.ui.components;

import main.java.ui.dtos.DroneDashboardCardDto;

import javax.swing.*;
import java.awt.*;

/**
 * The DroneDashboardCard class is a custom JComponent that represents a card
 * with information about a drone. It is used in the dashboard to display
 * information about a drone.
 */
public class DroneDashboardCard extends JComponent {
    /**
     * Creates a new DroneDashboardCard with the given DTO.
     *
     * @param dto The DTO containing the information to display.
     */
    public DroneDashboardCard(DroneDashboardCardDto dto) {
        setLayout(new BorderLayout());

        // Main content container with GridLayout
        JPanel contentContainer = new JPanel(new GridLayout(5, 2, 0, 4));

        // Add remaining standard label-value pairs
        Component[] leftContent = {
                new DroneStatus(dto),
                new JLabel("Speed"),
                new JLabel("Location"),
                new JLabel("Traveled"),
                new JLabel("Serial")
        };

        Component[] rightContent = {
                new DroneVisualBatteryStatus(dto),
                new JLabel((int) dto.getSpeed() + " km/h" ),
                new JLabel(dto.getLocation() != null ? dto.getLocation() + " km" : "N/A"),
                new JLabel(dto.getTravelDistance() != null && dto.getTravelDistance().toString().isEmpty() ? dto.getTravelDistance().toString() : "N/A"),
                new JLabel(dto.getSerialNumber())
        };

        // Add remaining rows
        for (int i = 0; i < leftContent.length; i++) {
            // Add label
            contentContainer.add(leftContent[i]);

            // Add value
            contentContainer.add(rightContent[i]);
        }

        // Create card with the content
        CardTemplate card = new CardTemplate(
                dto.getTypename(),
                dto.getManufacture(),
                contentContainer
        );
        add(card, BorderLayout.CENTER);
    }
}