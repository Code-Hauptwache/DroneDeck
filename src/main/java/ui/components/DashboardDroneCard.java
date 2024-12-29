package main.java.ui.components;

import main.java.ui.dtos.DashboardDroneCardDto;

import javax.swing.*;
import java.awt.*;

/**
 * The DashboardDroneCard class is a custom JComponent that represents a card
 * with information about a drone. It is used in the dashboard to display
 * information about a drone.
 */
public class DashboardDroneCard extends JComponent {
    /**
     * Creates a new DashboardDroneCard with the given DTO.
     *
     * @param dto The DTO containing the information to display.
     */
    public DashboardDroneCard(DashboardDroneCardDto dto) {
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
                new JLabel(dto.getLocation().isEmpty() ? "N/A" : dto.getLocation()),
                new JLabel(dto.getTravelDistance() != null ? dto.getTravelDistance() + " km" : "N/A"),
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