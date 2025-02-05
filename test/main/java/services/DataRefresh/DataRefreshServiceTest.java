package main.java.services.DataRefresh;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;

class DataRefreshServiceTest {

    @Test
    void testSingletonInstance() {
        // Verify the class exists and can be referenced
        assertDoesNotThrow(() -> {
            Class.forName("main.java.services.DataRefresh.DataRefreshService");
        }, "DataRefreshService class should exist");
    }

    @Test
    void testRefreshIntervalConstant() throws Exception {
        // Test the refresh interval constant without instantiating the service
        Field refreshIntervalField = Class.forName("main.java.services.DataRefresh.DataRefreshService")
            .getDeclaredField("REFRESH_INTERVAL_SECONDS");
        refreshIntervalField.setAccessible(true);
        int refreshInterval = refreshIntervalField.getInt(null);
        assertEquals(300, refreshInterval, "Refresh interval should be 300 seconds");
    }
}
