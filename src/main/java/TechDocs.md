## Pull Request Description

In this pull request, I have implemented the following:

1. **DataRefreshService**: A singleton service responsible for automatically refreshing drone data at regular intervals. Key features include:
   - Periodic data refresh every 300 seconds (5 minutes)
   - Automatic retry mechanism for failed refreshes
   - Thread-safe singleton implementation
   - Comprehensive error handling and logging
   - UI updates on the Event Dispatch Thread (EDT)
   - Graceful shutdown handling

2. **DataRefreshServiceTest**: Unit tests for `DataRefreshService` to verify its core functionality:
   - Singleton pattern implementation verification
   - Constant value validation
   - Class existence and accessibility tests

3. **Loading Progress System**: A comprehensive loading feedback system that provides:
   - Detailed progress tracking during initialization
   - Visual progress bar with status messages
   - Synchronized UI and console logging
   - Progress mapping between different loading stages
   - Emoji-enhanced log messages for better readability

The service performs the following operations during each refresh cycle:
1. Fetches basic drone data for all drones
2. Pre-fetches dynamics data for each drone
3. Updates the UI components (Dashboard and Catalog) with the refreshed data

Additionally:
- The service uses a `ScheduledExecutorService` for reliable periodic execution
- Failed refreshes are automatically retried after a 5-second delay
- All UI updates are properly dispatched to the EDT using `SwingUtilities.invokeLater`
- Comprehensive logging is implemented for monitoring and debugging
- The service can be manually triggered using `triggerRefreshData()`
- Loading progress is tracked and displayed through both UI and logging:
  * Initial setup (0-30%): Services, API token, configuration
  * Data loading (40-90%): API connection, data fetching, processing
  * Completion (100%): Final initialization and UI updates

---

## Documentation

### DataRefreshService

The `DataRefreshService` class is a singleton service that manages automatic data refresh operations.

---

**Key Methods:**
```java
public static synchronized DataRefreshService getInstance()
```
- Returns the singleton instance of the service

```java
public void triggerRefreshData()
```
- Manually triggers a data refresh operation

```java
public void shutdown()
```
- Gracefully shuts down the refresh service

---

**Configuration:**
- Refresh Interval: 300 seconds (5 minutes)
- Retry Delay: 5 seconds
- Thread Pool: Single-threaded executor

---

### DataRefreshServiceTest

The `DataRefreshServiceTest` class contains unit tests that verify the core functionality of the `DataRefreshService`.

---

**Example Test:**
```java
@Test
void testRefreshIntervalConstant() throws Exception {
    Field refreshIntervalField = Class.forName("main.java.services.DataRefresh.DataRefreshService")
        .getDeclaredField("REFRESH_INTERVAL_SECONDS");
    refreshIntervalField.setAccessible(true);
    int refreshInterval = refreshIntervalField.getInt(null);
    assertEquals(300, refreshInterval, "Refresh interval should be 300 seconds");
}
```

---

### Integration Points

The service integrates with several components:
- `DroneController`: For fetching drone data
- `DroneDashboard`: For updating the dashboard UI
- `DroneCatalog`: For updating the catalog UI
- `LocalDroneDao`: For accessing local drone data
- `IDroneApiService`: For fetching drone dynamics data

The service ensures that all data updates are properly synchronized and UI updates occur on the EDT to maintain thread safety in the Swing application.
