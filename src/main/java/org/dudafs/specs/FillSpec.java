package org.dudafs.specs;

import java.util.LinkedHashMap;
import java.util.Map;

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

    @Override
    public Map<String, String> toCsvFields() {
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("MinCapacity", Integer.toString(minCapacity));
        fields.put("MaxCapacity", Integer.toString(maxCapacity));
        fields.put("fillTypes", fillTypes);

        return fields;
    }
}
