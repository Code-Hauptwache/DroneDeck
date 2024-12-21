package main.java.ui.components;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

/**
 * A button that allows the user to switch between dark and light themes.
 * It displays an icon representing the current theme and toggles the theme
 * when clicked.
 */
public class ButtonThemeSwitch extends JButton {
    private final FontIcon darkThemeIcon = FontIcon.of(FontAwesomeSolid.MOON, 13);
    private final FontIcon lightThemeIcon = FontIcon.of(FontAwesomeSolid.SUN, 13);
    private boolean isDarkTheme = FlatLaf.isLafDark();

    /**
     * Creates a new theme switch button.
     * The button displays an icon representing the current theme and toggles
     * the theme when clicked.
     */
    public ButtonThemeSwitch() {
        setIcon(isDarkTheme ? darkThemeIcon : lightThemeIcon);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        addActionListener(e -> toggleTheme());
    }

    /**
     * Toggles the theme between dark and light.
     * The button icon is updated to reflect the new theme.
     * The theme is changed using FlatLaf.
     * The UI is updated to reflect the new theme.
     * The theme change is animated using FlatAnimatedLafChange.
     */
    public void toggleTheme() {
        isDarkTheme = !isDarkTheme;
        setIcon(isDarkTheme ? darkThemeIcon : lightThemeIcon);

        // Logic to toggle the theme
        FlatAnimatedLafChange.showSnapshot();
        if (isDarkTheme) {
            FlatDarkLaf.setup();
        } else {
            FlatLightLaf.setup();
        }
        FlatLaf.updateUI();
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
    }

    /**
     * Returns whether the current theme is dark.
     *
     * @return true if the current theme is dark, false otherwise
     */
    public boolean isDarkTheme() {
        return isDarkTheme;
    }

    /**
     * Returns the preferred size of the button, ensuring it is a square.
     * This is useful when the button is added to a layout that respects
     * preferred size, such as a BoxLayout.
     * The preferred size is set to be the maximum of the width and height
     * of the super class's preferred size.
     * This ensures that the button is always a square.
     * The super class's preferred size is calculated based on the button's
     * text and icon.
     */
    @Override
    public Dimension getPreferredSize() {
        int size = Math.max(super.getPreferredSize().width, super.getPreferredSize().height);
        return new Dimension(size, size); // Ensure the button is a square
    }

    /**
     * Returns the minimum size of the button, ensuring it is a square.
     * This is useful when the button is added to a layout that respects
     * minimum size, such as a BoxLayout.
     * The minimum size is set to be the same as the preferred size.
     * This ensures that the button is always a square.
     */
    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize(); // Use the same size for minimum
    }

    /**
     * Returns the maximum size of the button, ensuring it is a square.
     * This is useful when the button is added to a layout that respects
     * maximum size, such as a BoxLayout.
     * The maximum size is set to be the same as the preferred size.
     * This ensures that the button is always a square.
     */
    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize(); // Use the same size for maximum
    }
}
