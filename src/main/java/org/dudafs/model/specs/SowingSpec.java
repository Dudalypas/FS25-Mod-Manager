package org.dudafs.model.specs;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static org.dudafs.index.CategoryIndex.find;

public class SowingSpec implements ItemSpec {
    public enum SeedFruitSourceType{
        CATEGORY, EXPLICIT_TYPES
    }

    private final boolean useDirectPlanting;
    private final SeedFruitSourceType seedFruitSourceType;
    private final String seedFruitValue;


    public SowingSpec(boolean useDirectPlanting, SeedFruitSourceType seedFruitSourceType, String seedFruitValue) {
        this.useDirectPlanting = useDirectPlanting;
        this.seedFruitSourceType = seedFruitSourceType;
        this.seedFruitValue = seedFruitValue;
    }

    public boolean isUseDirectPlanting() {
        return useDirectPlanting;
    }

    public SeedFruitSourceType getSeedFruitSourceType() {
        return seedFruitSourceType;
    }

    public String getSeedFruitValue() {
        return seedFruitValue;
    }

    @Override
    public Map<String, String> toCsvFields() {
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("useDirectPlanting", String.valueOf(useDirectPlanting));
        fields.put("seedFruitSourceType", String.valueOf(seedFruitSourceType));
        fields.put("seedFruitValue", String.valueOf(find(seedFruitValue.trim().toUpperCase(Locale.ROOT))));
        return fields;
    }
}
