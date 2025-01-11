package main.java.ui.pages;

import main.java.controllers.DroneController;
import main.java.dao.LocalDroneDao;
import main.java.services.ScrollPane.ScrollPaneService;
import main.java.ui.components.DroneDashboardCard;
import main.java.ui.dtos.DroneDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class DroneDashboard extends JPanel {
    /**
     * The DroneDashboard class is a JPanel... (TODO)
     */
    public DroneDashboard() {
        // TODO: Implement the Drone Dashboard

        // *** THIS IS AN EXAMPLE OF HOW THE CardTemplate AND DroneDashboardCard CAN BE USED ***
        // Use BorderLayout for main arrangement
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
        JPanel cardPanel = new JPanel(new GridLayout(0, 1, gap, gap));

        // Create a fake DroneDto
        LocalDroneDao localDroneDao = new LocalDroneDao();
        DroneController droneController = new DroneController();
        List<DroneDto> testDtoList = droneController.getDroneThreads(localDroneDao.getDroneDataCount(), 0);

        // Add the fake DroneDashboardCard to the cardPanel
        for (DroneDto droneDto : testDtoList) {
            cardPanel.add(new DroneDashboardCard(droneDto));
        }

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
}
