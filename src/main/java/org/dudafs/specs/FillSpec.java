package org.dudafs.specs;

import org.dudafs.ItemSpec;

public class FillSpec implements ItemSpec {
    private final String fillTypes;
    private final int minCapacity;
    private final int maxCapacity;
    private final String displayUnit;

    public FillSpec(String fillTypes, int minCapacity,  int maxCapacity, String displayUnit) {
        this.fillTypes = fillTypes;
        this.minCapacity = minCapacity;
        this.maxCapacity = maxCapacity;
        this.displayUnit = displayUnit;
    }

    public String getFillUnits() {
        return fillTypes;
    }

    public int getMinCapacity() {
        return minCapacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getFillTypes() {
        return fillTypes;
    }

    public String getDisplayUnit() {
        return displayUnit;
    }
}
