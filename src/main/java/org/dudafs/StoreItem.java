package org.dudafs;

import org.dudafs.specs.ItemSpec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StoreItem {
    private final String brand;
    private final String name;
    private final String category;
    private final int price;

    private final List<ItemSpec> specs = new ArrayList<>();

    public StoreItem(String brand, String name, String category, int price) {
        this.brand = brand;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public void addSpec(ItemSpec spec) {
        if (spec == null) {
            return;
        }

        specs.add(spec);
    }

    public <T extends ItemSpec> Optional<T> getSpec(Class<T> specClass) {
        for (ItemSpec spec : specs) {
            if (specClass.isInstance(spec)) {
                return Optional.of(specClass.cast(spec));
            }
        }
        return Optional.empty();
    }

    public <T extends ItemSpec> boolean hasSpec(Class<T> specClass) {
        return getSpec(specClass).isPresent();
    }

    public List<ItemSpec> getSpecs() {
        return Collections.unmodifiableList(specs);
    }

    public String getDisplayName() {
        return brand + " " + name;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }
}
