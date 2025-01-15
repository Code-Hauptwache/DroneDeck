package main.java.ui.pages;

import main.java.entity.DroneTypeEntity;
import main.java.services.LocalSearch.LocalSearchService;
import main.java.services.LocalSearch.ILocalSearchService;
import main.java.ui.components.CardTemplate;
import main.java.ui.components.DroneCatalogCard;
import main.java.ui.dtos.DroneDto;
import main.java.services.ScrollPane.ScrollPaneService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

/**
 * The DroneCatalog class is a JPanel that displays a list of drone types.
 */
public class DroneCatalog extends JPanel {
    private static DroneCatalog instance;
    private final JPanel cardPanel;
    private final ILocalSearchService localSearchService;

    /**
     * The DroneCatalog method is the constructor for the DroneCatalog class.
     */
    private DroneCatalog() {
        // Use BorderLayout for main arrangement
        super(new BorderLayout());

        // Horizontal and vertical gaps for the GridLayout
        int gap = 30;

        // Add CardTemplate instances to the center panel using GridLayout
        cardPanel = new JPanel(new GridLayout(0, 1, gap, gap));

        // Initialize the local search service
        localSearchService = LocalSearchService.getCurrentInstance();

        // Load initial drone types
        updateDroneTypes("");

        // Add a resize listener to adjust the number of columns
        cardPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = cardPanel.getWidth();

                // Compute total width of a card (including gap)
                int cardTotalWidth = CardTemplate.CARD_WIDTH + gap;
                // Compute how many columns can fit
                int columns = Math.max(1, panelWidth / cardTotalWidth);

                // Reconfigure layout with that many columns
                GridLayout layout = (GridLayout) cardPanel.getLayout();
                layout.setColumns(columns);

                // Force layout update
                cardPanel.revalidate();
            }
        });

        // Make it scrollable (vertical only)
        JScrollPane scrollPane = ScrollPaneService.createScrollPane(cardPanel);

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Gets the instance of the DroneCatalog.
     *
     * @return the instance of the DroneCatalog
     */
    public static DroneCatalog getInstance() {
        if (instance == null) {
            instance = new DroneCatalog();
        }
        return instance;
    }

    /**
     * Updates the drone types displayed in the DroneCatalog.
     *
     * @param keyword the keyword to search for
     */
    public void updateDroneTypes(String keyword) {
        cardPanel.removeAll();

        // Load drone types from local file
        List<DroneTypeEntity> droneTypes = localSearchService.findDroneTypesByKeyword(keyword);

        if (droneTypes.isEmpty()) {
            JLabel noResultsLabel = new JLabel("No results", SwingConstants.CENTER);
            cardPanel.setLayout(new BorderLayout());
            cardPanel.add(noResultsLabel, BorderLayout.CENTER);
        } else {
            // Add the DroneCatalogCard to the cardPanel
            for (DroneTypeEntity droneType : droneTypes) {
                DroneDto dto = new DroneDto(
                        droneType.id,
                        droneType.typename,
                        droneType.manufacturer,
                        "N/A", // Status
                        0, // Battery status
                        droneType.battery_capacity,
                        0, // Speed
                        0.0, // Longitude
                        0.0, // Latitude
                        "N/A", // Serial number
                        0.0, // Carriage weight
                        "N/A", // Carriage type
                        droneType.max_carriage,
                        "N/A", // Last seen
                        droneType.weight,
                        droneType.max_speed,
                        droneType.control_range,
                        "N/A" // Timestamp
                );
                cardPanel.add(new DroneCatalogCard(dto), BorderLayout.WEST);
            }
        }

        cardPanel.revalidate();
        cardPanel.repaint();
    }
}