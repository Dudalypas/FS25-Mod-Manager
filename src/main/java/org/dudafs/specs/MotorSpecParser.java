package org.dudafs.specs;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class MotorSpecParser {

    public Optional<MotorSpec> parse(Document document) {
        int minPower = 0;
        int maxPower = 0;
        int maxSpeed = 0;
        int fuelCapacity = 0;
        NodeList fillUnitNodes = null;
        String transmissionName = "Automatic";
        NodeList items = document.getElementsByTagName("motorConfiguration");

        if (items.getLength() == 0) {
            return Optional.empty();
        }

        Element firstMotorConfiguration = (Element) items.item(0);
        Element lastMotorConfiguration = (Element) items.item(items.getLength() - 1);
        Element power = (Element) firstMotorConfiguration.getElementsByTagName("power").item(0);

        // TODO make it a cycle, because mods do not necessarily order them by horsepower, most do, but ladadada
        if (firstMotorConfiguration.hasAttribute("hp")) {
            minPower = Integer.parseInt(firstMotorConfiguration.getAttribute("hp"));
            maxPower = lastMotorConfiguration.hasAttribute("hp") ? Integer.parseInt(lastMotorConfiguration.getAttribute("hp")) : minPower;
        } else if (power != null) {
            minPower = Integer.parseInt(power.getTextContent());
            maxPower = minPower;
        }

        Element transmission = (Element) document.getElementsByTagName("transmission").item(0);
        if (transmission != null) {
            transmissionName = transmission.getAttribute("name");
        }

        Element motor = (Element) document.getElementsByTagName("motor").item(0);
        if (motor != null && motor.hasAttribute("maxForwardSpeed")) {
            maxSpeed = (int) Double.parseDouble(motor.getAttribute("maxForwardSpeed"));
        }

        Element fillUnits = (Element) document.getElementsByTagName("fillUnits").item(0);
        if (fillUnits != null) {
            fillUnitNodes = fillUnits.getElementsByTagName("fillUnit");
        }

        String displayUnit = "l";
        for (int i = 0; i < fillUnitNodes.getLength(); i++) {
            Element fillUnit = (Element) fillUnitNodes.item(i);
            String fillTypes = fillUnit.getAttribute("fillTypes").trim().toUpperCase(Locale.ROOT);

            if ("DIESEL".equals(fillTypes) || "ELECTRICCHARGE".equals(fillTypes) || "METHANE".equals(fillTypes)) {
                fuelCapacity = (int) Double.parseDouble(fillUnit.getAttribute("capacity"));
                displayUnit = fillUnit.getAttribute("unitTextOverride");
            }
            if (Objects.equals(displayUnit, "$l10n_unit_kw")) {
                displayUnit = "kW";
            } else {
                displayUnit = "l";
            }
        }

        return Optional.of(new MotorSpec(minPower, maxPower, transmissionName, maxSpeed, fuelCapacity, displayUnit));
    }
}
