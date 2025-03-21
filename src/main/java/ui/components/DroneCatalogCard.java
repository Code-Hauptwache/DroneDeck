package main.java.ui.components;

import main.java.ui.dtos.DroneDto;

import javax.swing.*;
import java.awt.*;

/**
 * The DroneCatalogCard class is a custom
 * JComponent that represents a card with information about a drone.
 * It is used in the catalog to display information about a drone.
 */
public class DroneCatalogCard extends JComponent {

    /**
     * Creates a new DroneCatalogCard with the given DTO.
     *
     * @param dto The DTO containing the information to display.
     */
    public DroneCatalogCard(DroneDto dto) {
        setLayout(new BorderLayout());

        // Main content container with GridLayout
        JPanel contentContainer = new JPanel(new GridLayout(5, 2, 0, 4));

        // Add remaining standard label-value pairs
        Component[] leftContent = {
                new JLabel("Weight"),
                new JLabel("Top Speed"),
                new JLabel("Battery Size"),
                new JLabel("Control Range"),
                new JLabel("Max Carriage")
        };

        Component[] rightContent = {
                new JLabel((int) dto.getWeight() + " g" ),
                new JLabel((int) dto.getMaxSpeed() + " km/h"),
                new JLabel((int) dto.getBatteryCapacity() + " mAh"),
                new JLabel((int) dto.getControlRange() + " m"),
                new JLabel((int) dto.getMaxCarriageWeight() + " g")
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
                dto.getTypeName(),
                dto.getManufacturer(),
                contentContainer
        );

        // Set the preferred, minimum, and maximum sizes to match the CardTemplate
        Dimension cardSize = card.getPreferredSize();
        setPreferredSize(cardSize);
        setMinimumSize(cardSize);
        setMaximumSize(cardSize);

        add(card, BorderLayout.CENTER);
    }
}
