package main.java.ui.pages;

import main.java.services.ScrollPane.ScrollPaneService;
import main.java.ui.components.CardTemplate;
import main.java.ui.components.DroneDashboardCard;
import main.java.ui.dtos.DroneCatalogCardDto;
import main.java.ui.dtos.DroneDashboardDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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

        // Create a fake DroneDashboardDto
        DroneDashboardDto[] fakeDTO = new DroneDashboardDto[] {
                new DroneDashboardDto("Drone 1", "DJI", "ON", 314, 400, 50.0, 12.34, 56.78, "1234567890", 52.0, "SEN", 60.0, "2024-12-15T17:00:52.588123+01:00", 732.0, 54.0, 250.0, "2024-12-15T17:00:52.588123+01:00"),
                new DroneDashboardDto("Drone 2", "Parrot", "ON", 120, 410, 55.0, 13.34, 57.78, "0987654321", 53.0, "AC", 65.0, "2024-12-16T17:00:52.588123+01:00", 742.0, 55.0, 260.0, "2024-12-16T17:00:52.588123+01:00"),
                new DroneDashboardDto("Drone 3", "Yuneec", "IS", 30, 420, 60.0, 14.34, 58.78, "1122334455", 54.0, "SEN", 70.0, "2024-12-17T17:00:52.588123+01:00", 752.0, 56.0, 270.0, "2024-12-17T17:00:52.588123+01:00"),
                new DroneDashboardDto("Drone 4", "DJI", "OF", 0, 400, 50.0, 12.34, 56.78, "1234567890", 52.0, "", 60.0, "2024-12-15T17:00:52.588123+01:00", 732.0, 54.0, 250.0, "2024-12-15T17:00:52.588123+01:00"),
                new DroneDashboardDto("Drone 5", "Parrot", "ON", 120, 410, 55.0, 13.34, 57.78, "0987654321", 53.0, "AC", 65.0, "2024-12-16T17:00:52.588123+01:00", 742.0, 55.0, 260.0, "2024-12-16T17:00:52.588123+01:00"),
        };

        // Add the fake DroneDashboardCard to the cardPanel
        for (DroneDashboardDto dto : fakeDTO) {
            cardPanel.add(new DroneDashboardCard(dto), BorderLayout.WEST);
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
