package org.dudafs.specs;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Optional;

public class WorkingSpeedSpecParser {
    public Optional<WorkingSpeedSpec> parse(Document document) {
        Element speedLimit = (Element) document.getElementsByTagName("speedLimit").item(0);
        if(speedLimit == null) {
            return Optional.empty();
        }

        double workingSpeed = Double.parseDouble(speedLimit.getAttribute("value"));

        return Optional.of(new WorkingSpeedSpec(workingSpeed));
    }
}
