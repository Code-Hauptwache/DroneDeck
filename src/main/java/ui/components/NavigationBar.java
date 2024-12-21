package main.java.ui.components;

import javax.swing.*;

public class NavigationBar extends JComponent {

    public NavigationBar() {

        // Set the layout manager
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // Center the navigation bar
        add(Box.createHorizontalGlue());

        // TODO: Add Navigation Buttons to switch between pages
        JLabel title = new JLabel("DroneDeck");
        add(title);

        // Add a filler to push the theme switch button to the right
        add(Box.createHorizontalGlue());

        // Add the theme switch button
        ButtonThemeSwitch themeSwitch = new ButtonThemeSwitch();
        add(themeSwitch);
    }
}