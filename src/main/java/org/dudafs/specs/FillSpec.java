package org.dudafs.specs;

import org.dudafs.ItemSpec;

public class FillSpec implements ItemSpec {
    private final String fillTypes;
    private final int capacity;
    private final String displayUnit;

    public FillSpec(String fillTypes, int capacity, String displayUnit) {
        this.fillTypes = fillTypes;
        this.capacity = capacity;
        this.displayUnit = displayUnit;
    }

    public String getFillUnits() {
        return fillTypes;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDisplayUnit() {
        return displayUnit;
    }
}
