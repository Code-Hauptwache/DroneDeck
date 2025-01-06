package main.java.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * The SearchBarAndThemeSwitcherPanel class is a JPanel
 * that contains the SearchBar and the ThemeSwitcher.
 * The SearchBarAndThemeSwitcherPanel is used in the NorthPanel.
 */
public class SearchBarAndThemeSwitcherPanel extends JPanel {
    public SearchBarAndThemeSwitcherPanel() {
        // Use BorderLayout for simpler layout management
        setLayout(new BorderLayout());

        // Create the Theme Switcher
        ButtonThemeSwitcher buttonThemeSwitcher = new ButtonThemeSwitcher();

        // Create the Search Bar
        SearchBar searchBar = new SearchBar();

        // Create a panel to center the search bar
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(searchBar, BorderLayout.CENTER);

        // Add some padding to the center panel
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 150, 0, 150));

        // Add components to the main panel
        add(centerPanel, BorderLayout.CENTER);
        add(buttonThemeSwitcher, BorderLayout.EAST);

        // Set padding for the main panel
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
}
