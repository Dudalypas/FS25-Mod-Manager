package org.dudafs.specs;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Optional;

public class FillSpecParser {
    public Optional<FillSpec> parse(Document document) {
        NodeList fillUnits = document.getElementsByTagName("fillUnits");

        if (fillUnits.getLength() == 0) {
            return Optional.empty();
        }
        Element fillUnitsContainer;
        NodeList fillUnitElement;
        Element fillUnit = null;
        String fillType = "None";
        String displayUnit = "l";
        int capacity = 0;

        for(int j = 0; j < fillUnits.getLength(); j++) {
            fillUnitsContainer = (Element) document.getElementsByTagName("fillUnits").item(j);
            fillUnitElement = fillUnitsContainer.getElementsByTagName("fillUnit");
            for (int i = 0; i < fillUnitElement.getLength(); i++) {
                fillUnit = (Element) fillUnitsContainer.getElementsByTagName("fillUnit").item(i);
                if (fillUnit.hasAttribute("fillTypes")) {
                    fillType = fillUnit.getAttribute("fillTypes");
                } else if (fillUnit.hasAttribute("fillTypeCategories")) {
                    fillType = fillUnit.getAttribute("fillTypeCategories");
                    displayUnit = fillUnit.getAttribute("shopDisplayUnit");
                }
                else if (fillUnit.getAttribute("fillType").equals("diesel") && document.getElementsByTagName("motorConfiguration").getLength() != 0 || fillUnit.getAttribute("fillType").equals("electricity") && document.getElementsByTagName("motorConfiguration").getLength() != 0) {
                    return Optional.empty();
                }
            }
        }
        assert fillUnit != null;
        if(fillUnit.hasAttribute("capacity")) {
            capacity = (int) Double.parseDouble(fillUnit.getAttribute("capacity"));
        }

        return Optional.of(new FillSpec(fillType, capacity, displayUnit));
    }
}
