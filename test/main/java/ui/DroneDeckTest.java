package main.java.ui;

import main.java.DroneDeck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DroneDeckTest {

    @Test
    void testMain() {
        try {
            DroneDeck.main(new String[]{});
        } catch (Exception e) {
            fail("Exception occurred during test execution: " + e.getMessage());
        }
    }
}