package main.java.ui.components;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

public class ButtonThemeSwitch extends JButton {
    private final FontIcon darkThemeIcon = FontIcon.of(FontAwesomeSolid.MOON, 13);
    private final FontIcon lightThemeIcon = FontIcon.of(FontAwesomeSolid.SUN, 13);
    private boolean isDarkTheme = FlatLaf.isLafDark();

    public ButtonThemeSwitch() {
        setIcon(isDarkTheme ? darkThemeIcon : lightThemeIcon);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        addActionListener(e -> toggleTheme());
    }

    private void toggleTheme() {
        isDarkTheme = !isDarkTheme;
        setIcon(isDarkTheme ? darkThemeIcon : lightThemeIcon);

        // Logic to toggle the theme
        if (isDarkTheme) {
            FlatDarkLaf.setup();
        } else {
            FlatLightLaf.setup();
        }
        FlatLaf.updateUI();
    }

    @Override
    public Dimension getPreferredSize() {
        int size = Math.max(super.getPreferredSize().width, super.getPreferredSize().height);
        return new Dimension(size, size); // Ensure the button is a square
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize(); // Use the same size for minimum
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize(); // Use the same size for maximum
    }
}
