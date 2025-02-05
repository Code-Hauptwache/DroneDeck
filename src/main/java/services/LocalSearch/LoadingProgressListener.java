package main.java.services.LocalSearch;

/**
 * Interface for receiving loading progress updates from LocalSearchService.
 */
public interface LoadingProgressListener {
    /**
     * Called when loading progress is updated.
     * @param progress The current progress (0-100)
     * @param status The current status message
     */
    void onProgressUpdate(int progress, String status);
}
