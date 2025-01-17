package main.java.examples;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.jthemedetecor.OsThemeDetector;
import main.java.controllers.DroneController;
import main.java.services.DroneStatus.DroneStatusService;
import main.java.ui.components.AllDronesStatusPieChartPanel;

import javax.swing.*;
import java.awt.*;

public class AllDronesStatusPieChartCardExample {
    public static void main(String[] args) {
        // Set up FlatLaf look and feel
        FlatLaf.registerCustomDefaultsSource("main.java.ui.themes");

        OsThemeDetector detector = OsThemeDetector.getDetector();
        boolean isDarkThemeUsed = detector.isDark();
        if (isDarkThemeUsed) {
            FlatDarkLaf.setup();
        } else {
            FlatLightLaf.setup();
        }

        // Set up the frame
        JFrame frame = new JFrame("All Drones Status Pie Chart Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Initialize the DroneController and DroneStatusService
        DroneController droneController = new DroneController();
        DroneStatusService droneStatusService = new DroneStatusService(droneController);

        // Create the AllDronesStatusPieChartCard
        AllDronesStatusPieChartPanel pieChartCard = new AllDronesStatusPieChartPanel(droneStatusService);

        // Add the pie chart card to the frame
        frame.add(pieChartCard, BorderLayout.CENTER);

        // Display the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}