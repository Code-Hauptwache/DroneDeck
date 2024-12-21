package main.java.ui.components;

import javax.swing.*;

public class SearchBarAndThemeSwitcherPanel extends JPanel {
    /**
     * The SearchBarAndThemeSwitcherPanel class is a JPanel
     * that contains a SearchBar and a ButtonThemeSwitcher.
     * The SearchBarAndThemeSwitcherPanel is used in the
     * NorthPanel which is used in the MainPanel.
     */
    public SearchBarAndThemeSwitcherPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Center the search bar
        add(Box.createHorizontalGlue());

        // Add Search Bar
        SearchBar searchBar = new SearchBar();
        add(searchBar);

        add(Box.createHorizontalGlue());

        // Add the Theme Switcher
        ButtonThemeSwitcher buttonThemeSwitcher = new ButtonThemeSwitcher();
        add(buttonThemeSwitcher);
    }
}
