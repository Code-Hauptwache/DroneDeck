package main.java.ui.components;

import main.java.ui.dtos.DroneDto;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DroneDashboardCardTest {

    @Test
    void testDashboardDroneCardContent() {
        // Create a fake DroneDto
        DroneDashboardCard card = getDroneDashboardCard();

        // Get the content container
        JPanel contentContainer = (JPanel) ((CardTemplate) card.getComponent(0)).getComponent(1);

        // Verify the content of the card
        Component[] components = contentContainer.getComponents();
        int labelIndex = 0;

        String[] expectedTexts = {
                "Issue", "78.5%",
                "Speed", "50 km/h",
                "Location", "N/A",
                "Traveled", "N/A",
                "Serial", "1234567890"
        };

        for (Component component : components) {
            if (component instanceof JLabel) {
                assertEquals(expectedTexts[labelIndex], ((JLabel) component).getText());
                labelIndex++;
            }
        }
    }

    private static @NotNull DroneDashboardCard getDroneDashboardCard() {
        DroneDto dto = new DroneDto(
                1,
                "Drone 1",
                "DJI",
                "IS",
                314,
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

        // Create a DroneDashboardCard instance
        DroneDashboardCard card = new DroneDashboardCard(dto);
        return card;
    }
}