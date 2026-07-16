package org.dudafs.specs;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static org.dudafs.CategoryIndex.find;

public class SowingSpec implements ItemSpec {
    private final boolean useDirectPlanting;
    private final String seedFruitTypeCategories;

    public SowingSpec(boolean useDirectPlanting, String seedFruitTypeCategories) {
        this.useDirectPlanting = useDirectPlanting;
        this.seedFruitTypeCategories = seedFruitTypeCategories;
    }

    public boolean isUseDirectPlanting() {
        return useDirectPlanting;
    }

    public String getSeedFruitTypeCategories() {
        return seedFruitTypeCategories;
    }

    @Override
    public Map<String, String> toCsvFields() {
        Map<String, String> fields = new LinkedHashMap<>();
        fields.put("useDirectPlanting", String.valueOf(useDirectPlanting));
        fields.put("seedFruitTypeCategories", String.valueOf(find(seedFruitTypeCategories.trim().toUpperCase(Locale.ROOT))));
        return fields;
    }
}
