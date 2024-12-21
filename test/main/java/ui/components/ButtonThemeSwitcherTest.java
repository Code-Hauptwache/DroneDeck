package main.java.ui.components;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The ButtonThemeSwitchTest class contains unit tests for the ButtonThemeSwitch class.
 * The tests cover the toggleTheme, getPreferredSize, getMinimumSize, and getMaximumSize methods.
 * The tests verify that the theme is toggled correctly, the preferred size is a square,
 * and the minimum and maximum sizes are equal to the preferred size.
 * The tests are run using JUnit 5.
 */
class ButtonThemeSwitcherTest {

    @org.junit.jupiter.api.Test
    void toggleTheme() {
        ButtonThemeSwitcher buttonThemeSwitcher = new ButtonThemeSwitcher();
        boolean isDarkTheme = buttonThemeSwitcher.isDarkTheme();
        buttonThemeSwitcher.toggleTheme();
        assertNotEquals(isDarkTheme, buttonThemeSwitcher.isDarkTheme());
    }

    @org.junit.jupiter.api.Test
    void getPreferredSize() {
        ButtonThemeSwitcher buttonThemeSwitcher = new ButtonThemeSwitcher();
        assertEquals(buttonThemeSwitcher.getPreferredSize().width, buttonThemeSwitcher.getPreferredSize().height);
    }

    @org.junit.jupiter.api.Test
    void getMinimumSize() {
        ButtonThemeSwitcher buttonThemeSwitcher = new ButtonThemeSwitcher();
        assertEquals(buttonThemeSwitcher.getMinimumSize(), buttonThemeSwitcher.getPreferredSize());
    }

    @org.junit.jupiter.api.Test
    void getMaximumSize() {
        ButtonThemeSwitcher buttonThemeSwitcher = new ButtonThemeSwitcher();
        assertEquals(buttonThemeSwitcher.getMaximumSize(), buttonThemeSwitcher.getPreferredSize());
    }

}