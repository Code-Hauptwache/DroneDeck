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
        // Use a simple horizontal box layout
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Left skip buttons
        FontIcon chevronLeft = FontIcon.of(FontAwesomeSolid.CHEVRON_LEFT, 15, UIManager.getColor("Label.foreground"));
        skipLeft1 = new JButton();
        skipLeft1.setIcon(new DoubleIcon(chevronLeft, -2));
        skipLeft2 = new JButton(chevronLeft);
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
        FontIcon chevronRight =  FontIcon.of(FontAwesomeSolid.CHEVRON_RIGHT, 15, UIManager.getColor("Label.foreground"));
        skipRight1 = new JButton(chevronRight);
        skipRight2 = new JButton();
        skipRight2.setIcon(new DoubleIcon(chevronRight, -2));
        skipRight1.addActionListener(createSkipListener("skipRight1"));
        skipRight2.addActionListener(createSkipListener("skipRight2"));

        setBorder(new FlatLineBorder(
                new Insets(15, 15, 15, 15),
                UIManager.getColor("Component.borderColor"),
                1,
                40 // corner radius
        ));

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
