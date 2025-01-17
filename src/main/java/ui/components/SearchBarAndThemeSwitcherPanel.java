package main.java.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * The SearchBarAndThemeSwitcherPanel class is a JPanel
 * that contains the SearchBar and the ThemeSwitcher.
 * The SearchBarAndThemeSwitcherPanel is used in the NorthPanel.
 */
public class SearchBarAndThemeSwitcherPanel extends JPanel {
    /**
     * The SearchBarAndThemeSwitcherPanel method is the constructor for the SearchBarAndThemeSwitcherPanel class.
     * It sets the layout of the SearchBarAndThemeSwitcherPanel to BorderLayout.
     * It then adds the SearchBar and the ThemeSwitcher to the SearchBarAndThemeSwitcherPanel.
     */
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

        // Wrap the ButtonThemeSwitcher in a panel to enforce its size
        JPanel buttonPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for centering
        buttonPanel.add(buttonThemeSwitcher); // Add the square button
        buttonPanel.setPreferredSize(new Dimension(buttonThemeSwitcher.getPreferredSize().width, searchBar.getPreferredSize().height)); // Match height to SearchBar

        // Create a panel for left-side padding with the same width as the theme button
        JPanel leftPaddingPanel = new JPanel();
        leftPaddingPanel.setPreferredSize(new Dimension(buttonThemeSwitcher.getPreferredSize().width, searchBar.getPreferredSize().height));

        // Add components to the main panel
        add(leftPaddingPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);

        // Set padding for the main panel
        setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));

        // Add a ComponentListener to adjust padding dynamically
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int frameWidth = getWidth();
                int padding = Math.max(150, frameWidth / 5);
                centerPanel.setBorder(BorderFactory.createEmptyBorder(0, padding, 0, padding));
                revalidate();
                repaint();
            }
        });
    }
}