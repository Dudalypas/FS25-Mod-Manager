package org.dudafs.specs;

import org.dudafs.ItemSpec;

public class WorkingSpeedSpec implements ItemSpec {
    private final double workingSpeed;

    public WorkingSpeedSpec(double workingSpeed) {
        this.workingSpeed = workingSpeed;
    }

    public double getWorkingSpeed() {
        return workingSpeed;
    }
}
