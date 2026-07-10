package org.dudafs.specs;

import org.w3c.dom.Document;

import java.util.Optional;

public class WorkingWidthSpecParser {
    public Optional<WorkingWidthSpec> parse(Document document) {
        if(document.getElementsByTagName("workingWidth").getLength() == 0) {
            return Optional.empty();
        }
        double workingWidth = Double.parseDouble(document.getElementsByTagName("workingWidth").item(0).getTextContent());

        workingWidth = Math.round(workingWidth*10.0)/10.0;
        return Optional.of(new WorkingWidthSpec(workingWidth));
    }
}
