package main.java.entity;

import java.io.Serializable;

/**
 * DroneType Entity
 */
public class DroneTypeEntity implements Serializable {

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
