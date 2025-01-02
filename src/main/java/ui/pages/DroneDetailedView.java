package main.java.ui.pages;

import main.java.ui.components.DroneStatus;
import main.java.ui.components.DroneVisualBatteryStatus;
import main.java.ui.components.InfoTooltip;
import main.java.ui.dtos.DroneDashboardDto;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

public class DroneDetailedView extends JPanel {

    public DroneDetailedView(DroneDashboardDto dto, JPanel overlayPanel) {
        super(new BorderLayout());

        // 1) Build & add top bar
        add(buildTopBar(dto, overlayPanel), BorderLayout.NORTH);

        // 2) Build center panel (wrapped so it hugs top)
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(buildCenterPanel(dto), BorderLayout.NORTH);
        add(wrapper, BorderLayout.CENTER);
    }

    // =======================
    //       Top Bar
    // =======================

    private JPanel buildTopBar(DroneDashboardDto dto, JPanel overlayPanel) {
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Create "Back" button
        JLabel backButton = buildBackButton(overlayPanel);
        topBar.add(backButton);
        topBar.add(Box.createHorizontalStrut(20)); // small space

        // Create "title pane" for name + manufacturer
        JPanel titlePane = new JPanel();
        titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.Y_AXIS));
        titlePane.add(buildTitleLabel(dto.getTypeName()));
        titlePane.add(buildSubtitleLabel(dto.getManufacturer()));
        topBar.add(titlePane);

        return topBar;
    }

    private JLabel buildBackButton(JPanel overlayPanel) {
        FontIcon backIcon = FontIcon.of(FontAwesomeSolid.CHEVRON_LEFT, 20, UIManager.getColor("Label.foreground"));
        JLabel backButton = new JLabel(backIcon);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addBackButtonListener(backButton, overlayPanel);
        return backButton;
    }

    private JLabel buildTitleLabel(String text) {
        JLabel title = new JLabel(text);
        title.putClientProperty("FlatLaf.styleClass", "h2");
        title.setHorizontalAlignment(SwingConstants.LEFT);
        return title;
    }

    private JLabel buildSubtitleLabel(String text) {
        JLabel subtitle = new JLabel(text);
        subtitle.putClientProperty("FlatLaf.styleClass", "medium");
        subtitle.setForeground(UIManager.getColor("Label.disabledForeground"));
        subtitle.setHorizontalAlignment(SwingConstants.LEFT);
        return subtitle;
    }

    // =======================
    //      Center Panel
    // =======================

    private JPanel buildCenterPanel(DroneDashboardDto dto) {
        // Main container
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagLayout layout = (GridBagLayout) centerPanel.getLayout();
        layout.columnWeights = new double[]{1.0, 2.0, 1.0, 2.0};

        // Common constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // 1) Column 1 (indexes for rows: 0..7)
        Component[] column1 = {
                buildStatusPanel(dto),
                createDisabledLabel("Speed"),
                createDisabledLabel("Average Speed"),
                createDisabledLabel("Location"),
                createDisabledLabel("Traveled"),
                createDisabledLabel("Carriage Weight"),
                createDisabledLabel("Carriage Type"),
                createDisabledLabel("Last Seen"),
                createDisabledLabel("Serial Number")
        };
        addColumn(centerPanel, column1, 0, gbc);

        // 2) Column 2
        Component[] column2 = {
                new InfoTooltip("Data Timestamp: " + dto.getDataTimestamp()),
                new JLabel((int) dto.getSpeed() + " km/h"),
                new JLabel(dto.getAverageSpeed() != null ? (int) dto.getAverageSpeed() + " km/h" : "N/A"),
                new JLabel(dto.getLocation() != null ? dto.getLocation() : "N/A"),
                new JLabel((dto.getTravelDistance() != null && !dto.getTravelDistance().toString().isEmpty()) ? dto.getTravelDistance().toString() + " m" : "N/A"),
                new JLabel(dto.getCarriageWeight() > 0 ? (int) dto.getCarriageWeight() + " g" : "N/A"),
                new JLabel(dto.getCarriageType() != null ? dto.getCarriageType() : "N/A"),
                new JLabel(dto.getLastSeen() != null ? dto.getLastSeen() : "N/A"),
                new JLabel(dto.getSerialNumber())
        };
        addColumn(centerPanel, column2, 1, gbc);

        // 3) Column 3
        Component[] column3 = {
                new JLabel(""), // empty row to align with Column 2's tooltip
                createDisabledLabel("Weight"),
                createDisabledLabel("Top Speed"),
                createDisabledLabel("Battery Capacity"),
                createDisabledLabel("Control Range"),
                createDisabledLabel("Max Carriage")
        };
        addColumn(centerPanel, column3, 2, gbc);

        // 4) Column 4
        Component[] column4 = {
                new JLabel(""), // empty row
                new JLabel((int) dto.getWeight() + " g"),
                new JLabel((int) dto.getMaxSpeed() + " km/h"),
                new JLabel((int) dto.getBatteryCapacity() + " mAh"),
                new JLabel((int) dto.getControlRange() + " m"),
                new JLabel((int) dto.getMaxCarriageWeight() + " g")
        };
        addColumn(centerPanel, column4, 3, gbc);

        return centerPanel;
    }

    /** Helper: adds the given array of components as rows in a single column. */
    private void addColumn(JPanel panel, Component[] components, int columnIndex, GridBagConstraints gbc) {
        for (int row = 0; row < components.length; row++) {
            gbc.gridx = columnIndex;
            gbc.gridy = row;
            // If it’s the first row in this column, and you want special alignment:
            if (row == 0 && columnIndex == 1) {
                gbc.anchor = GridBagConstraints.EAST; // align the tooltip to the right
            } else {
                gbc.anchor = GridBagConstraints.WEST;
            }
            panel.add(components[row], gbc);
        }
    }

    private JPanel buildStatusPanel(DroneDashboardDto dto) {
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));

        // Add "drone status" + horizontal gap + "battery"
        statusPanel.add(new DroneStatus(dto));
        statusPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        statusPanel.add(new DroneVisualBatteryStatus(dto));

        return statusPanel;
    }

    // =======================
    //   Generic Helpers
    // =======================

    /** Adds a listener to the back button that removes the overlay panel. */
    private static void addBackButtonListener(JLabel backButton, JPanel overlayPanel) {
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Container parent = overlayPanel.getParent();
                if (parent != null) {
                    parent.remove(overlayPanel);
                    parent.revalidate();
                    parent.repaint();
                }
            }
        });
    }

    /** Creates a JLabel with a disabled-foreground color and left alignment. */
    private JLabel createDisabledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(UIManager.getColor("Label.disabledForeground"));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }
}
