package main.java.ui.components;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;

public class ButtonThemeSwitch extends JButton {
    private FontIcon darkThemeIcon = FontIcon.of(FontAwesomeSolid.MOON, 13);
    private FontIcon lightThemeIcon = FontIcon.of(FontAwesomeSolid.SUN, 13);
    private boolean isDarkTheme = FlatLaf.isLafDark();

    public ButtonThemeSwitch() {
        FlatLaf.registerCustomDefaultsSource("main.java.ui.themes");

        setIcon(isDarkTheme ? darkThemeIcon : lightThemeIcon);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
}