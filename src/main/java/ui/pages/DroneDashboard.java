package main.java.ui.pages;

import main.java.controllers.DroneController;
import main.java.controllers.IDroneController;
import main.java.dao.ILocalDroneDao;
import main.java.dao.LocalDroneDao;
import main.java.entity.DroneEntity;
import main.java.services.LocalSearch.ILocalSearchService;
import main.java.services.LocalSearch.LocalSearchService;
import main.java.services.ScrollPane.ScrollPaneService;
import main.java.ui.components.CardTemplate;
import main.java.ui.components.DroneDashboardCard;
import main.java.ui.dtos.DroneDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

/**
 * The DroneDashboard class is a JPanel that displays a list of drones.
 */
public class DroneDashboard extends JPanel {
    private static DroneDashboard instance;
    private final JPanel cardPanel;
    private final ILocalSearchService localSearchService;
    private final List<DroneDto> fetchedDrones;
    private final int gridGap = 30;
    private boolean isGridLayout = true; // Track the current layout type

    /**
     * The DroneDashboard method is the constructor for the DroneDashboard class.
     */
    private DroneDashboard() {
        super(new BorderLayout());

        // Horizontal and vertical gaps for the GridLayout

        // This is a placeholder in orange for the graphical components of the drone dashboard
        JLabel label = new JLabel("Graphical Components (TODO)", SwingConstants.CENTER);
        label.setForeground(Color.ORANGE);
        label.setPreferredSize(new Dimension(0, 300 + gridGap));
        label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.ORANGE));
        add(label, BorderLayout.NORTH);

        // Add CardTemplate instances to the center panel using GridLayout
        cardPanel = new JPanel(new GridLayout(0, 1, gridGap, gridGap));

        // Initialize the local search service
        localSearchService = LocalSearchService.getCurrentInstance();

        ILocalDroneDao localDroneDao = new LocalDroneDao();
        IDroneController droneController = new DroneController();
        fetchedDrones = droneController.getDroneThreads(localDroneDao.getDroneDataCount(), 0);

        // Load initial drones
        updateDrones("");

        // Add a resize listener to adjust the number of columns
        cardPanel.addComponentListener(new ComponentAdapter() {
            /**
             * Invoked when the component's size changes.
             *
             * @param e the component event
             */
            @Override
            public void componentResized(ComponentEvent e) {
                // Check if the layout is currently GridLayout
                if (isGridLayout) {
                    int panelWidth = cardPanel.getWidth();

                    // Compute total width of a card (including gap)
                    int cardTotalWidth = CardTemplate.CARD_WIDTH + gridGap;
                    // Compute how many columns can fit
                    int columns = Math.max(1, panelWidth / cardTotalWidth);

                    // Ensure the layout is GridLayout before casting
                    LayoutManager layout = cardPanel.getLayout();
                    if (layout instanceof GridLayout) {
                        ((GridLayout) layout).setColumns(columns);
                        // Force layout update
                        cardPanel.revalidate();
                    }
                }
            }
        });

        // Make it scrollable (vertical only)
        JScrollPane scrollPane = ScrollPaneService.createScrollPane(cardPanel);

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Gets the instance of the DroneDashboard.
     *
     * @return the instance of the DroneDashboard
     */
    public static DroneDashboard getInstance() {
        if (instance == null) {
            instance = new DroneDashboard();
        }
        return instance;
    }

    /**
     * Updates the drones displayed in the DroneDashboard.
     *
     * @param keyword the keyword to search for
     */
    public void updateDrones(String keyword) {
        cardPanel.removeAll(); // Remove all components from the cardPanel

        // Load drones from local file
        List<DroneEntity> drones = localSearchService.findDronesByKeyword(keyword);

        if (drones.isEmpty()) {
            // Switch to BorderLayout to display "No results"
            cardPanel.setLayout(new BorderLayout());
            JLabel noResultsLabel = new JLabel("No results", SwingConstants.CENTER);
            cardPanel.add(noResultsLabel, BorderLayout.CENTER);
            isGridLayout = false; // Mark that the layout is now BorderLayout
        } else {
            // Reset layout to GridLayout for displaying drone cards
            cardPanel.setLayout(new GridLayout(0, 1, gridGap, gridGap));
            isGridLayout = true; // Mark that the layout is now GridLayout

            // Filter fetchedDrones based on the IDs of the drones in the drones list
            List<DroneDto> filteredDrones = fetchedDrones.stream()
                    .filter(dto -> drones.stream().anyMatch(drone -> drone.getId() == dto.getId()))
                    .toList();

            // Add the DroneDashboardCard to the cardPanel
            for (DroneDto drone : filteredDrones) {
                cardPanel.add(new DroneDashboardCard(drone));
            }
        }

        // Revalidate and repaint the cardPanel to apply changes
        cardPanel.revalidate();
        cardPanel.repaint();
    }
}
