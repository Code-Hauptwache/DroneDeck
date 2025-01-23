package main.java.ui.components;

import main.java.controllers.DroneController;
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
        DroneController droneController = new DroneController();
        DroneStatusService droneStatusService = new DroneStatusService(droneController) {
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