package main.java.ui.components;

import main.java.ui.dtos.DroneCatalogCardDto;

import javax.swing.*;
import java.awt.*;

public class DroneCatalogCard extends JComponent {
    public DroneCatalogCard(DroneCatalogCardDto dto) {
        setLayout(new BorderLayout());

        // Main content container with GridLayout
        JPanel contentContainer = new JPanel(new GridLayout(5, 2, 0, 4));

        // Add remaining standard label-value pairs
        Component[] leftContent = {
                new JLabel("Weight"),
                new JLabel("Max Speed"),
                new JLabel("Battery Size"),
                new JLabel("Control Range"),
                new JLabel("Max Carriage")
        };

        Component[] rightContent = {
                new JLabel((int) dto.getWeight() + " g" ),
                new JLabel((int) dto.getMaxSpeed() + " km/h"),
                new JLabel((int) dto.getBatteryCapacity() + " mAh"),
                new JLabel((int) dto.getControlRange() + " m"),
                new JLabel((int) dto.getMaxCarriage() + " g")
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
