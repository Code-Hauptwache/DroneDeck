package main.java.ui.pages;

import main.java.exceptions.DroneApiException;
import main.java.services.ApiToken.ApiTokenService;
import main.java.services.DroneApi.DroneApiService;
import main.java.services.DroneApi.IDroneApiService;
import main.java.services.DroneApi.dtos.DroneDynamics;
import main.java.services.DroneApi.dtos.DroneDynamicsResponse;
import main.java.services.ReverseGeocode.IReverseGeocodeService;
import main.java.services.ReverseGeocode.ReverseGeocodeService;
import main.java.ui.components.DroneStatus;
import main.java.ui.components.DroneVisualBatteryStatus;
import main.java.ui.components.EntryTimestampSelector;
import main.java.ui.components.InfoTooltip;
import main.java.ui.dtos.DroneDto;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The DroneDetailedView class is a JPanel that displays detailed information about a drone.
 */
public class DroneDetailedView extends JPanel {

    private static final Logger logger = Logger.getLogger(DroneDetailedView.class.getName());

    private static final String API_KEY = ApiTokenService.getApiToken();
    private final IDroneApiService droneApiService = new DroneApiService(API_KEY);
    private final IReverseGeocodeService reverseGeocodeService = new ReverseGeocodeService();
    private final JLabel speedLabel = new JLabel();
    private final JLabel locationLabel = new JLabel();
    private final JLabel batteryStatusLabel = new JLabel();
    private final JLabel lastSeenLabel = new JLabel();
    private final JLabel statusLabel = new JLabel();

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
        graphWrapper.add(buildGraphPanel(), BorderLayout.NORTH);
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
        speedLabel.setText((int) dto.getSpeed() + " km/h");
        locationLabel.setText(dto.getLocation() != null ? dto.getLocation() : "N/A");
        batteryStatusLabel.setText(dto.getBatteryPercentage() + "%");
        lastSeenLabel.setText(dto.getLastSeen() != null ? dto.getLastSeen() : "N/A");
        statusLabel.setText(dto.getStatus());

        return new Component[]{
                speedLabel,
                new JLabel(dto.isAverageSpeedSet() ? (int) dto.getAverageSpeed() + " km/h" : "N/A"),
                locationLabel,
                new JLabel(dto.isTravelDistanceSet() ? (int) dto.getTravelDistance() + " km" : "N/A"),
                new JLabel(dto.getCarriageWeight() >= 0 ? (int) dto.getCarriageWeight() + " g" : "N/A"),
                new JLabel(dto.getCarriageType() != null ? dto.getCarriageType() : "N/A"),
                lastSeenLabel,
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

    private JPanel buildGraphPanel() {
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

         try {
             DroneDynamicsResponse droneDynamicsResponse = droneApiService.getDroneDynamicsResponseByDroneId(dto.getId(), 1, 0);
             int droneDynamicsEntryCount = droneDynamicsResponse.getCount();

             JComponent droneStatus = new DroneStatus(dto);
             JComponent batteryStatus = new DroneVisualBatteryStatus(dto);
             EntryTimestampSelector entryTimestampSelector = new EntryTimestampSelector(droneDynamicsEntryCount);
             JComponent infoTooltip = new InfoTooltip("Data Timestamp: " + dto.getDataTimestamp());

             Arrays.asList(droneStatus, batteryStatus, infoTooltip).forEach(component -> {
                 component.setAlignmentY(Component.CENTER_ALIGNMENT);
                 component.setMaximumSize(component.getPreferredSize());
             });

             // Wrap the entryTimestampSelector in a panel and apply a negative left border
             JPanel entryTimestampSelectorWrapper = new JPanel();
             // Use an empty border with negative left inset to pull the component left
             entryTimestampSelectorWrapper.setBorder(BorderFactory.createEmptyBorder(0, -65, 0, 0));
             entryTimestampSelectorWrapper.add(entryTimestampSelector);

             statusPanel.add(droneStatus);
             statusPanel.add(Box.createHorizontalStrut(10));
             statusPanel.add(batteryStatus);
             statusPanel.add(entryTimestampSelectorWrapper);
             statusPanel.add(Box.createHorizontalGlue());
             statusPanel.add(infoTooltip);

             // Add ActionListener to update the UI when a new entry is selected
             entryTimestampSelector.addTimestampChangeListener(e -> {
                 int selectedIndex = entryTimestampSelector.getSelectedEntryIndex();
                 updateDroneDetails(dto, selectedIndex);
             });
         } catch (DroneApiException e) {
             logger.log(Level.SEVERE, "Failed to get drone dynamics for drone with ID: " + dto.getId(), e);
             // Handle the exception, e.g., show an error message or return an empty panel
             statusPanel.add(new JLabel("Failed to load drone dynamics."));
         }

         return statusPanel;
     }

    private void updateDroneDetails(DroneDto dto, int entryIndex) {
        try {
            DroneDynamics dynamics = droneApiService.getDroneDynamicsByEntryIndex(dto.getId(), entryIndex);
            // Update UI components with new data
            updateUIComponents(dynamics);
        } catch (DroneApiException e) {
            logger.log(Level.SEVERE, "Failed to update drone details for entry index: " + entryIndex, e);
        }
    }

    private void updateUIComponents(DroneDynamics dynamics) {
        // Update the UI components with the new data from dynamics
        speedLabel.setText(dynamics.speed + " km/h");
        locationLabel.setText(reverseGeocodeService.getCityAndCountry(dynamics.latitude, dynamics.longitude));
        batteryStatusLabel.setText(dynamics.battery_status + "%");
        lastSeenLabel.setText(dynamics.last_seen.toString());
        statusLabel.setText(dynamics.status);
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
