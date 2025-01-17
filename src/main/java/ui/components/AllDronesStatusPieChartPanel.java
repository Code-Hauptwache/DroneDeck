package main.java.ui.components;

import main.java.services.DroneStatus.DroneStatusService;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * The AllDronesStatusPieChartPanel class is a JPanel that displays a pie chart of all drones' statuses.
 */
public class AllDronesStatusPieChartPanel extends JPanel {
    /**
     * Constructor for AllDronesStatusPieChartPanel.
     *
     * @param droneStatusService the drone status service
     */
    public AllDronesStatusPieChartPanel(DroneStatusService droneStatusService) {
        super(new BorderLayout()); // Set a layout manager

        PieChartPanel.Slice slice1 = new PieChartPanel.Slice(
                droneStatusService.getOnlineCount(), Color.GREEN, "Online");
        PieChartPanel.Slice slice2 = new PieChartPanel.Slice(
                droneStatusService.getIssueCount(), Color.ORANGE, "Issue");
        PieChartPanel.Slice slice3 = new PieChartPanel.Slice(
                droneStatusService.getOfflineCount(), Color.RED, "Offline");

        PieChartPanel pieChartPanel = new PieChartPanel(Arrays.asList(slice1, slice2, slice3));

        GraphicalCardTemplate allDronesStatusPieChartCard = new GraphicalCardTemplate("Drone Status", pieChartPanel);

        add(allDronesStatusPieChartCard, BorderLayout.CENTER); // Add to the center of the card
    }
}
