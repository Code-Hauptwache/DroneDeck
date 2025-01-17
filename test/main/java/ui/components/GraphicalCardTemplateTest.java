package main.java.ui.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GraphicalCardTemplateTest {
    private GraphicalCardTemplate graphicalCardTemplate;
    private JLabel contentLabel;

    @BeforeEach
    void setUp() {
        contentLabel = new JLabel("Test Content");
        graphicalCardTemplate = new GraphicalCardTemplate("Test Title", contentLabel);
    }

    @Test
    void testGraphicalCardTemplateTitle() {
        Component[] components = graphicalCardTemplate.getComponents();
        boolean titleFound = false;

        for (Component component : components) {
            if (component instanceof JLabel label) {
                if ("Test Title".equals(label.getText())) {
                    titleFound = true;
                    break;
                }
            }
        }

        assertTrue(titleFound, "Title label should be present with the correct text.");
    }

    @Test
    void testGraphicalCardTemplateContent() {
        Component[] components = graphicalCardTemplate.getComponents();
        boolean contentFound = false;

        for (Component component : components) {
            if (component instanceof JPanel panel) {
                Component[] panelComponents = panel.getComponents();
                for (Component panelComponent : panelComponents) {
                    if (panelComponent == contentLabel) {
                        contentFound = true;
                        break;
                    }
                }
            }
        }

        assertTrue(contentFound, "Content component should be present in the card.");
    }

    @Test
    void testGraphicalCardTemplateSize() {
        Dimension expectedSize = new Dimension(CardTemplate.cardWidth, CardTemplate.cardHeight);
        assertEquals(expectedSize, graphicalCardTemplate.getPreferredSize(), "Card size should match the expected size.");
    }
}