package main.java.ui.components;

import main.java.ui.dtos.DashboardDroneCardDto;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

/**
 * The DroneVisualBatteryStatus class is a custom
 * JComponent that represents the battery status of a drone.
 */
public class DroneVisualBatteryStatus extends JPanel {
    private final int batteryPercentage;
    private final JLabel iconLabel;
    private final boolean isPercentageAvailable;

    /**
     * Creates a new DroneVisualBatteryStatus with the given DTO.
     *
     * @param dto The DTO containing the information to display.
     */
    public DroneVisualBatteryStatus(DashboardDroneCardDto dto) {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // Create a FlatSVGIcon for the battery outline
        FlatSVGIcon batteryIcon = new FlatSVGIcon("EmptyBatteryIcon.svg", (float) 0.75);

        // Set a color filter to change the icon color to FlatLaf's default label color
        batteryIcon.setColorFilter(new FlatSVGIcon.ColorFilter() {
            @Override
            public Color filter(Color color) {
                return UIManager.getColor("Label.foreground");
            }
        });
        iconLabel = new JLabel(batteryIcon);

        // Set the battery percentage
        isPercentageAvailable = dto.getStatus() == null || !dto.getStatus().equalsIgnoreCase("OF");
        batteryPercentage = isPercentageAvailable ? (int) dto.getBatteryPercentage() : 0;
        setPreferredSize(new Dimension(batteryIcon.getIconWidth(), batteryIcon.getIconHeight()));

        // Create a JLabel to display the percentage
        JLabel batteryPercentageLabel = new JLabel(
                isPercentageAvailable ? batteryPercentage + "%" : "N/A"
        );

        // Add components to the main panel
        add(iconLabel);
        add(batteryPercentageLabel);
    }

    /**
     * Updates the battery percentage of the drone.
     *
     * @param batteryPercentage The new battery percentage of the drone.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!isPercentageAvailable) {
            return; // Do not draw the filling if the percentage is not available
        }

        // Get the icon's bounds for proper overlay placement
        Rectangle bounds = iconLabel.getBounds();
        int x = bounds.x + 3; // Adjust for padding
        int y = bounds.y + 3;
        int width = bounds.width - 6;
        int height = bounds.height - 6;

        // Calculate the filled battery level width
        int filledWidth = (int) (width * (batteryPercentage / 100.0));

        // Set the color based on the battery percentage
        Color fillColor;
        if (batteryPercentage > 50) {
            fillColor = Color.GREEN;
        } else if (batteryPercentage > 20) {
            fillColor = Color.ORANGE;
        } else {
            fillColor = Color.RED;
        }

        // Draw the filled battery level with rounded corners
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(fillColor);
        g2d.fillRoundRect(x, y, filledWidth, height, 4, 4); // 4 is the arc width and height for rounded corners
    }
}