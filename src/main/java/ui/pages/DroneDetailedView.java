package main.java.ui.pages;

import main.java.ui.dtos.DroneDashboardCardDto;

import javax.swing.*;
import java.awt.*;

public class DroneDetailedView extends JPanel {

    public DroneDetailedView(DroneDashboardCardDto dto, JPanel overlayPanel) {
        super(new BorderLayout());

        // Example top bar with a “Back” button
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        topBar.add(backButton);

        // On click, remove the overlay from its parent
        backButton.addActionListener(e -> {
            Container parent = overlayPanel.getParent();
            if (parent != null) {
                parent.remove(overlayPanel);
                parent.revalidate();
                parent.repaint();
            }
        });


        // Add topBar
        add(topBar, BorderLayout.NORTH);

        // Your detailed drone info in the center
        // (Construct or fill out the UI with dto data here)
        JPanel detailContent = new JPanel();
        detailContent.add(new JLabel("Details for: " + dto.getSerialNumber()));
        add(detailContent, BorderLayout.CENTER);

    }
}

