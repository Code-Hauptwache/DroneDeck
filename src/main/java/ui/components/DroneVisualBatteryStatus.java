package main.java.ui.components;

import main.java.ui.dtos.DashboardDroneCardDTO;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

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

        // Initialize the FontIcon
        FontIcon batteryIcon = FontIcon.of(FontAwesomeSolid.BATTERY_EMPTY, 20, Color.ORANGE);

        // Create a JLabel to hold the icon
        batteryIconLabel = new JLabel(batteryIcon);

        // Initialize battery percentage text label
        batteryPercentageLabel = new JLabel((int) dto.getBatteryPercentage() + "%");

        // Add the icon and battery percentage label to the panel
        add(batteryIconLabel);
        add(batteryPercentageLabel);
    }
}
