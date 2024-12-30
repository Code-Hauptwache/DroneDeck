package main.java.ui.dtos;

public class DroneCatalogCardDto {
    private String typename;
    private String manufacturer;
    private int weight;
    private int max_speed;
    private int battery_capacity;
    private int control_range;
    private int max_carriage;

    public DroneCatalogCardDto(String typename, String manufacturer, int weight, int max_speed, int battery_capacity, int control_range, int max_carriage) {
        this.typename = typename;
        this.manufacturer = manufacturer;
        this.weight = weight;
        this.max_speed = max_speed;
        this.battery_capacity = battery_capacity;
        this.control_range = control_range;
        this.max_carriage = max_carriage;
    }

    public String getTypename() {
        return typename;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getWeight() {
        return weight;
    }

    public int getMaxSpeed() {
        return max_speed;
    }

    public int getBatteryCapacity() {
        return battery_capacity;
    }

    public int getControlRange() {
        return control_range;
    }

    public int getMaxCarriage() {
        return max_carriage;
    }
}
