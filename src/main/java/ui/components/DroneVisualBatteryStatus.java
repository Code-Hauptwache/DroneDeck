package main.java.ui.components;

import main.java.ui.dtos.DroneDto;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;

/**
 * The DroneVisualBatteryStatus class is a custom
 * JComponent that represents the battery status of a drone.
 */
public class DroneVisualBatteryStatus extends JPanel {
    private int batteryPercentage;
    private final JLabel iconLabel;
    private final JLabel batteryPercentageLabel;
    private final boolean isPercentageAvailable;

    /**
     * Creates a new DroneVisualBatteryStatus with the given DTO.
     *
     * @param dto The DTO containing the information to display.
     */
    public DroneVisualBatteryStatus(DroneDto dto) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setAlignmentY(Component.CENTER_ALIGNMENT);

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

        // Create a JLabel to display the percentage
        batteryPercentageLabel = new JLabel(
                isPercentageAvailable ? batteryPercentage + "%" : "N/A"
        );

        // Ensure consistent height by calculating the maximum height of components
        int maxHeight = Math.max(batteryIcon.getIconHeight(), batteryPercentageLabel.getPreferredSize().height);
        Dimension consistentSize = new Dimension(
                batteryIcon.getIconWidth() + batteryPercentageLabel.getPreferredSize().width + 10,
                maxHeight
        );

        iconLabel.setPreferredSize(new Dimension(batteryIcon.getIconWidth(), maxHeight));
        batteryPercentageLabel.setPreferredSize(new Dimension(batteryPercentageLabel.getPreferredSize().width, maxHeight));

        // Set the preferred, maximum, and minimum sizes for this panel
        setPreferredSize(consistentSize);
        setMaximumSize(consistentSize);
        setMinimumSize(consistentSize);

        // Add components to the main panel
        iconLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        batteryPercentageLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        add(iconLabel);
        add(Box.createHorizontalStrut(5)); // Add spacing between icon and label
        add(batteryPercentageLabel);
    }

    /**
     * Updates the battery percentage of the drone.
     *
     * @param g The Graphics object to paint the component.
     *          Swing calls this method to draw the component.
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

        // Subtract width for the right-side terminal
        int terminalWidth = 3;
        int fillableWidth = width - terminalWidth;

        // Compute fill width for 0-100% range
        double percentage = batteryPercentage == 0 ? 0 : Math.min(Math.max(batteryPercentage, 10), 100) / 100.0;
        int filledWidth = (int) (fillableWidth * percentage);

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

    /**
     * Sets the battery percentage of the drone.
     *
     * @param batteryStatus The current battery status of the drone.
     * @param batteryCapacity The maximum battery capacity of the drone.
     */
    public void setBatteryPercentage(int batteryStatus, double batteryCapacity) {
        // Update the battery percentage and repaint the component
        this.batteryPercentage = (int) Math.min(100.0, batteryStatus / batteryCapacity * 100);
        batteryPercentageLabel.setText(batteryPercentage + "%");
        repaint();
    }
}