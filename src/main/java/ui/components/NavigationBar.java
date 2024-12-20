package main.java.ui.components;

import javax.swing.*;

public class NavigationBar extends JPanel {
    public NavigationBar() {
        // Set the layout manager
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // TODO: Add Navigation Buttons

        // Add a filler to push the theme switch button to the right
        add(Box.createHorizontalGlue());

        // Add the theme switch button
        ButtonThemeSwitch themeSwitch = new ButtonThemeSwitch();
        add(themeSwitch);
    }
}
