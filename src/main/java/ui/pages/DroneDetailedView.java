package main.java.ui.pages;

import main.java.ui.dtos.DroneDashboardCardDto;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

/**
 * The DroneDetailedView class is a custom
 * JPanel that displays detailed information about a drone.
 */
public class DroneDetailedView extends JPanel {
    /**
     * Creates a new DroneDetailedView with the given DTO and overlay panel.
     *
     * @param dto          The DTO containing the information to display.
     * @param overlayPanel The overlay panel to remove when the back button is clicked.
     */
    public DroneDetailedView(DroneDashboardCardDto dto, JPanel overlayPanel) {
        super(new BorderLayout());

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel titlePane = new JPanel();
        titlePane.setLayout(new BoxLayout(titlePane, BoxLayout.Y_AXIS));

        // Add Title and Subtitle to titlePane
        JLabel title = new JLabel(dto.getTypename());
        title.putClientProperty("FlatLaf.styleClass", "h2");
        JLabel subtitle = new JLabel(dto.getManufacture());
        subtitle.putClientProperty("FlatLaf.styleClass", "medium");
        subtitle.setForeground(UIManager.getColor("Label.disabledForeground"));
        titlePane.add(title);
        titlePane.add(subtitle);

        // Create back button
        FontIcon backIcon = FontIcon.of(FontAwesomeSolid.CHEVRON_LEFT, 20, UIManager.getColor("Label.foreground"));
        JLabel backButton = new JLabel(backIcon);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addBackButtonListener(backButton, overlayPanel);


        // Add back button to topBar
        topBar.add(backButton);
        topBar.add(Box.createHorizontalStrut(20));
        topBar.add(titlePane);

        // Add topBar
        add(topBar, BorderLayout.NORTH);

        // TODO: Implement the detailed view

        // This is a placeholder in orange for the detailed view
        JPanel placeholderPanel = new JPanel(new BorderLayout());
        JPanel placeholderContent = new JPanel(new GridBagLayout());
        JLabel placeholderLabel = new JLabel("Detailed Drone View (TODO)");
        placeholderLabel.setForeground(Color.ORANGE);
        placeholderContent.add(placeholderLabel, new GridBagConstraints());
        JPanel detailContent = new JPanel(new GridBagLayout());
        JLabel detailsLabel = new JLabel("Drone: " + dto.getSerialNumber());
        detailsLabel.setForeground(Color.ORANGE);
        detailContent.add(detailsLabel, new GridBagConstraints());
        placeholderPanel.add(placeholderContent, BorderLayout.CENTER);
        placeholderPanel.add(detailContent, BorderLayout.SOUTH);
        add(placeholderPanel, BorderLayout.CENTER);
    }

    private static void addBackButtonListener(JLabel backButton, JPanel overlayPanel) {
        // On click, remove the overlay from its parent
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Container parent = overlayPanel.getParent();
                if (parent != null) {
                    parent.remove(overlayPanel);
                    parent.revalidate();
                    parent.repaint();
                }
            }
        });
    }
}