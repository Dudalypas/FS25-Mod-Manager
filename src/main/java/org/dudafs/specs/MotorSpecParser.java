package org.dudafs.specs;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Optional;

public class MotorSpecParser {

    public Optional<MotorSpec> parse(Document document) {
        int minPower = 0;
        int maxPower = 0;
        int fuelCapacity = 0;
        NodeList items = document.getElementsByTagName("motorConfiguration");

        if (items.getLength() == 0) {
            return Optional.empty();
        }

        Element firstMotorConfiguration = (Element) items.item(0);
        Element lastMotorConfiguration = (Element) items.item(items.getLength() - 1);

        if(!firstMotorConfiguration.hasAttribute("hp")) {
            minPower = Integer.parseInt(document.getElementsByTagName("power").item(0).getTextContent());
            maxPower = Integer.parseInt(document.getElementsByTagName("power").item(0).getTextContent());
        }
        else {
            minPower = Integer.parseInt(firstMotorConfiguration.getAttribute("hp"));
            maxPower = Integer.parseInt(lastMotorConfiguration.getAttribute("hp"));
        }

        Element transmission = (Element) document.getElementsByTagName("transmission").item(0);
        String transmissionName = transmission.getAttribute("name");

        Element motor = (Element) document.getElementsByTagName("motor").item(0);
        int maxSpeed = (int) Double.parseDouble(motor.getAttribute("maxForwardSpeed")); // 44

        Element fillUnits = (Element) document.getElementsByTagName("fillUnits").item(0);
        NodeList fillUnitNodes = fillUnits.getElementsByTagName("fillUnit");

        for (int i = 0; i < fillUnitNodes.getLength(); i++) {
            Element fillUnit = (Element) fillUnitNodes.item(i);

            if ("diesel".equals(fillUnit.getAttribute("fillTypes"))
                    || "electricity".equals(fillUnit.getAttribute("fillTypes"))) {
                fuelCapacity = (int) Double.parseDouble(fillUnit.getAttribute("capacity"));
            }
        }

        return Optional.of(new MotorSpec(minPower, maxPower, transmissionName, maxSpeed, fuelCapacity));
    }
}
