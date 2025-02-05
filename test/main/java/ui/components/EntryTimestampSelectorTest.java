package main.java.ui.components;

        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;

        import static org.junit.jupiter.api.Assertions.*;

        class EntryTimestampSelectorTest {

            private EntryTimestampSelector selector;

            @BeforeEach
            void setUp() {
                selector = new EntryTimestampSelector(20);
                selector.getTimestampComboBox().setSelectedIndex(10); // Set initial index to the middle
            }

            @Test
            void initialSelectedIndex_ShouldBeMiddleEntry() {
                assertEquals(9, selector.getSelectedEntryIndex(), "Initial selected index should be 9");
            }

            @Test
            void skipToOldestDroneData_ShouldSetIndexToFirstEntry() {
                selector.getSkipToOldestDroneData().doClick();
                assertEquals(0, selector.getSelectedEntryIndex(), "After clicking skipLeft1, selected index should be 0");
            }

            @Test
            void skipToOneOlderDroneData_ShouldDecreaseIndexByOne() {
                selector.getSkipToOneOlderDroneData().doClick();
                assertEquals(8, selector.getSelectedEntryIndex(), "After clicking skipLeft2, selected index should be 10");
            }

            @Test
            void skipToOneLaterDroneData_ShouldIncreaseIndexByOne() {
                selector.getSkipToOneLaterDroneData().doClick();
                assertEquals(10, selector.getSelectedEntryIndex(), "After clicking skipRight1, selected index should be 8");
            }

            @Test
            void skipToLatestDroneData_ShouldSetIndexToLastEntry() {
                selector.getSkipToLatestDroneData().doClick();
                assertEquals(19, selector.getSelectedEntryIndex(), "After clicking skipRight2, selected index should be 19");
            }

            @Test
            void buttonStatesAtStart_ShouldEnableAllButtons() {
                assertTrue(selector.getSkipToOldestDroneData().isEnabled(), "skipLeft1 should be enabled at start");
                assertTrue(selector.getSkipToOneOlderDroneData().isEnabled(), "skipLeft2 should be enabled at start");
                assertTrue(selector.getSkipToOneLaterDroneData().isEnabled(), "skipRight1 should be enabled at start");
                assertTrue(selector.getSkipToLatestDroneData().isEnabled(), "skipRight2 should be enabled at start");
            }
        }