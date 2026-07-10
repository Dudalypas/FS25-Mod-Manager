package org.dudafs.specs;

import org.dudafs.ItemSpec;

public class WeightSpec implements ItemSpec {
    private final double weight;

    public WeightSpec(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
}
