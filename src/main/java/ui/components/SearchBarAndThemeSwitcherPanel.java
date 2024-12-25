package main.java.ui.components;

import javax.swing.*;

/**
 * The SearchBarAndThemeSwitcherPanel class is a JPanel
 * that contains the SearchBar and the ThemeSwitcher.
 * The SearchBarAndThemeSwitcherPanel is used in the NorthPanel.
 */
public class SearchBarAndThemeSwitcherPanel extends JPanel {
    /**
     * The SearchBarAndThemeSwitcherPanel method is the constructor for the SearchBarAndThemeSwitcherPanel class.
     * It sets the layout of the SearchBarAndThemeSwitcherPanel to BoxLayout with the X_AXIS.
     * It then adds the ThemeSwitcher, padding, SearchBar, padding, and the ThemeSwitcher to the SearchBarAndThemeSwitcherPanel.
     */
    public SearchBarAndThemeSwitcherPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Create the Theme Switcher
        ButtonThemeSwitcher buttonThemeSwitcher = new ButtonThemeSwitcher();

        // Add padding on the left equivalent to the width of the ButtonThemeSwitcher Button
        int paddingWidth = buttonThemeSwitcher.getPreferredSize().width;
        add(Box.createHorizontalStrut(paddingWidth));

        // Center the search bar
        add(Box.createHorizontalGlue());

        // Add Search Bar
        SearchBar searchBar = new SearchBar();
        add(searchBar);

        // Center the search bar and push the theme switcher to the right
        add(Box.createHorizontalGlue());

        // Add the Theme Switcher
        add(buttonThemeSwitcher);
    }
}
