package org.dudafs.specs;

import org.dudafs.ItemSpec;

public class NeededPowerSpec implements ItemSpec {
    int neededPower;

    public NeededPowerSpec(int neededPower) {
        this.neededPower = neededPower;
    }

    public int getNeededPower() {
        return neededPower;
    }
}
