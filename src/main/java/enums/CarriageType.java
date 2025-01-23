package main.java.enums;

/**
 * Enum for carriage types.
 */
public enum CarriageType {
    SEN("Sensor"),
    ACT("Actuator"),
    NONE("None");

    private final String displayName;

    CarriageType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Utility to convert code (e.g., "SEN", "ACT") to CarriageType enum.
     * Returns NONE if code is unrecognized.
     */
    public static CarriageType fromCode(String code) {
        if (code == null) {
            return NONE;
        }
        return switch (code) {
            case "SEN" -> SEN;
            case "ACT" -> ACT;
            default -> NONE;
        };
    }
}
