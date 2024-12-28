package main.java.services.DroneApi.dtos;

import main.java.entity.DroneTypeEntity;

import java.io.Serializable;

/**
 * DroneType Data Transfer Object.
 * The DroneType contains static about the DroneType.
 */
public class DroneType {

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


    /**
     * Pretty string Representation of this class's Data
     * @return String Representation
     */
    @Override
    public String toString() {
        return "DroneType{" +
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

    /**
     * This method makes Dto to Entity
     * @return new DroneTypeEntity
     */
    public DroneTypeEntity toEntity() {
        return new DroneTypeEntity(id, manufacturer, typename, weight, max_speed, battery_capacity, control_range, max_carriage);
    }
}
