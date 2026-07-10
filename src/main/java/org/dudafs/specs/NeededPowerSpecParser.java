package org.dudafs.specs;

import org.w3c.dom.Document;

import java.util.Optional;

public class NeededPowerSpecParser {
    public Optional<NeededPowerSpec> parse(Document document) {
        if(document.getElementsByTagName("NeededPower").item(0) == null) {
            return Optional.empty();
        }
        int NeededPower = Integer.parseInt(document.getElementsByTagName("NeededPower").item(0).getTextContent());

        return Optional.of(new NeededPowerSpec(NeededPower));
    }
}
