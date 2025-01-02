package main.java.ui.components;

import main.java.ui.dtos.DroneDashboardCardDto;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

/**
 * The DroneStatus class is a custom
 * JComponent that represents the status of a drone.
 */
public class DroneStatus extends JPanel {
    private final JLabel statusLabel;
    private final JLabel iconLabel;
    private final FontIcon statusIcon;

    /**
     * Creates a new DroneStatus with the given DTO.
     *
     * @param dto The DTO containing the information to display.
     */
    public DroneStatus(DroneDashboardCardDto dto) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // Initialize the FontIcon
        statusIcon = FontIcon.of(FontAwesomeSolid.CIRCLE);
        statusIcon.setIconSize(10);

        // Create a JLabel to hold the icon
        iconLabel = new JLabel(statusIcon);

        // Initialize status text label
        statusLabel = new JLabel();

        // Add the icon and status label to the panel
        add(iconLabel);
        add(statusLabel);

        updateStatus(dto.getStatus());
    }

    /**
     * Updates the status of the drone.
     *
     * @param status The new status of the drone.
     */
    public void updateStatus(String status) {
        switch (status) {
            case "ON":
                statusIcon.setIconColor(Color.GREEN);
                statusLabel.setText("Online");
                break;
            case "OF":
                statusIcon.setIconColor(Color.RED);
                statusLabel.setText("Offline");
                break;
            case "IS":
                statusIcon.setIconColor(Color.ORANGE);
                statusLabel.setText("Issue");
                break;
            default:
                statusIcon.setIconColor(Color.GRAY);
                statusLabel.setText("Unknown");
                break;
        }
        // Force the icon label to repaint with the new color
        iconLabel.repaint();
    }
}