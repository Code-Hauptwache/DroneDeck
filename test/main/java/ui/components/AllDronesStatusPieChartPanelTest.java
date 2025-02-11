package main.java.ui.components;

import main.java.ui.dtos.DroneDto;
import java.util.ArrayList;
import java.util.List;
import main.java.services.ApiToken.ApiTokenService;
import main.java.services.DroneStatus.DroneStatusService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AllDronesStatusPieChartPanelTest {
    private AllDronesStatusPieChartPanel allDronesStatusPieChartPanel;

    @BeforeAll
    static void initialSetup() {
        //Initialize the parent frame for the dialog
        JFrame frame = new JFrame();
        ApiTokenService.setParent(frame);
    }

    @BeforeEach
    void setUp() {
        List<DroneDto> mockDrones = new ArrayList<>();
        // Add mock drones with different statuses
        mockDrones.add(new DroneDto(1, "Type1", "Mfg1", "ON", 80, 100, 20.0, 0.0, 0.0, "SN1", 10.0, "NONE", 20.0, "2024-02-11T10:00:00", 15.0, 30.0, 1000.0, "2024-02-11T10:00:00"));
        mockDrones.add(new DroneDto(2, "Type2", "Mfg2", "IS", 60, 100, 15.0, 0.0, 0.0, "SN2", 8.0, "NONE", 15.0, "2024-02-11T10:00:00", 12.0, 25.0, 800.0, "2024-02-11T10:00:00"));
        mockDrones.add(new DroneDto(3, "Type3", "Mfg3", "OF", 20, 100, 0.0, 0.0, 0.0, "SN3", 5.0, "NONE", 10.0, "2024-02-11T10:00:00", 10.0, 20.0, 600.0, "2024-02-11T10:00:00"));
        
        DroneStatusService droneStatusService = new DroneStatusService(mockDrones) {
            @Override
            public int getOnlineCount() {
                return 5;
            }

            @Override
            public int getIssueCount() {
                return 3;
            }

            @Override
            public int getOfflineCount() {
                return 2;
            }
        };

        allDronesStatusPieChartPanel = new AllDronesStatusPieChartPanel(droneStatusService);
    }

    @Test
    void testAllDronesStatusPieChartPanelCreation() {
        assertNotNull(allDronesStatusPieChartPanel);
        assertEquals(BorderLayout.class, allDronesStatusPieChartPanel.getLayout().getClass());
    }
}
