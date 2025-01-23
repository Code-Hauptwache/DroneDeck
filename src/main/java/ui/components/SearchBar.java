package main.java.ui.components;

import com.formdev.flatlaf.ui.FlatLineBorder;
import main.java.ui.pages.DroneCatalog;
import main.java.ui.pages.DroneDashboard;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A search bar that allows the user to search for drones.
 * The search bar is used in the NorthPanel.
 */
public class SearchBar extends JComponent {

    private static final Logger logger = Logger.getLogger(SearchBar.class.getName());

    private final JTextField textField;
    private String searchText;

    public SearchBar() {
        // Set layout for the component
        setLayout(new BorderLayout());

        // Create the search field
        textField = new JTextField();
        textField.putClientProperty("JTextField.placeholderText", "Search"); // Placeholder for FlatLaf
        // Set the background color transparent
        textField.setBackground(new Color(0, 0, 0, 0));

        // Set the border for the text field
        textField.setBorder(BorderFactory.createCompoundBorder(
                new FlatLineBorder(
                        new Insets(7, 7, 7, 7),
                        UIManager.getColor("Component.borderColor"),
                        1,
                        18 // Rounded corner radius
                ),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)
        ));

        // Add a DocumentListener to update searchText as the user types
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSearchText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSearchText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSearchText();
            }

            private void updateSearchText() {
                searchText = textField.getText();

                logger.log(Level.INFO, "Search Text: " + searchText);

                DroneCatalog.getInstance().updateDroneTypes(searchText);
                DroneDashboard.getInstance().updateDrones(searchText);
            }
        });

        add(textField, BorderLayout.CENTER);
    }

    /**
     * Returns the current text in the search bar.
     *
     * @return the search text
     */
    public String getSearchText() {
        return searchText;
    }

    /**
     * Sets an action listener for the search bar when Enter is pressed.
     *
     * @param listener the action listener
     */
    public void setSearchActionListener(ActionListener listener) {
        textField.addActionListener(listener);
    }
}