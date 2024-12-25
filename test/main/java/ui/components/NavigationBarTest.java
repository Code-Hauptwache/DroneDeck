package main.java.ui.components;

import main.java.ui.MainPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The NavigationBarTest class contains unit tests for the NavigationBar class.
 * The tests cover the button selection and page switching functionality.
 * The tests verify that the correct button is selected and the correct page is displayed.
 * The tests are run using JUnit 5.
 */
class NavigationBarTest {

    private MainPanel mainPanel;
    private NavigationBar navigationBar;

    @BeforeEach
    void setUp() {
        mainPanel = new MainPanel();
        navigationBar = new NavigationBar(mainPanel);
    }

    @Test
    void testButtonSelection() {
        JButton catalogButton = (JButton) navigationBar.getComponent(0);
        JButton dashboardButton = (JButton) navigationBar.getComponent(1);

        // By default, the dashboard button should be selected
        assertFalse(catalogButton.isBorderPainted());
        assertTrue(dashboardButton.isBorderPainted());

        // Click the catalog button and check the selection
        catalogButton.doClick();
        assertTrue(catalogButton.isBorderPainted());
        assertFalse(dashboardButton.isBorderPainted());
    }

    @Test
    void testPageSwitching() {
        JButton catalogButton = (JButton) navigationBar.getComponent(0);
        JButton dashboardButton = (JButton) navigationBar.getComponent(1);

        dashboardButton.doClick();
        assertEquals(MainPanel.Page.DASHBOARD, mainPanel.getCurrentPage());

        catalogButton.doClick();
        assertEquals(MainPanel.Page.CATALOG, mainPanel.getCurrentPage());
    }
}