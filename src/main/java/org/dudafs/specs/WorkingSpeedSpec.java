package org.dudafs.specs;

import java.util.Map;

public class WorkingSpeedSpec implements ItemSpec {
    private final double workingSpeed;

    public WorkingSpeedSpec(double workingSpeed) {
        this.workingSpeed = workingSpeed;
    }

    public double getWorkingSpeed() {
        return workingSpeed;
    }

    @Override
    public Map<String, String> toCsvFields() {
        return Map.of("WorkingSpeed_km/h", Double.toString(workingSpeed));
    }
}
