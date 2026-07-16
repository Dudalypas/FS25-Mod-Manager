package org.dudafs.specs;

import java.util.Map;

public class NeededPowerSpec implements ItemSpec {
    int neededPower;

    public NeededPowerSpec(int neededPower) {
        this.neededPower = neededPower;
    }

    public int getNeededPower() {
        return neededPower;
    }

    @Override
    public Map<String, String> toCsvFields(){
        return Map.of("neededPower_hp", String.valueOf(neededPower));
    }
}
