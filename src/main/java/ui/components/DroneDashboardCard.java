package main.java.ui.components;

import main.java.ui.MainPanel;
import main.java.ui.dtos.DroneDashboardCardDto;
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
    private final DroneDashboardCardDto dto;

    /**
     * Creates a new DroneDashboardCard with the given DTO.
     *
     * @param dto The DTO containing the information to display.
     */
    public DroneDashboardCard(DroneDashboardCardDto dto) {
        this.dto = dto;
        setLayout(new BorderLayout());

        // Main content container with GridLayout
        JPanel contentContainer = new JPanel(new GridLayout(5, 2, 0, 4));

        // Add remaining standard label-value pairs
        Component[] leftContent = {
                new DroneStatus(dto),
                new JLabel("Speed"),
                new JLabel("Location"),
                new JLabel("Traveled"),
                new JLabel("Serial")
        };

        Component[] rightContent = {
                new DroneVisualBatteryStatus(dto),
                new JLabel((int) dto.getSpeed() + " km/h"),
                new JLabel(dto.getLocation() != null ? dto.getLocation() + " km" : "N/A"),
                new JLabel(
                        (dto.getTravelDistance() != null && !dto.getTravelDistance().toString().isEmpty())
                                ? dto.getTravelDistance().toString()
                                : "N/A"
                ),
                new JLabel(dto.getSerialNumber())
        };

        for (int i = 0; i < leftContent.length; i++) {
            contentContainer.add(leftContent[i]);
            contentContainer.add(rightContent[i]);
        }

        // Create card with the content
        CardTemplate card = new CardTemplate(
                dto.getTypename(),
                dto.getManufacture(),
                contentContainer
        );
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Set cursor to pointer when hovering
        add(card, BorderLayout.CENTER);

        // Add a mouse listener that shows the overlay on click
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showDetailOverlay();
            }
        });
    }

    private void showDetailOverlay() {
        // Find the MainPanel and the JLayeredPane
        MainPanel mainPanel = (MainPanel) SwingUtilities.getAncestorOfClass(MainPanel.class, this);
        if (mainPanel == null) {
            return;
        }

        // Add the overlay panel to the JLayeredPane
        JLayeredPane layeredPane = mainPanel.getMainLayeredPane();

        // Create the overlay panel
        JPanel overlayPanel = getOverlayPanel();

        // Add the overlay panel to the layered pane
        layeredPane.add(overlayPanel, JLayeredPane.MODAL_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    private JPanel getOverlayPanel() {
        JPanel overlayPanel = new JPanel(new BorderLayout());
        overlayPanel.setBackground(new Color(0, 0, 0, 100)); // slight transparency
        DroneDetailedView detailView = new DroneDetailedView(dto, overlayPanel);
        overlayPanel.add(detailView, BorderLayout.CENTER);
        return overlayPanel;
    }
}
