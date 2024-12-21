package main.java.ui.components;

import javax.swing.*;
import java.awt.*;

public class NorthPanel extends JPanel {
    /**
     * The NorthPanel class is a JPanel that contains the NavigationBar, SearchBar, and ThemeSwitcher.
     * The NavigationBar is a horizontal bar that contains buttons for navigating the application.
     * The SearchBar is a horizontal bar that contains a search bar.
     * The ThemeSwitcher is a button that toggles the theme of the application.
     */
    public NorthPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add the Navigation Bar
        NavigationBar navigationBar = new NavigationBar();
        navigationBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(navigationBar);

        // Add the Search Bar and Theme Switcher
        SearchBarAndThemeSwitcherPanel searchBarAndThemeSwitcherPanel = new SearchBarAndThemeSwitcherPanel();
        add(searchBarAndThemeSwitcherPanel);
    }
}