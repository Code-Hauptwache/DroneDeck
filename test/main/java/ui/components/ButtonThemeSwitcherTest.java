package main.java.ui.components;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The ButtonThemeSwitchTest class contains unit tests for the ButtonThemeSwitch class.
 * The tests cover the toggleTheme, getPreferredSize, getMinimumSize, and getMaximumSize methods.
 * The tests verify that the theme is toggled correctly, the preferred size is a square,
 * and the minimum and maximum sizes are equal to the preferred size.
 * The tests are run using JUnit 5.
 */
class ButtonThemeSwitcherTest {

    private TestNavigationBar testNavigationBar;
    private ButtonThemeSwitcher buttonThemeSwitcher;

    @BeforeEach
    void setUp() {
        testNavigationBar = new TestNavigationBar();
        buttonThemeSwitcher = new ButtonThemeSwitcher();
    }

    @Test
    void toggleTheme() {
        boolean isDarkTheme = buttonThemeSwitcher.isDarkTheme();
        buttonThemeSwitcher.toggleTheme();
        assertNotEquals(isDarkTheme, buttonThemeSwitcher.isDarkTheme());
        assertTrue(testNavigationBar.isUpdateButtonBorderColorCalled());
    }

    @Test
    void getPreferredSize() {
        assertEquals(buttonThemeSwitcher.getPreferredSize().width, buttonThemeSwitcher.getPreferredSize().height);
    }

    @Test
    void getMinimumSize() {
        assertEquals(buttonThemeSwitcher.getMinimumSize(), buttonThemeSwitcher.getPreferredSize());
    }

    @Test
    void getMaximumSize() {
        assertEquals(buttonThemeSwitcher.getMaximumSize(), buttonThemeSwitcher.getPreferredSize());
    }

    private static class TestNavigationBar extends NavigationBar {
        private boolean updateButtonBorderColorCalled = false;

        public TestNavigationBar() {
            super(null);
        }

        @Override
        public void updateButtonBorderColor() {
            updateButtonBorderColorCalled = true;
        }

        public boolean isUpdateButtonBorderColorCalled() {
            return updateButtonBorderColorCalled;
        }
    }
}