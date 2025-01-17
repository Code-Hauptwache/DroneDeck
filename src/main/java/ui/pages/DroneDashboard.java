package main.java.ui.pages;

import main.java.controllers.DroneController;
import main.java.dao.ILocalDroneDao;
import main.java.dao.LocalDroneDao;
import main.java.entity.DroneEntity;
import main.java.services.DroneStatus.DroneStatusService;
import main.java.services.LocalSearch.ILocalSearchService;
import main.java.services.LocalSearch.LocalSearchService;
import main.java.services.ScrollPane.ScrollPaneService;
import main.java.ui.components.*;
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
    public static int componentGap = 30;
    private boolean isGridLayout = true;

    /**
     * The DroneDashboard method is the constructor for the DroneDashboard class.
     */
    private DroneDashboard() {
        super(new BorderLayout());

        // Create a DroneController and DroneStatusService instance
        DroneController droneController = new DroneController();
        DroneStatusService droneStatusService = new DroneStatusService(droneController); // Add this line

        // Create a JPanel to hold the graphical components
        JPanel graphPanel = new JPanel();
        graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.X_AXIS));

        // Create a pie chart panel and a graphical card
        AllDronesStatusPieChartPanel pieChartPanel = new AllDronesStatusPieChartPanel(droneStatusService);
        GraphicalCardTemplate allDronesStatusPieChartCard = new GraphicalCardTemplate("Drone Status", pieChartPanel);

        // Create a graphical card with a JLabel
        JLabel label = new JLabel("Graphical Components (TODO)", SwingConstants.CENTER);
        label.setForeground(Color.ORANGE);
        label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.ORANGE));
        GraphicalCardTemplate graphicalCard = new GraphicalCardTemplate("Graphical Card #2", label, 2, componentGap);

        graphPanel.add(Box.createHorizontalGlue());
        graphPanel.add(allDronesStatusPieChartCard);
        graphPanel.add(Box.createHorizontalStrut(componentGap));
        graphPanel.add(graphicalCard);
        graphPanel.add(Box.createHorizontalGlue());


        // Add padding to the bottom of graphPanel
        graphPanel.setBorder(BorderFactory.createEmptyBorder(componentGap, 0, componentGap, 0));

        // Add the graphPanel to the DroneDashboard
        add(graphPanel, BorderLayout.NORTH);

        // The rest of your logic for the drone cards stays the same
        cardPanel = new JPanel(new GridLayout(0, 1, componentGap, componentGap));
        localSearchService = LocalSearchService.getCurrentInstance();
        ILocalDroneDao localDroneDao = new LocalDroneDao();
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
                    int cardTotalWidth = CardTemplate.cardWidth + componentGap;

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
        cardPanel.removeAll();

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
            cardPanel.setLayout(new GridLayout(0, 1, componentGap, componentGap));
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