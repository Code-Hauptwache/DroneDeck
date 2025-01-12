package main.java.ui.pages;

import main.java.controllers.DroneController;
import main.java.controllers.IDroneController;
import main.java.dao.ILocalDroneDao;
import main.java.dao.LocalDroneDao;
import main.java.entity.DroneEntity;
import main.java.services.LocalSearch.ILocalSearchService;
import main.java.services.LocalSearch.LocalSearchService;
import main.java.services.ScrollPane.ScrollPaneService;
import main.java.ui.components.DroneDashboardCard;
import main.java.ui.dtos.DroneDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class DroneDashboard extends JPanel {
    private static DroneDashboard instance;
    private final JPanel cardPanel;
    private final ILocalSearchService localSearchService;
    private final List<DroneDto> fetchedDrones;

    /**
     * The DroneDashboard class is a JPanel... (TODO)
     */
    private DroneDashboard() {
        super(new BorderLayout());

        // Horizontal and vertical gaps for the GridLayout
        int gap = 30;

        // This is a placeholder in orange for the graphical components of the drone dashboard
        JLabel label = new JLabel("Graphical Components (TODO)", SwingConstants.CENTER);
        label.setForeground(Color.ORANGE);
        label.setPreferredSize(new Dimension(0, 300 + gap));
        label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.ORANGE));
        add(label, BorderLayout.NORTH);

        // Add CardTemplate instances to the center panel using GridLayout
        cardPanel = new JPanel(new GridLayout(0, 1, gap, gap));

        // Initialize the local search service
        localSearchService = LocalSearchService.getCurrentInstance();

        ILocalDroneDao localDroneDao = new LocalDroneDao();
        IDroneController droneController = new DroneController();
        fetchedDrones = droneController.getDroneThreads(localDroneDao.getDroneDataCount(), 0);


        // Load initial drones
        updateDrones("");



        // Add a resize listener to adjust the number of columns
        cardPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int panelWidth = cardPanel.getWidth();

                int cardTotalWidth = 250 +  gap; // 250 for card + 10 for right gap
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

    public void updateDrones(String keyword) {
        cardPanel.removeAll();

        // Load drones from local file
        List<DroneEntity> drones = localSearchService.findDronesByKeyword(keyword);

        // Filter fetchedDrones based on the IDs of the drones in the drones list
        List<DroneDto> filteredDrones = fetchedDrones.stream()
                .filter(dto -> drones.stream().anyMatch(drone -> drone.getId() == dto.getId()))
                .toList();

        // Add the DroneDashboardCard to the cardPanel
        for (DroneDto drone : filteredDrones) {
            cardPanel.add(new DroneDashboardCard(drone));
        }

        cardPanel.revalidate();
        cardPanel.repaint();
    }
}
