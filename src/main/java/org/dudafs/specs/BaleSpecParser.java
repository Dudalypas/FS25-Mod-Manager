package org.dudafs.specs;

import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.*;


public class BaleSpecParser {
    public Optional<BaleSpec> parse(Document document) {
        if (document.getElementsByTagName("baleType").getLength() == 0) {
            return Optional.empty();
        }

        String baleTypeName = "Square";
        NodeList baleTypesList = document.getElementsByTagName("baleTypes");
        Element baleTypes = null;

        if (baleTypesList.getLength() > 0) {
            baleTypes = (Element) baleTypesList.item(0);
        }

        Element baleType;

        assert baleTypes != null;
        NodeList baleTypeNodes = baleTypes.getElementsByTagName("baleType");

        Map<String, List<Double>> sizes = new HashMap<>();
        for (int i = 0; i < baleTypeNodes.getLength(); i++) {
            baleType = (Element) baleTypes.getElementsByTagName("baleType").item(i);
            if (!baleType.getAttribute("diameter").isEmpty()) {
                sizes.computeIfAbsent("Round", k -> new ArrayList<>()).add(Double.parseDouble(baleType.getAttribute("diameter")));
            } else if (!baleType.getAttribute("length").isEmpty()) {
                sizes.computeIfAbsent("Square", k -> new ArrayList<>()).add(Double.parseDouble(baleType.getAttribute("length")));
            }
        }

        int minBaleDiameter = 0;
        int maxBaleDiameter = 0;
        if (sizes.get("Round") != null) {
            minBaleDiameter = (int) (Collections.min(sizes.get("Round"))*100);
            maxBaleDiameter = (int) (Collections.max(sizes.get("Round"))*100);
        }

        int minBaleLength = 0;
        int maxBaleLength = 0;
        if (sizes.get("Square") != null) {
            minBaleLength = (int) (Collections.min(sizes.get("Square"))*100);
            maxBaleLength = (int) (Collections.max(sizes.get("Square"))*100);
        }

        baleType = (Element) baleTypes.getElementsByTagName("baleType").item(0);
        if(baleType.hasAttribute("length") && baleType.hasAttribute("diameter")){
            baleTypeName = "Mixed";
        } else if (baleType.getAttribute("isRoundBale").equals("true")) {
            baleTypeName = "Round";
        }


        return Optional.of(new BaleSpec(baleTypeName, minBaleDiameter, maxBaleDiameter, minBaleLength, maxBaleLength));
    }
}
