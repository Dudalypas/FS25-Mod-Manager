package org.dudafs.specs;

import java.util.LinkedHashMap;
import java.util.Map;

public class MotorSpec implements ItemSpec {
    private final Integer minPower, maxPower;
    private final String transmission;
    private final int maxSpeed;
    private final int fuelCapacity;
    private final String displayUnit;

    public MotorSpec(Integer minPower, Integer maxPower, String transmission, int maxSpeed, int fuelCapacity, String displayUnit) {
        this.minPower = minPower;
        this.maxPower = maxPower;
        this.transmission = transmission;
        this.maxSpeed = maxSpeed;
        this.fuelCapacity = fuelCapacity;
        this.displayUnit = displayUnit;
    }

    public Integer getMinPower() {
        return minPower;
    }

    public Integer getMaxPower() {
        return maxPower;
    }

    public String getTransmission() {
        return transmission;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getFuelCapacity() {
        return fuelCapacity;
    }

    public String getDisplayUnit() {
        return displayUnit;
    }

    @Override
    public Map<String, String> toCsvFields() {
        Map <String, String> fields = new LinkedHashMap<>();
        fields.put("MinPower", minPower.toString());
        fields.put("MaxPower", maxPower.toString());
        fields.put("Transmission", transmission);
        fields.put("MaxSpeed_km/h", Integer.toString(maxSpeed));
        fields.put("FuelCapacity_" + displayUnit, Integer.toString(fuelCapacity));

        return fields;
    }
}
