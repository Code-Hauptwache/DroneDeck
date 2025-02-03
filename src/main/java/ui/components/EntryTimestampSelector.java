package main.java.ui.components;

import com.formdev.flatlaf.ui.FlatLineBorder;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EntryTimestampSelector extends JComponent {
    private final JButton skipLeft1;
    private final JButton skipLeft2;
    private final JComboBox<String> timestampComboBox;
    private final JButton skipRight1;
    private final JButton skipRight2;

    public EntryTimestampSelector(int entryCount) {
        // Determine if there is an error condition (entryCount too large)
        boolean error = (entryCount > 99999);

        // Use a simple horizontal box layout
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Left skip buttons
        FontIcon chevronLeft = FontIcon.of(FontAwesomeSolid.CHEVRON_LEFT, 15, UIManager.getColor("Label.foreground"));
        skipLeft1 = new JButton();
        skipLeft1.setIcon(new DoubleIcon(chevronLeft, -2));
        skipLeft1.setContentAreaFilled(false);
        skipLeft1.setBorderPainted(false);
        skipLeft1.setFocusPainted(false);
        skipLeft1.addActionListener(createSkipListener("skipLeft1"));

        skipLeft2 = new JButton(chevronLeft);
        skipLeft2.setContentAreaFilled(false);
        skipLeft2.setBorderPainted(false);
        skipLeft2.setFocusPainted(false);
        skipLeft2.addActionListener(createSkipListener("skipLeft2"));

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
        skipRight1 = new JButton(chevronRight);
        skipRight1.setContentAreaFilled(false);
        skipRight1.setBorderPainted(false);
        skipRight1.setFocusPainted(false);
        skipRight1.addActionListener(createSkipListener("skipRight1"));

        skipRight2 = new JButton();
        skipRight2.setIcon(new DoubleIcon(chevronRight, -2));
        skipRight2.setContentAreaFilled(false);
        skipRight2.setBorderPainted(false);
        skipRight2.setFocusPainted(false);
        skipRight2.addActionListener(createSkipListener("skipRight2"));

        // Add components in horizontal order:
        add(skipLeft1);
        add(Box.createHorizontalStrut(5));
        add(skipLeft2);
        add(Box.createHorizontalGlue());
        add(entryLabel);
        add(timestampComboBox);
        add(ofLabel);
        add(Box.createHorizontalGlue());
        add(skipRight1);
        add(Box.createHorizontalStrut(5));
        add(skipRight2);

        // Set a border (optional, adjust as needed)
        setBorder(new FlatLineBorder(
                new Insets(2, 2, 2, 2),
                UIManager.getColor("Component.borderColor"),
                1,
                10 // corner radius
        ));
    }

    public void addTimestampChangeListener(ActionListener listener) {
        timestampComboBox.addActionListener(listener);
    }

    public int getSelectedEntryIndex() {
        int selectedIndex = timestampComboBox.getSelectedIndex();
        return timestampComboBox.getItemCount() - selectedIndex - 1;
    }

    /**
     * Utility method to create an ActionListener that calls
     * your skip methods. Replace with your actual skip logic.
     */
    private ActionListener createSkipListener(String whichSkip) {
        return e -> {
            System.out.println("Clicked: " + whichSkip);
            // TODO: Replace with your actual skip method calls
        };
    }

    @Override
    public Dimension getPreferredSize() {
        // Provide a preferred size for the entire component (optional)
        return new Dimension(310, 30);
    }
}
