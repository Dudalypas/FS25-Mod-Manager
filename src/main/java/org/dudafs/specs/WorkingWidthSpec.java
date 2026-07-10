package org.dudafs.specs;

import org.dudafs.ItemSpec;

public class WorkingWidthSpec implements ItemSpec {
    private final double workingWidth;

    public WorkingWidthSpec(double workingWidth) {
        this.workingWidth = workingWidth;
    }

    public double getWorkingWidth() {
        return workingWidth;
    }
}
