package main.java.ui.components;

import com.formdev.flatlaf.ui.FlatLineBorder;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * A custom component for selecting an entry timestamp.
 * This component consists of a pair of skip buttons, a combo box, and another pair of skip buttons.
 * The combo box displays a list of numbers from the total number of entries down to 1.
 * The skip buttons allow the user to quickly navigate to the first or last entry.
 * The selected entry index can be retrieved using the getSelectedEntryIndex() method.
 */
public class EntryTimestampSelector extends JComponent {
    private final JButton skipToOldestDroneData;
    private final JButton skipToOneOlderDroneData;
    private final JComboBox<String> timestampComboBox;
    private final JButton skipToOneLaterDroneData;
    private final JButton skipToLatestDroneData;

    /**
     * Creates a new EntryTimestampSelector with the given number of entries.
     * If the number of entries is too large (greater than 99999), an error message will be displayed.
     *
     * @param entryCount the number of entries to display
     */
    public EntryTimestampSelector(int entryCount) {
        // Determine if there is an error condition (entryCount too large)
        boolean error = (entryCount > 99999);

        // Use a simple horizontal box layout
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Left skip buttons
        FontIcon chevronLeft = FontIcon.of(FontAwesomeSolid.CHEVRON_LEFT, 15, UIManager.getColor("Label.foreground"));
        skipToOldestDroneData = new JButton();
        skipToOldestDroneData.setIcon(new DoubleIcon(chevronLeft, -2));
        skipToOldestDroneData.setContentAreaFilled(false);
        skipToOldestDroneData.setBorderPainted(false);
        skipToOldestDroneData.setFocusPainted(false);
        skipToOldestDroneData.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        skipToOldestDroneData.addActionListener(createSkipListener("skipLeft1"));

        skipToOneOlderDroneData = new JButton(chevronLeft);
        skipToOneOlderDroneData.setContentAreaFilled(false);
        skipToOneOlderDroneData.setBorderPainted(false);
        skipToOneOlderDroneData.setFocusPainted(false);
        skipToOneOlderDroneData.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        skipToOneOlderDroneData.addActionListener(createSkipListener("skipLeft2"));

        // Create a label for "Entry"
        JLabel entryLabel = new JLabel("Entry");
        entryLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        entryLabel.setOpaque(false);
        // Hide the "Entry" label if the entry count is too large.
        if (error) {
            entryLabel.setVisible(false);
        }

        // Create the combo box
        timestampComboBox = new JComboBox<>();
        if (error) {
            // Disable combo box and show an error message if too many entries
            timestampComboBox.setEnabled(false);
            timestampComboBox.addItem("Too many entries");
        } else {
            // Populate with numbers from entryCount down to 1
            for (int i = entryCount; i > 0; i--) {
                timestampComboBox.addItem(String.valueOf(i));
            }
            // Set prototype display value for sizing
            timestampComboBox.setPrototypeDisplayValue(String.valueOf(entryCount));
        }

        // Remove the background of the JComboBox (make it transparent)
        timestampComboBox.setOpaque(false);
        timestampComboBox.setBackground(new Color(0, 0, 0, 0));
        timestampComboBox.setBorder(BorderFactory.createEmptyBorder());
        timestampComboBox.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Calculate the width based on either the error message or the entryCount value
        String textForSizing = error ? "Too many entries" : String.valueOf(entryCount);
        FontMetrics fm = timestampComboBox.getFontMetrics(timestampComboBox.getFont());
        int textWidth = fm.stringWidth(textForSizing + "    ");
        int extraPadding = 30; // extra space for the arrow and inner spacing
        Dimension comboSize = timestampComboBox.getPreferredSize();
        comboSize.width = textWidth + extraPadding;
        timestampComboBox.setPreferredSize(comboSize);
        timestampComboBox.setMinimumSize(comboSize);
        timestampComboBox.setMaximumSize(comboSize);

        // Center the text in the combo box
        timestampComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                                                          Object value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setOpaque(isSelected);
                return label;
            }
        });

        // Create a label for "of <entryCount>" (leave blank in error mode)
        JLabel ofLabel = new JLabel(error ? "" : "of " + entryCount);
        ofLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        ofLabel.setOpaque(false);

        // Right skip buttons
        FontIcon chevronRight = FontIcon.of(FontAwesomeSolid.CHEVRON_RIGHT, 15, UIManager.getColor("Label.foreground"));
        skipToOneLaterDroneData = new JButton(chevronRight);
        skipToOneLaterDroneData.setContentAreaFilled(false);
        skipToOneLaterDroneData.setBorderPainted(false);
        skipToOneLaterDroneData.setFocusPainted(false);
        skipToOneLaterDroneData.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        skipToOneLaterDroneData.addActionListener(createSkipListener("skipRight1"));

        skipToLatestDroneData = new JButton();
        skipToLatestDroneData.setIcon(new DoubleIcon(chevronRight, -2));
        skipToLatestDroneData.setContentAreaFilled(false);
        skipToLatestDroneData.setBorderPainted(false);
        skipToLatestDroneData.setFocusPainted(false);
        skipToLatestDroneData.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        skipToLatestDroneData.addActionListener(createSkipListener("skipRight2"));

        // Add components in horizontal order:
        add(skipToOldestDroneData);
        add(Box.createHorizontalStrut(5));
        add(skipToOneOlderDroneData);
        add(Box.createHorizontalGlue());
        add(entryLabel);
        add(timestampComboBox);
        add(ofLabel);
        add(Box.createHorizontalGlue());
        add(skipToOneLaterDroneData);
        add(Box.createHorizontalStrut(5));
        add(skipToLatestDroneData);

        // Set a border (optional, adjust as needed)
        setBorder(new FlatLineBorder(
                new Insets(2, 2, 2, 2),
                UIManager.getColor("Component.borderColor"),
                1,
                10 // corner radius
        ));

        // Add listener to update button states
        timestampComboBox.addActionListener(_ -> updateButtonStates());
        updateButtonStates();
    }

    /**
     * Adds an ActionListener to the combo box.
     *
     * @param listener the ActionListener to add
     */
    public void addTimestampChangeListener(ActionListener listener) {
        timestampComboBox.addActionListener(listener);
    }

    /**
     * Returns the index of the selected entry.
     * The index is 0-based, with 0 being the last entry.
     *
     * @return the index of the selected entry
     */
    public int getSelectedEntryIndex() {
        int selectedIndex = timestampComboBox.getSelectedIndex();
        return timestampComboBox.getItemCount() - selectedIndex - 1;
    }

    private void updateButtonStates() {
        int selectedIndex = getSelectedEntryIndex();
        boolean atStart = selectedIndex == 0;
        boolean atEnd = selectedIndex == timestampComboBox.getItemCount() - 1;

        skipToOldestDroneData.setEnabled(!atStart);
        skipToOneOlderDroneData.setEnabled(!atStart);
        skipToOneLaterDroneData.setEnabled(!atEnd);
        skipToLatestDroneData.setEnabled(!atEnd);
    }

    private ActionListener createSkipListener(String whichSkip) {
        return _ -> {
            int currentIndex = timestampComboBox.getSelectedIndex();
            int itemCount = timestampComboBox.getItemCount();

            switch (whichSkip) {
                case "skipLeft1":
                    if (currentIndex < itemCount - 1) {
                        // skip to the last index
                        timestampComboBox.setSelectedIndex(itemCount - 1);
                    }
                    break;
                case "skipLeft2":
                    if (currentIndex < itemCount - 1) {
                        timestampComboBox.setSelectedIndex(currentIndex + 1);
                    }
                    break;
                case "skipRight1":
                    if (currentIndex > 0) {
                        timestampComboBox.setSelectedIndex(currentIndex - 1);
                    }
                    break;
                case "skipRight2":
                    if (currentIndex > 1) {
                        // skip to the first index
                        timestampComboBox.setSelectedIndex(0);
                    }
                    break;
            }
        };
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(310, 30);
    }

    public JButton getSkipToOldestDroneData() {
        return skipToOldestDroneData;
    }

    public JButton getSkipToOneOlderDroneData() {
        return skipToOneOlderDroneData;
    }

    public JButton getSkipToOneLaterDroneData() {
        return skipToOneLaterDroneData;
    }

    public JButton getSkipToLatestDroneData() {
        return skipToLatestDroneData;
    }

    public JComboBox<String> getTimestampComboBox() {
        return timestampComboBox;
    }
}