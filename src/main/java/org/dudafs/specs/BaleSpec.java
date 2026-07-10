package org.dudafs.specs;

import org.dudafs.ItemSpec;

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
}
