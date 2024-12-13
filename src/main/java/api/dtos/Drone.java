package main.java.api.dtos;

import java.util.Date;

public class Drone {

    public int id = 0;

    public String dronetype = "";

    public Date created = new Date();

    public String serialnumber = "";

    public int carriage_weight = 0;

    public String carriage_type = "";

    public int getDronetypeId() {
        try {
            return Integer.parseInt(dronetype.substring(dronetype.length() - 3, dronetype.length() - 1));
        } catch (Exception ex) {
            //TODO Logging
            return 0;
        }

    }

    @Override
    public String toString() {
        return "Drone{" +
                "id=" + id +
                ", dronetypeId=" + getDronetypeId() +
                ", dronetype='" + dronetype + '\'' +
                ", created=" + created +
                ", serialnumber='" + serialnumber + '\'' +
                ", carriage_weight=" + carriage_weight +
                ", carriage_type='" + carriage_type + '\'' +
                '}';
    }
}
