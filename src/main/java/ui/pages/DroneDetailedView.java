package main.java.ui.pages;

import main.java.ui.components.DroneStatus;
import main.java.ui.components.DroneVisualBatteryStatus;
import main.java.ui.components.InfoTooltip;
import main.java.ui.dtos.DroneDto;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

/**
 * The DroneDetailedView class is a JPanel that displays detailed information about a drone.
 */
public class DroneDetailedView extends JPanel {

    /**
     * Creates a new DroneDetailedView instance.
     *
     * @param dto          the DroneDto containing the drone data
     * @param overlayPanel the overlay panel to remove when going back
     */
    public DroneDetailedView(DroneDto dto, JPanel overlayPanel) {
        super(new BorderLayout());

        // Build and add the top bar
        add(buildTopBar(dto, overlayPanel), BorderLayout.NORTH);

        // Build the center panel and graph panel within the wrapper
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(buildCenterPanel(dto), BorderLayout.NORTH);
        JPanel graphWrapper = new JPanel(new BorderLayout());
        graphWrapper.add(buildGraphPanel(dto), BorderLayout.NORTH);
        wrapper.add(buildCenterPanel(dto), BorderLayout.NORTH);
        wrapper.add(graphWrapper, BorderLayout.CENTER);

        // Add the wrapper to the center of the main layout
        add(wrapper, BorderLayout.CENTER);

        // Add a listener to capture side mouse button (Button 4) to go back
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == 4) {
                    goBack(overlayPanel);
                }
            }
        });
    }

    // =======================
    //       Top Bar
    // =======================

    private JPanel buildTopBar(DroneDto dto, JPanel overlayPanel) {
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));

        topBar.add(buildBackButton(overlayPanel)); // Back button
        topBar.add(Box.createHorizontalStrut(20)); // Spacer

        // Title pane with name and manufacturer
        JPanel titlePane = new JPanel();
        titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.Y_AXIS));
        titlePane.add(createLabel(dto.getTypeName()));
        titlePane.add(createLabel(dto.getManufacturer(), "medium", UIManager.getColor("Label.disabledForeground")));
        topBar.add(titlePane);

        return topBar;
    }

    private JLabel buildBackButton(JPanel overlayPanel) {
        JLabel backButton = new JLabel(FontIcon.of(FontAwesomeSolid.CHEVRON_LEFT, 20, UIManager.getColor("Label.foreground")));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                goBack(overlayPanel);
            }
        });
        return backButton;
    }

    private JLabel createLabel(String text) {
        return createLabel(text, "h2", UIManager.getColor("Label.foreground"));
    }

    private JLabel createLabel(String text, String styleClass, Color color) {
        JLabel label = new JLabel(text);
        label.putClientProperty("FlatLaf.styleClass", styleClass);
        label.setForeground(color);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }

    // =======================
    //      Center Panel
    // =======================

    private JPanel buildCenterPanel(DroneDto dto) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(buildStatusPanel(dto), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();

        // Populate columns with data
        addColumn(centerPanel, createColumnLabels(new String[]{"Speed", "Average Speed", "Location", "Traveled", "Carriage Weight", "Carriage Type", "Last Seen", "Serial Number"}), 0, gbc);
        addColumn(centerPanel, createColumnData(dto), 1, gbc);
        addColumn(centerPanel, createColumnLabels(new String[]{"Top Speed", "Weight", "Battery Capacity", "Control Range", "Max Carriage"}), 2, gbc);
        addColumn(centerPanel, createAdditionalData(dto), 3, gbc);

        wrapper.add(centerPanel, BorderLayout.CENTER);
        return wrapper;
    }

    private GridBagConstraints createDefaultGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        return gbc;
    }

    private Component[] createColumnLabels(String[] labels) {
        return Arrays.stream(labels)
                .map(this::createDisabledLabel)
                .toArray(Component[]::new);
    }


    private Component[] createColumnData(DroneDto dto) {
        return new Component[]{
                new JLabel((int) dto.getSpeed() + " km/h"),
                new JLabel(dto.getAverageSpeed() != null ? (int) dto.getAverageSpeed() + " km/h" : "N/A"),
                // new JLabel(dto.getLocation() != null ? dto.getLocation() + " km" : "N/A"),
                new JLabel("N/A"), // Location is temporarily disabled
                new JLabel(dto.isTravelDistanceSet() ? (int) dto.getTravelDistance() + " km" : "N/A"),
                new JLabel(dto.getCarriageWeight() > 0 ? (int) dto.getCarriageWeight() + " g" : "N/A"),
                new JLabel(dto.getCarriageType() != null ? dto.getCarriageType() : "N/A"),
                new JLabel(dto.getLastSeen() != null ? dto.getLastSeen() : "N/A"),
                new JLabel(dto.getSerialNumber())
        };
    }

    private Component[] createAdditionalData(DroneDto dto) {
        return new Component[]{
                new JLabel((int) dto.getMaxSpeed() + " km/h"),
                new JLabel((int) dto.getWeight() + " g"),
                new JLabel((int) dto.getBatteryCapacity() + " mAh"),
                new JLabel((int) dto.getControlRange() + " m"),
                new JLabel((int) dto.getMaxCarriageWeight() + " g")
        };
    }

    private JLabel createDisabledLabel(String text) {
        return createLabel(text, "medium", UIManager.getColor("Label.disabledForeground"));
    }

    private void addColumn(JPanel panel, Component[] components, int columnIndex, GridBagConstraints gbc) {
        for (int row = 0; row < components.length; row++) {
            gbc.gridx = columnIndex;
            gbc.gridy = row;
            panel.add(components[row], gbc);
        }
    }

    // =======================
    //   Bottom Graph Panel
    // =======================

    private JPanel buildGraphPanel(DroneDto dto) {
        JPanel graphPanel = new JPanel(new BorderLayout());
        graphPanel.setPreferredSize(new Dimension(0, 330));
        JLabel placeholder = new JLabel("Graphical Components (TODO)", SwingConstants.CENTER);
        placeholder.setForeground(Color.ORANGE);
        placeholder.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.ORANGE));
        graphPanel.add(placeholder);
        return graphPanel;
    }

    // =======================
    //   Status Panel
    // =======================

    private JPanel buildStatusPanel(DroneDto dto) {
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

        JComponent droneStatus = new DroneStatus(dto);
        JComponent batteryStatus = new DroneVisualBatteryStatus(dto);
        JComponent infoTooltip = new InfoTooltip("Data Timestamp: " + dto.getDataTimestamp());

        Arrays.asList(droneStatus, batteryStatus, infoTooltip).forEach(component -> {
            component.setAlignmentY(Component.CENTER_ALIGNMENT);
            component.setMaximumSize(component.getPreferredSize());
        });

        statusPanel.add(droneStatus);
        statusPanel.add(Box.createHorizontalStrut(10));
        statusPanel.add(batteryStatus);
        statusPanel.add(Box.createHorizontalStrut(95));
        statusPanel.add(infoTooltip);

        return statusPanel;
    }

    // =======================
    //   Navigation Helper
    // =======================

    private static void goBack(JPanel overlayPanel) {
        Container parent = overlayPanel.getParent();
        if (parent != null) {
            parent.remove(overlayPanel);
            parent.revalidate();
            parent.repaint();
        }
    }
}
