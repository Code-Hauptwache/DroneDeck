package main.java.ui.components;

import com.formdev.flatlaf.ui.FlatLineBorder;

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
        // Use a simple horizontal box layout
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Left skip buttons
        skipLeft1 = new JButton("<<");
        skipLeft2 = new JButton("<");
        skipLeft1.addActionListener(createSkipListener("skipLeft1"));
        skipLeft2.addActionListener(createSkipListener("skipLeft2"));

        // Create the combo box with placeholder time strings
        timestampComboBox = new JComboBox<>();
        for (int i = 0; i < entryCount; i++) {
            // For example, create times as "00:00:xx"
            String item = String.format("00:00:%02d", i);
            timestampComboBox.addItem(item);
        }

        // Right skip buttons
        skipRight1 = new JButton(">");
        skipRight2 = new JButton(">>");
        skipRight1.addActionListener(createSkipListener("skipRight1"));
        skipRight2.addActionListener(createSkipListener("skipRight2"));

        // Add components in horizontal order
        add(skipLeft1);
        add(skipLeft2);
        add(Box.createHorizontalStrut(5)); // small spacing
        add(timestampComboBox);
        add(Box.createHorizontalStrut(5)); // small spacing
        add(skipRight1);
        add(skipRight2);

        // Set a simple FlatLineBorder
        setBorder(new FlatLineBorder(new Insets(2, 2, 2, 2), (int) 1f));
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
        // Provide a preferred size (optional)
        return new Dimension(300, 30);
    }
}
