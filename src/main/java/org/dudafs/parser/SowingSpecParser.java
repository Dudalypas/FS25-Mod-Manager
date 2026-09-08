package org.dudafs.parser;

import org.dudafs.model.specs.SowingSpec;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Optional;

import static org.dudafs.model.specs.SowingSpec.SeedFruitSourceType.CATEGORY;
import static org.dudafs.model.specs.SowingSpec.SeedFruitSourceType.EXPLICIT_TYPES;

public class SowingSpecParser {
    public Optional<SowingSpec> parse(Document document){
        boolean isUseDirectPlanting = false;
        String seedFruitValue = "";
        SowingSpec.SeedFruitSourceType seedFruitSourceType;

        if(document.getElementsByTagName("useDirectPlanting").getLength() > 0) {
            Element useDirectPlanting = (Element) document.getElementsByTagName("useDirectPlanting").item(0);
            isUseDirectPlanting = useDirectPlanting.getAttribute("value").equals("true");
        }


        if(document.getElementsByTagName("seedFruitTypeCategories").getLength() > 0) {
            seedFruitValue = document.getElementsByTagName("seedFruitTypeCategories").item(0).getTextContent();
            seedFruitSourceType = CATEGORY;
        }
        else if (document.getElementsByTagName("seedFruitTypes").getLength() > 0){
            seedFruitValue = document.getElementsByTagName("seedFruitTypes").item(0).getTextContent();
            seedFruitSourceType = EXPLICIT_TYPES;
        }
        else
        {
            return Optional.empty();
        }

        return Optional.of(new SowingSpec(isUseDirectPlanting, seedFruitSourceType, seedFruitValue));
    }
}
