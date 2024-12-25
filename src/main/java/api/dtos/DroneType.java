package main.java.api.dtos;

import java.io.Serializable;

public class DroneType implements Serializable {

    public int id = 0;

    public String manufacturer = "";

    public String typename = "";

    public int weight = 0;

    public int max_speed = 0;

    public int battery_capacity = 0;

    public int control_range = 0;

    public int max_carriage = 0;

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
}
