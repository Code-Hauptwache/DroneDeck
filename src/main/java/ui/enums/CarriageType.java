package main.java.ui.enums;

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
        switch (code) {
            case "SEN": return SEN;
            case "ACT": return ACT;
            default:    return NONE;
        }
    }
}
