package org.dudafs.specs;

import org.dudafs.ItemSpec;

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
}
