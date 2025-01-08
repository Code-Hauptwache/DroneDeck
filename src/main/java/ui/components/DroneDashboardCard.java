package main.java.ui.components;

import main.java.ui.MainPanel;
import main.java.ui.dtos.DroneDto;
import main.java.ui.pages.DroneDetailedView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * The DroneDashboardCard class is a custom JComponent that represents a card
 * with information about a drone. It is used in the dashboard to display
 * information about a drone.
 */
public class DroneDashboardCard extends JComponent {
    private final DroneDto dto;

    /**
     * Creates a new DroneDashboardCard with the given DTO.
     *
     * @param dto The DTO containing the information to display.
     */
    public DroneDashboardCard(DroneDto dto) {
        setLayout(new BorderLayout());
        this.dto = dto;

        // Main content container with GridLayout
        JPanel contentContainer = new JPanel(new GridLayout(5, 2, 0, 4));

        // Add remaining standard label-value pairs
        Component[] leftContent = {
                new DroneStatus(dto),
                new JLabel("Speed"),
                new JLabel("Top Speed"),
                new JLabel("Location"),
                new JLabel("Serial")
        };

        Component[] rightContent = {
                new DroneVisualBatteryStatus(dto),
                new JLabel((int) dto.getSpeed() + " km/h"),
                new JLabel((int) dto.getMaxSpeed() + " km/h"),
                // new JLabel(dto.getLocation() != null ? dto.getLocation() + " km" : "N/A"),
                new JLabel("N/A"), // Location is temporarily disabled
                new JLabel(dto.getSerialNumber())
        };

        for (int i = 0; i < leftContent.length; i++) {
            contentContainer.add(leftContent[i]);
            contentContainer.add(rightContent[i]);
        }

        // Create card with the content
        CardTemplate card = new CardTemplate(
                dto.getTypeName(),
                dto.getManufacturer(),
                contentContainer
        );
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set cursor to pointer when hovering

        // Set the preferred, minimum, and maximum sizes to match the CardTemplate
        Dimension cardSize = card.getPreferredSize();
        setPreferredSize(cardSize);
        setMinimumSize(cardSize);
        setMaximumSize(cardSize);

        add(card, BorderLayout.CENTER);

        // Add a mouse listener that shows the overlay on click
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDetailOverlay();
            }
        });
    }

    private JPanel createLoadingPanel() {
        JPanel loadingPanel = new JPanel();
        loadingPanel.setLayout(new BorderLayout());
        JLabel loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
        loadingPanel.add(loadingLabel, BorderLayout.CENTER);
        return loadingPanel;
    }

    private void showDetailOverlay() {
        MainPanel mainPanel = (MainPanel) SwingUtilities.getAncestorOfClass(MainPanel.class, this);
        if (mainPanel == null) {
            return;
        }

        JLayeredPane layeredPane = mainPanel.getMainLayeredPane();

        // Create the overlay panel
        JPanel overlayPanel = new JPanel(new BorderLayout());

        // Create and add the loading panel to the overlay
        JPanel loadingPanel = createLoadingPanel();
        overlayPanel.add(loadingPanel, BorderLayout.CENTER);

        // Add the overlay panel to the layered pane
        layeredPane.add(overlayPanel, JLayeredPane.MODAL_LAYER);

        // Activate a glass pane to block events during loading
        Component glassPane = mainPanel.getRootPane().getGlassPane();
        glassPane.setVisible(true);

        // Repaint and revalidate to ensure it displays properly
        layeredPane.revalidate();
        layeredPane.repaint();

        // Load the detailed view asynchronously
        SwingUtilities.invokeLater(() -> {
            DroneDetailedView detailView = new DroneDetailedView(dto, overlayPanel);

            // Remove loading panel and add detailed view
            overlayPanel.remove(loadingPanel);
            overlayPanel.add(detailView, BorderLayout.CENTER);

            // Deactivate the glass pane
            glassPane.setVisible(false);

            overlayPanel.revalidate();
            overlayPanel.repaint();
        });
    }

    private String getTravelDistanceString(DroneDto dto) {
        return (dto.getTravelDistance() != null && !dto.getTravelDistance().toString().isEmpty())
                ? dto.getTravelDistance().toString()
                : "N/A";
    }
}
