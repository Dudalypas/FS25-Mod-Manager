package org.dudafs.specs;

import java.util.Map;

public class WeightSpec implements ItemSpec {
    private final double weight;

    public WeightSpec(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public Map<String, String> toCsvFields() {
        return Map.of("weight_kg", Double.toString(weight));
    }
}
