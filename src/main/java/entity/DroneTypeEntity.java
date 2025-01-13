package main.java.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * DroneType Entity
 */
public class DroneTypeEntity implements Serializable {

    // To fix the serialVersionUID
    @Serial
    private static final long serialVersionUID = 1879467100886065125L;

    /// The Id of the DroneType
    public int id = 0;

    /// The Manufacturer
    public String manufacturer = "";

    /// The Typename
    public String typename = "";

    /// The Weight of this DroneType
    public int weight = 0;

    /// The Max Speed
    public int max_speed = 0;

    /// Battery Capacity
    public int battery_capacity = 0;

    /// Control Range
    public int control_range = 0;

    /// The Max Carriage this Drone can Carry
    public int max_carriage = 0;

    public DroneTypeEntity(int id, String manufacturer, String typename, int weight, int max_speed, int battery_capacity, int control_range, int max_carriage) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.typename = typename;
        this.weight = weight;
        this.max_speed = max_speed;
        this.battery_capacity = battery_capacity;
        this.control_range = control_range;
        this.max_carriage = max_carriage;
    }

    /**
     * check if this drone type entity matches with keyword
     * Especially for drone type's manufacturer and typename.
     * @param keyword for searching
     * @return boolean value that this drone type matches with condition
     */
    public boolean checkIfKeywordMatches(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return true; // If empty value comes in, all the drone types return.
        }

        Pattern pattern = Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE);

        return (typename != null && pattern.matcher(this.typename).find())
                || (manufacturer != null && pattern.matcher(this.manufacturer).find());
    }

    /**
     * ToString Override method for Test
     * @return All DroneTypeEntity's field value String
     */
    @Override
    public String toString() {
        return "DroneTypeEntity{" +
                "id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                ", typename='" + typename + '\'' +
                ", weight=" + weight +
                ", max_speed=" + max_speed +
                ", battery_capacity=" + battery_capacity +
                ", control_range=" + control_range +
                ", max_carriage=" + max_carriage +
                '}';
    }
}
