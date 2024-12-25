package main.java.ui.components;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
class CardTemplateTest {

    @Test
    void testCardTemplateCreation() {
        // Create a test panel to be used as content
        JPanel testPanel = new JPanel();
        testPanel.add(new JButton("Test Button"));
        testPanel.add(new JLabel("Test Label"));

        // Create a CardTemplate instance
        CardTemplate card = new CardTemplate("Test Title", "Test Subtitle", testPanel);

        // Verify the title and subtitle labels
        JLabel titleLabel = (JLabel) ((JPanel) card.getComponent(0)).getComponent(0);
        JLabel subtitleLabel = (JLabel) ((JPanel) card.getComponent(0)).getComponent(1);

        assertEquals("Test Title", titleLabel.getText());
        assertEquals("Test Subtitle", subtitleLabel.getText());

        // Verify the content panel
        JPanel centerPanel = (JPanel) card.getComponent(1);
        JPanel contentPanel = (JPanel) centerPanel.getComponent(1);
        assertEquals(testPanel, contentPanel.getComponent(0));
    }

    @Test
    void updateThemeColor() {
        // Create a test panel to be used as content
        JPanel testPanel = new JPanel();
        testPanel.add(new JButton("Test Button"));
        testPanel.add(new JLabel("Test Label"));

        // Create a CardTemplate instance
        CardTemplate card = new CardTemplate("Test Title", "Test Subtitle", testPanel);

        // Change the theme color
        UIManager.put("Label.disabledForeground", Color.RED);
        card.updateThemeColor();

        // Verify the theme color update
        assertEquals(Color.RED, card.getSubtitleLabel().getForeground());
        assertEquals(Color.RED, card.getSeparator().getForeground());
    }

    @Test
    void updateAllInstances() {
        // Create multiple CardTemplate instances
        JPanel testPanel1 = new JPanel();
        testPanel1.add(new JButton("Test Button 1"));
        testPanel1.add(new JLabel("Test Label 1"));

        JPanel testPanel2 = new JPanel();
        testPanel2.add(new JButton("Test Button 2"));
        testPanel2.add(new JLabel("Test Label 2"));

        CardTemplate card1 = new CardTemplate("Test Title 1", "Test Subtitle 1", testPanel1);
        CardTemplate card2 = new CardTemplate("Test Title 2", "Test Subtitle 2", testPanel2);

        // Change the theme color
        UIManager.put("Label.disabledForeground", Color.BLUE);
        CardTemplate.updateAllInstances();

        // Verify the theme color update for all instances
        assertEquals(Color.BLUE, card1.getSubtitleLabel().getForeground());
        assertEquals(Color.BLUE, card1.getSeparator().getForeground());
        assertEquals(Color.BLUE, card2.getSubtitleLabel().getForeground());
        assertEquals(Color.BLUE, card2.getSeparator().getForeground());
    }
}