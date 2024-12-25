package main.java.ui.components;

import main.java.ui.MainPanel;

import javax.swing.*;

/**
 * The NorthPanel class is a JPanel
 * that contains the NavigationBar, SearchBar, and ThemeSwitcher.
 * The NorthPanel is used in the MainPanel.
 */
public class NorthPanel extends JPanel {
    /**
     * The NorthPanel method is the constructor for the NorthPanel class.
     * It sets the layout of the NorthPanel to BoxLayout with the Y_AXIS.
     * It then adds the NavigationBar and SearchBarAndThemeSwitcherPanel to the NorthPanel.
     * @param mainPanel The mainPanel reference
     */
    public NorthPanel(MainPanel mainPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add the Navigation Bar, passing in the mainPanel reference
        NavigationBar navigationBar = new NavigationBar(mainPanel);
        navigationBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(navigationBar);

        // Add the Search Bar and Theme Switcher (unchanged)
        SearchBarAndThemeSwitcherPanel searchBarAndThemeSwitcherPanel = new SearchBarAndThemeSwitcherPanel();
        add(searchBarAndThemeSwitcherPanel);
    }
}