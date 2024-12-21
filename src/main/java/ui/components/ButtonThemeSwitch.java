package main.java.ui.components;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;
import java.awt.*;

public class ButtonThemeSwitch extends JComponent {
    private final FontIcon darkThemeIcon = FontIcon.of(FontAwesomeSolid.MOON, 13, UIManager.getColor("Label.foreground"));
    private final FontIcon lightThemeIcon = FontIcon.of(FontAwesomeSolid.SUN, 13, UIManager.getColor("Label.foreground"));
    public boolean isDarkTheme = FlatLaf.isLafDark();
    private final JButton button;

    public ButtonThemeSwitch() {
        button = new JButton(isDarkTheme ? darkThemeIcon : lightThemeIcon);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.addActionListener(e -> toggleTheme());
        setLayout(new BorderLayout());
        add(button, BorderLayout.CENTER);
    }

    public void toggleTheme() {
        isDarkTheme = !isDarkTheme;
        button.setIcon(isDarkTheme ? darkThemeIcon : lightThemeIcon);

        // Logic to toggle the theme
        FlatAnimatedLafChange.showSnapshot();
        if (isDarkTheme) {
            FlatDarkLaf.setup();
        } else {
            FlatLightLaf.setup();
        }
        FlatLaf.updateUI();
        button.setForeground(UIManager.getColor("Label.foreground"));
        darkThemeIcon.setIconColor(UIManager.getColor("Label.foreground"));
        lightThemeIcon.setIconColor(UIManager.getColor("Label.foreground"));
        FlatAnimatedLafChange.hideSnapshotWithAnimation();
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