package org.dudafs.specs;

import java.util.Map;

public class WorkingWidthSpec implements ItemSpec {
    private final double workingWidth;

    public WorkingWidthSpec(double workingWidth) {
        this.workingWidth = workingWidth;
    }

    public double getWorkingWidth() {
        return workingWidth;
    }

    @Override
    public Map<String, String> toCsvFields() {
        return Map.of("WorkingWidth_m", Double.toString(workingWidth));
    }
}
