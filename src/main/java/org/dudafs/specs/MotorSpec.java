package org.dudafs.specs;

import org.dudafs.ItemSpec;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MotorSpec implements ItemSpec {
    private final Integer minPower, maxPower;
    private final String transmission;
    private final int maxSpeed;
    private final int fuelCapacity;

    public MotorSpec(int minPower, int maxPower, String transmission, int maxSpeed, int fuelCapacity) {
        this.minPower = minPower;
        this.maxPower = maxPower;
        this.transmission = transmission;
        this.maxSpeed = maxSpeed;
        this.fuelCapacity = fuelCapacity;
    }

    public int getPower() {
        return minPower;
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
}
