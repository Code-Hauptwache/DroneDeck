package main.java.ui.components;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ButtonThemeSwitchTest class contains unit tests for the ButtonThemeSwitch class.
 * The tests cover the toggleTheme, getPreferredSize, getMinimumSize, and getMaximumSize methods.
 * The tests verify that the theme is toggled correctly, the preferred size is a square,
 * and the minimum and maximum sizes are equal to the preferred size.
 * The tests are run using JUnit 5.
 */
class ButtonThemeSwitchTest {

    @org.junit.jupiter.api.Test
    void toggleTheme() {
        ButtonThemeSwitch buttonThemeSwitch = new ButtonThemeSwitch();
        boolean isDarkTheme = buttonThemeSwitch.isDarkTheme;
        buttonThemeSwitch.toggleTheme();
        assertNotEquals(isDarkTheme, buttonThemeSwitch.isDarkTheme);
    }

    @org.junit.jupiter.api.Test
    void getPreferredSize() {
        ButtonThemeSwitch buttonThemeSwitch = new ButtonThemeSwitch();
        assertEquals(buttonThemeSwitch.getPreferredSize().width, buttonThemeSwitch.getPreferredSize().height);
    }

    @org.junit.jupiter.api.Test
    void getMinimumSize() {
        ButtonThemeSwitch buttonThemeSwitch = new ButtonThemeSwitch();
        assertEquals(buttonThemeSwitch.getMinimumSize(), buttonThemeSwitch.getPreferredSize());
    }

    @org.junit.jupiter.api.Test
    void getMaximumSize() {
        ButtonThemeSwitch buttonThemeSwitch = new ButtonThemeSwitch();
        assertEquals(buttonThemeSwitch.getMaximumSize(), buttonThemeSwitch.getPreferredSize());
    }

}