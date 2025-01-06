package main.java.ui.components;

import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * A search bar that allows the user to search for drones.
 * The search bar is used in the NorthPanel.
 */
public class SearchBar extends JComponent {
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


        // Bind the entered text to the searchText variable when Enter is pressed
        textField.addActionListener(e -> {
            searchText = textField.getText();
            // Call search logic or event here if needed
            System.out.println("Search Text: " + searchText);
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
