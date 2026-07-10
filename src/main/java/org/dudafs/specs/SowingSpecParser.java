package org.dudafs.specs;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Objects;
import java.util.Optional;

public class SowingSpecParser {
    public Optional<SowingSpec> parseSowingSpec(Document document){
        Element useDirectPlanting = (Element) document.getElementsByTagName("useDirectPlanting").item(0);
        boolean isUseDirectPlanting = false;

        if(useDirectPlanting == null)
        {
            return Optional.empty();
        }

        isUseDirectPlanting = useDirectPlanting.getAttribute("value").equals("true");

        String seedFruitTypeCategories = document.getElementsByTagName("seedFruitTypeCategories").item(0).getTextContent();

        return Optional.of(new SowingSpec(isUseDirectPlanting, seedFruitTypeCategories));
    }
}
