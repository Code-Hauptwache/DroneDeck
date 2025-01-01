package main.java.ui.pages;

import main.java.ui.components.DroneStatus;
import main.java.ui.components.DroneVisualBatteryStatus;
import main.java.ui.components.InfoTooltip;
import main.java.ui.dtos.DroneDashboardDto;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

public class DroneDetailedView extends JPanel {

    public DroneDetailedView(DroneDashboardDto dto, JPanel overlayPanel) {
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
        // Force left alignment, if needed:
        title.setHorizontalAlignment(SwingConstants.LEFT);
        subtitle.setHorizontalAlignment(SwingConstants.LEFT);
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

        // Create the center panel with GridBagLayout
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagLayout layout = (GridBagLayout) centerPanel.getLayout();

        // We have 4 columns with relative widths
        layout.columnWeights = new double[] {2.0, 1.0, 1.0, 1.0};

        // Common GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        // Make sure to anchor left and don’t stretch horizontally
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Build a panel that shows DroneStatus and DroneVisualBatteryStatus horizontally
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusPanel.add(new DroneStatus(dto));
        statusPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        statusPanel.add(new DroneVisualBatteryStatus(dto));

        // Create an array of components for column 1
        Component[] Column1 = {
                statusPanel,
                createDisabledLabel("Average Speed"),
                createDisabledLabel("Location"),
                createDisabledLabel("Traveled"),
                createDisabledLabel("Carriage Weight"),
                createDisabledLabel("Carriage Type"),
                createDisabledLabel("Last Seen"),
                createDisabledLabel("Serial Number")
        };

        // Add each element of Column1 to the first column (column index = 0), each in its own row
        for (int row = 0; row < Column1.length; row++) {
            gbc.gridx = 0;   // first column
            gbc.gridy = row; // row is the loop index
            centerPanel.add(Column1[row], gbc);
        }

        // Create an array of components for column 2
        Component[] Column2 = {
                new InfoTooltip(dto.getDataTimestamp()),
                new JLabel((int) dto.getSpeed() + " km/h"),
                new JLabel(dto.getLocation() != null ? dto.getLocation() + " km" : "N/A"),
                new JLabel(
                        (dto.getTravelDistance() != null && !dto.getTravelDistance().toString().isEmpty())
                                ? dto.getTravelDistance().toString()
                                : "N/A"
                ),
                new JLabel(dto.getCarriageWeight() > 0 ? dto.getCarriageWeight() + " kg" : "N/A"),
                new JLabel(dto.getCarriageType() != null ? dto.getCarriageType() : "N/A"),
                new JLabel(dto.getLastSeen() != null ? dto.getLastSeen() : "N/A"),
                new JLabel(dto.getSerialNumber())
        };

        // Add each element of Column2 to the second column (column index = 1), each in its own row
        for (int row = 0; row < Column2.length; row++) {
            gbc.gridx = 1;   // second column
            gbc.gridy = row; // row index

            if (row == 0) {
                // For the tooltip only, align to the right
                gbc.anchor = GridBagConstraints.EAST;
            } else {
                // For all other rows in this column, align left
                gbc.anchor = GridBagConstraints.WEST;
            }

            centerPanel.add(Column2[row], gbc);
        }

        // Create an array of components for column 3
        Component[] Column3 = {
                new JLabel(""),
                createDisabledLabel("Weight"),
                createDisabledLabel("Top Speed"),
                createDisabledLabel("Battery Capacity"),
                createDisabledLabel("Control Range"),
                createDisabledLabel("Max Carriage")
        };

        // Add each element of Column3 to the third column (column index = 2), each in its own row
        for (int row = 0; row < Column3.length; row++) {
            gbc.gridx = 2;   // third column
            gbc.gridy = row; // row is the loop index
            centerPanel.add(Column3[row], gbc);
        }

        // Create an array of components for column 4
        Component[] Column4 = {
                new JLabel(""),
                new JLabel(dto.getWeight() + " kg"),
                new JLabel(dto.getMaxSpeed() + " km/h"),
                new JLabel(dto.getBatteryPercentage() + "%"),
                new JLabel(dto.getControlRange() + " km"),
                new JLabel(dto.getMaxCarriageWeight() + " kg")
        };

        // Add each element of Column4 to the fourth column (column index = 3), each in its own row
        for (int row = 0; row < Column4.length; row++) {
            gbc.gridx = 3;   // fourth column
            gbc.gridy = row; // row is the loop index
            centerPanel.add(Column4[row], gbc);
        }

        // Wrap centerPanel in another panel so it hugs the top
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(centerPanel, BorderLayout.NORTH);

        // Finally, add the wrapper to the CENTER of our main panel
        add(wrapper, BorderLayout.CENTER);
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

    /**
     * Helper method to create a JLabel with disabled foreground color and left alignment.
     */
    private JLabel createDisabledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(UIManager.getColor("Label.disabledForeground"));
        // Ensure text is left-aligned
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }
}