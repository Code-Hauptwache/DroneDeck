package main.java.ui.components;

import javax.swing.*;

public class SearchBarAndThemeSwitcherPanel extends JPanel {
    /**
     * The SearchBarAndThemeSwitcherPanel class is a JPanel
     * that contains a SearchBar and a ButtonThemeSwitcher.
     * The SearchBarAndThemeSwitcherPanel is used in the
     * NorthPanel which is used in the MainPanel.
     */
    public SearchBarAndThemeSwitcherPanel(NavigationBar navigationBar) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Create the Theme Switcher
        ButtonThemeSwitcher buttonThemeSwitcher = new ButtonThemeSwitcher(navigationBar);

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
