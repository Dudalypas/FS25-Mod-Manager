package org.dudafs.specs;

import java.util.LinkedHashMap;
import java.util.Map;

public class BaleSpec implements ItemSpec {
    private final String baleType;
    private final int minBaleDiameter;
    private final int maxBaleDiameter;
    private final int minBaleLength;
    private final int maxBaleLength;

    public BaleSpec(String baleType, int minBaleDiameter, int maxBaleDiameter, int minBaleLength, int maxBaleLength) {
        this.baleType = baleType;
        this.minBaleDiameter = minBaleDiameter;
        this.maxBaleDiameter = maxBaleDiameter;
        this.minBaleLength = minBaleLength;
        this.maxBaleLength = maxBaleLength;
    }

    public String getbaleType() {
        return baleType;
    }

    public int getMaxBaleDiameter() {
        return maxBaleDiameter;
    }

    public int getMinBaleDiameter() {
        return minBaleDiameter;
    }

    public int getMinBaleLength() {
        return minBaleLength;
    }

    public int getMaxBaleLength() {
        return maxBaleLength;
    }

    @Override
    public Map<String, String> toCsvFields() {
        Map<String, String> fields = new LinkedHashMap<>();

        fields.put("minBaleDiameter_cm", Integer.toString(minBaleDiameter));
        fields.put("maxBaleDiameter_cm", Integer.toString(maxBaleDiameter));
        fields.put("minBaleLength_cm", Integer.toString(minBaleLength));
        fields.put("maxBaleLength_cm", Integer.toString(maxBaleLength));

        return fields;
    }
}
