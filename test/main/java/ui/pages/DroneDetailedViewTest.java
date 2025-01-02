package main.java.ui.pages;

import main.java.ui.dtos.DroneDashboardCardDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class DroneDetailedViewTest {

    private JPanel overlayPanel;
    private DroneDetailedView droneDetailedView;

    @BeforeEach
    void setUp() {
        DroneDashboardCardDto dto = new DroneDashboardCardDto(
                "Test Drone",
                "Test Manufacturer",
                "Active",
                100,
                200,
                50.0,
                10.0,
                20.0,
                "12345"
        );

        overlayPanel = new JPanel();
        droneDetailedView = new DroneDetailedView(dto, overlayPanel);
    }

    @Test
    void testTitleAndSubtitle() {
        JLabel title = (JLabel) ((JPanel) ((JPanel) droneDetailedView.getComponent(0)).getComponent(2)).getComponent(0);
        JLabel subtitle = (JLabel) ((JPanel) ((JPanel) droneDetailedView.getComponent(0)).getComponent(2)).getComponent(1);

        assertEquals("Test Drone", title.getText());
        assertEquals("Test Manufacturer", subtitle.getText());
    }

    @Test
    void testBackButtonListener() {
        JLabel backButton = (JLabel) ((JPanel) droneDetailedView.getComponent(0)).getComponent(0);
        backButton.getMouseListeners()[0].mouseClicked(null);

        assertNull(overlayPanel.getParent());
    }

    @Test
    void testDetailContent() {
        JLabel detailsLabel = (JLabel) ((JPanel) ((JPanel) droneDetailedView.getComponent(1)).getComponent(1)).getComponent(0);

        assertEquals("Drone: 12345", detailsLabel.getText());
    }
}