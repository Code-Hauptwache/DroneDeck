package main.java.ui.components;

import main.java.ui.dtos.DroneCatalogCardDto;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DroneCatalogCardTest {

    @Test
    void testDroneCatalogCardCreation() {
        DroneCatalogCardDto dto = new DroneCatalogCardDto(
                "Drone 1",
                "DJI",
                1060,
                53,
                5075,
                1000,
                500
        );

        DroneCatalogCard card = new DroneCatalogCard(dto);

        assertNotNull(card, "The DroneCatalogCard should not be null");
        assertInstanceOf(BorderLayout.class, card.getLayout(), "The layout should be BorderLayout");

        Component[] components = card.getComponents();
        assertEquals(1, components.length, "There should be one main component in the card");

        assertInstanceOf(CardTemplate.class, components[0], "The main component should be an instance of CardTemplate");
    }
}