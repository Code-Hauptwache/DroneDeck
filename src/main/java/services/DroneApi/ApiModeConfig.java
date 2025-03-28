package main.java.services.DroneApi;

/**
 * Configuration class for API mode settings.
 * This allows switching between live API and demo mode with mock data.
 */
public class ApiModeConfig {
    private static boolean demoMode = false;
    
    /**
     * Check if demo mode is enabled
     * @return true if in demo mode, false if using live API
     */
    public static boolean isDemoMode() {
        return demoMode;
    }
    
    /**
     * Enable or disable demo mode
     * @param enable true to enable demo mode, false to use live API
     */
    public static void setDemoMode(boolean enable) {
        demoMode = enable;
    }
}