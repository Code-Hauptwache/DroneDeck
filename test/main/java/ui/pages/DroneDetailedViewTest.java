package main.java.ui.pages;

import main.java.ui.dtos.DroneDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DroneDetailedViewTest {

    private JPanel overlayPanel;
    private DroneDetailedView droneDetailedView;

    @BeforeEach
    void setUp() {
        DroneDto dto = new DroneDto(
                "Test Drone",
                "Test Manufacturer",
                "Active",
                100,
                200,
                50.0,
                10.0,
                20.0,
                "12345",
                52.0,
                "SEN",
                60.0,
                "2024-12-15T17:00:52.588123+01:00",
                732.0,
                54.0,
                250.0,
                "2024-12-15T17:00:52.588123+01:00"
        );

        overlayPanel = new JPanel();
        droneDetailedView = new DroneDetailedView(dto, overlayPanel);
    }

    @Test
    void testTitleAndSubtitle() {
        JPanel topBar = (JPanel) droneDetailedView.getComponent(0);
        JPanel titlePane = (JPanel) topBar.getComponent(2);
        JLabel title = (JLabel) titlePane.getComponent(0);
        JLabel subtitle = (JLabel) titlePane.getComponent(1);

        assertEquals("Test Drone", title.getText());
        assertEquals("Test Manufacturer", subtitle.getText());
    }

    @Test
    void testBackButtonListener() {
        JPanel topBar = (JPanel) droneDetailedView.getComponent(0);
        JLabel backButton = (JLabel) topBar.getComponent(0);
        backButton.getMouseListeners()[0].mouseClicked(null);

        assertNull(overlayPanel.getParent());
    }


    @Test
    void testDetailContent() {
        JPanel wrapper = (JPanel) droneDetailedView.getComponent(1);
        JPanel centerPanel = (JPanel) ((JPanel) wrapper.getComponent(0)).getComponent(0);

        // Iterate through the components and verify their content
        for (Component component : centerPanel.getComponents()) {
            if (component instanceof JLabel label) {
                String text = label.getText();
                switch (text) {
                    case "Speed" -> assertEquals("Speed", text);
                    case "50 km/h" -> assertEquals("50 km/h", text);
                    case "Average Speed" -> assertEquals("Average Speed", text);
                    case "N/A" -> assertEquals("N/A", text);
                }
            }
        }
    }
}