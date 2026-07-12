package org.dudafs.specs;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Optional;

public class NeededPowerSpecParser {
    public Optional<NeededPowerSpec> parse(Document document) {
        Element neededPowerElement = (Element) document.getElementsByTagName("neededPower").item(0);
        if(neededPowerElement == null) {
            return Optional.empty();
        }
        int neededPower = Integer.parseInt(neededPowerElement.getTextContent());

        return Optional.of(new NeededPowerSpec(neededPower));
    }
}
