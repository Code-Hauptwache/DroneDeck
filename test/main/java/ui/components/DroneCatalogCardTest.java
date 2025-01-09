package main.java.ui.components;

import main.java.ui.dtos.DroneDto;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DroneCatalogCardTest {

    @Test
    void testDroneCatalogCardCreation() {
        DroneCatalogCard card = getDroneCatalogCard();

        assertNotNull(card, "The DroneCatalogCard should not be null");
        assertInstanceOf(BorderLayout.class, card.getLayout(), "The layout should be BorderLayout");

        Component[] components = card.getComponents();
        assertEquals(1, components.length, "There should be one main component in the card");

        assertInstanceOf(CardTemplate.class, components[0], "The main component should be an instance of CardTemplate");
    }

    private static @NotNull DroneCatalogCard getDroneCatalogCard() {
        DroneDto dto = new DroneDto(
                1,
                "Test Drone",
                "Test Manufacturer",
                "Active",
                100,
                200,
                50.0,
                10.0,
                20.0,
                "12345",
                52.0,
                "SEN",
                60.0,
                "2024-12-15T17:00:52.588123+01:00",
                732.0,
                54.0,
                250.0,
                "2024-12-15T17:00:52.588123+01:00"
        );

        DroneCatalogCard card = new DroneCatalogCard(dto);
        return card;
    }
}