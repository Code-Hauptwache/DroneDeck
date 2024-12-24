package main.java.ui.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The CardComponentTest class contains unit tests for the CardComponent class.
 * The tests cover the parameters, design, layout, horizontal line separator,
 * reusability, and consistent styling of the CardComponent.
 * The tests are run using JUnit 5.
 */
class CardComponentTest {

    private CardComponent cardComponent;

    @BeforeEach
    void setUp() {
        cardComponent = new CardComponent("TypeName", "Brand", "Content");
    }

    @Test
    void testParameters() {
        assertEquals("TypeName", cardComponent.getTypeName());
        assertEquals("Brand", cardComponent.getBrand());
        assertEquals("Content", cardComponent.getContent());

        cardComponent.setTypeName("NewTypeName");
        cardComponent.setBrand("NewBrand");
        cardComponent.setContent("NewContent");

        assertEquals("NewTypeName", cardComponent.getTypeName());
        assertEquals("NewBrand", cardComponent.getBrand());
        assertEquals("NewContent", cardComponent.getContent());
    }

    @Test
    void testDesignAndLayout() {
        assertEquals(BorderLayout.class, cardComponent.getLayout().getClass());

        Component[] components = cardComponent.getComponents();
        assertEquals(4, components.length);

        assertTrue(components[0] instanceof JLabel);
        assertTrue(components[1] instanceof JLabel);
        assertTrue(components[2] instanceof JSeparator);
        assertTrue(components[3] instanceof JTextArea);

        JLabel typeNameLabel = (JLabel) components[0];
        assertEquals("TypeName", typeNameLabel.getText());
        assertEquals(Font.BOLD, typeNameLabel.getFont().getStyle());
        assertEquals(16, typeNameLabel.getFont().getSize());

        JLabel brandLabel = (JLabel) components[1];
        assertEquals("Brand", brandLabel.getText());
        assertEquals(Font.PLAIN, brandLabel.getFont().getStyle());
        assertEquals(14, brandLabel.getFont().getSize());

        JTextArea contentArea = (JTextArea) components[3];
        assertEquals("Content", contentArea.getText());
        assertFalse(contentArea.isEditable());
    }

    @Test
    void testHorizontalLineSeparator() {
        Component[] components = cardComponent.getComponents();
        assertTrue(components[2] instanceof JSeparator);
    }

    @Test
    void testReusabilityAndConsistentStyling() {
        CardComponent anotherCardComponent = new CardComponent("AnotherTypeName", "AnotherBrand", "AnotherContent");

        assertEquals("AnotherTypeName", anotherCardComponent.getTypeName());
        assertEquals("AnotherBrand", anotherCardComponent.getBrand());
        assertEquals("AnotherContent", anotherCardComponent.getContent());

        assertEquals(BorderLayout.class, anotherCardComponent.getLayout().getClass());

        Component[] components = anotherCardComponent.getComponents();
        assertEquals(4, components.length);

        assertTrue(components[0] instanceof JLabel);
        assertTrue(components[1] instanceof JLabel);
        assertTrue(components[2] instanceof JSeparator);
        assertTrue(components[3] instanceof JTextArea);

        JLabel typeNameLabel = (JLabel) components[0];
        assertEquals("AnotherTypeName", typeNameLabel.getText());
        assertEquals(Font.BOLD, typeNameLabel.getFont().getStyle());
        assertEquals(16, typeNameLabel.getFont().getSize());

        JLabel brandLabel = (JLabel) components[1];
        assertEquals("AnotherBrand", brandLabel.getText());
        assertEquals(Font.PLAIN, brandLabel.getFont().getStyle());
        assertEquals(14, brandLabel.getFont().getSize());

        JTextArea contentArea = (JTextArea) components[3];
        assertEquals("AnotherContent", contentArea.getText());
        assertFalse(contentArea.isEditable());
    }
}
