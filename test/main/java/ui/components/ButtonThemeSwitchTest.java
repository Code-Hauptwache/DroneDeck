package main.java.ui.components;

import static org.junit.jupiter.api.Assertions.*;

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