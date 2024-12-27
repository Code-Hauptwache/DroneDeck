package main.java.ui.components;

import main.java.ui.dtos.DashboardDroneCardDTO;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

/**
 * The DroneVisualBatteryStatus class is a custom
 * JComponent that represents the battery status of a drone.
 */
public class DroneVisualBatteryStatus extends JPanel {
    private final JLabel batteryIconLabel;
    private final JLabel batteryPercentageLabel;

    /**
     * Creates a new DroneVisualBatteryStatus with the given DTO.
     *
     * @param dto The DTO containing the information to display.
     */
    public DroneVisualBatteryStatus(DashboardDroneCardDTO dto) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // Load the SVG icon
        // Create the icon
        FlatSVGIcon batteryIcon = new FlatSVGIcon("EmptyBatteryIcon.svg", (float) 0.7);

        // Set a color filter to change the icon color to FlatLaf's default label color
        batteryIcon.setColorFilter(new FlatSVGIcon.ColorFilter() {
            @Override
            public Color filter(Color color) {
                return UIManager.getColor("Label.foreground");
            }
        });

        // Create a JLabel to hold the icon
        batteryIconLabel = new JLabel(batteryIcon);

        // Create a JLabel to hold the battery percentage
        batteryPercentageLabel = new JLabel(
            dto.getStatus() != null &&
            (dto.getStatus().equalsIgnoreCase("OF") || dto.getStatus().equalsIgnoreCase("IS"))
            ? "N/A"
            : (int) dto.getBatteryPercentage() + "%"
        );

        // Add the icon and battery percentage label to the panel
        add(batteryIconLabel);
        add(batteryPercentageLabel);
    }
}