package org.dudafs.parser;

import org.dudafs.model.specs.MotorSpec;
import org.dudafs.xml.XmlHelper;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MotorSpecParserTest {

    @Test
    public void shouldReturnMinPowerWhenPresent() throws Exception {
        String xml = """
        <vehicle>
        <motorConfigurations>
            <motorConfiguration name="8R 280" hp="326" price="0" consumerConfigurationIndex="1">
                <motor torqueScale="1.377" minRpm="900" maxRpm="2200" maxForwardSpeed="53">
                </motor>
            </motorConfiguration>
            <motorConfiguration name="8R 410" hp="458" price="56500" consumerConfigurationIndex="5">
                <motor torqueScale="1.935" dampingRateScale="1.9"/>
                <transmission name="Powershift">
                </transmission>
            </motorConfiguration>
        </motorConfigurations>
        <fillUnit>
            <fillUnitConfigurations>
                <fillUnitConfiguration>
                    <fillUnits>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="diesel" capacity="727"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="def" capacity="37"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="air" capacity="3000"/>
                    </fillUnits>
                </fillUnitConfiguration>
            </fillUnitConfigurations>
        </fillUnit>
        </vehicle>
        """;

        Document document = XmlHelper.loadXmlFromString(xml);
        MotorSpecParser motorSpecParser = new MotorSpecParser();
        Optional<MotorSpec> result = motorSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals(326, result.get().getMinPower());
    }

    @Test
    public void shouldReturnMaxPowerWhenPresent() throws Exception {
        String xml = """
        <vehicle>
        <motorConfigurations>
            <motorConfiguration name="8R 280" hp="326" price="0" consumerConfigurationIndex="1">
                <motor torqueScale="1.377" minRpm="900" maxRpm="2200" maxForwardSpeed="53">
                </motor>
            </motorConfiguration>
            <motorConfiguration name="8R 410" hp="458" price="56500" consumerConfigurationIndex="5">
                <motor torqueScale="1.935" dampingRateScale="1.9"/>
                <transmission name="Powershift">
                </transmission>
            </motorConfiguration>
        </motorConfigurations>
        <fillUnit>
            <fillUnitConfigurations>
                <fillUnitConfiguration>
                    <fillUnits>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="diesel" capacity="727"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="def" capacity="37"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="air" capacity="3000"/>
                    </fillUnits>
                </fillUnitConfiguration>
            </fillUnitConfigurations>
        </fillUnit>
        </vehicle>
        """;

        Document document = XmlHelper.loadXmlFromString(xml);
        MotorSpecParser motorSpecParser = new MotorSpecParser();
        Optional<MotorSpec> result = motorSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals(458, result.get().getMaxPower());
    }

    @Test
    public void shouldReturnTransmissionNameWhenPresent() throws Exception {
        String xml = """
        <vehicle>
        <motorConfigurations>
            <motorConfiguration name="8R 280" hp="326" price="0" consumerConfigurationIndex="1">
                <motor torqueScale="1.377" minRpm="900" maxRpm="2200" maxForwardSpeed="53">
                </motor>
            </motorConfiguration>
            <motorConfiguration name="8R 410" hp="458" price="56500" consumerConfigurationIndex="5">
                <motor torqueScale="1.935" dampingRateScale="1.9"/>
                <transmission name="Powershift">
                </transmission>
            </motorConfiguration>
        </motorConfigurations>
        <fillUnit>
            <fillUnitConfigurations>
                <fillUnitConfiguration>
                    <fillUnits>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="diesel" capacity="727"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="def" capacity="37"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="air" capacity="3000"/>
                    </fillUnits>
                </fillUnitConfiguration>
            </fillUnitConfigurations>
        </fillUnit>
        </vehicle>
        """;

        Document document = XmlHelper.loadXmlFromString(xml);
        MotorSpecParser motorSpecParser = new MotorSpecParser();
        Optional<MotorSpec> result = motorSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals("Powershift", result.get().getTransmission());
    }

    @Test
    public void shouldReturnMaxSpeedWhenPresent() throws Exception {
        String xml = """
        <vehicle>
        <motorConfigurations>
            <motorConfiguration name="8R 280" hp="326" price="0" consumerConfigurationIndex="1">
                <motor torqueScale="1.377" minRpm="900" maxRpm="2200" maxForwardSpeed="53">
                </motor>
            </motorConfiguration>
            <motorConfiguration name="8R 410" hp="458" price="56500" consumerConfigurationIndex="5">
                <motor torqueScale="1.935" dampingRateScale="1.9"/>
                <transmission name="Powershift">
                </transmission>
            </motorConfiguration>
        </motorConfigurations>
        <fillUnit>
            <fillUnitConfigurations>
                <fillUnitConfiguration>
                    <fillUnits>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="diesel" capacity="727"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="def" capacity="37"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="air" capacity="3000"/>
                    </fillUnits>
                </fillUnitConfiguration>
            </fillUnitConfigurations>
        </fillUnit>
        </vehicle>
        """;

        Document document = XmlHelper.loadXmlFromString(xml);
        MotorSpecParser motorSpecParser = new MotorSpecParser();
        Optional<MotorSpec> result = motorSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals(53, result.get().getMaxSpeed());
    }

    @Test
    public void shouldReturnFuelCapacityWhenPresent() throws Exception {
        String xml = """
        <vehicle>
        <motorConfigurations>
            <motorConfiguration name="8R 280" hp="326" price="0" consumerConfigurationIndex="1">
                <motor torqueScale="1.377" minRpm="900" maxRpm="2200" maxForwardSpeed="53">
                </motor>
            </motorConfiguration>
            <motorConfiguration name="8R 410" hp="458" price="56500" consumerConfigurationIndex="5">
                <motor torqueScale="1.935" dampingRateScale="1.9"/>
                <transmission name="Powershift">
                </transmission>
            </motorConfiguration>
        </motorConfigurations>
        <fillUnit>
            <fillUnitConfigurations>
                <fillUnitConfiguration>
                    <fillUnits>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="diesel" capacity="727"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="def" capacity="37"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="air" capacity="3000"/>
                    </fillUnits>
                </fillUnitConfiguration>
            </fillUnitConfigurations>
        </fillUnit>
        </vehicle>
        """;

        Document document = XmlHelper.loadXmlFromString(xml);
        MotorSpecParser motorSpecParser = new MotorSpecParser();
        Optional<MotorSpec> result = motorSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals(727, result.get().getFuelCapacity());
    }

    @Test
    public void shouldReturnDieselDisplayUnitWhenPresent() throws Exception {
        String xml = """
        <vehicle>
        <motorConfigurations>
            <motorConfiguration name="8R 280" hp="326" price="0" consumerConfigurationIndex="1">
                <motor torqueScale="1.377" minRpm="900" maxRpm="2200" maxForwardSpeed="53">
                </motor>
            </motorConfiguration>
            <motorConfiguration name="8R 410" hp="458" price="56500" consumerConfigurationIndex="5">
                <motor torqueScale="1.935" dampingRateScale="1.9"/>
                <transmission name="Powershift">
                </transmission>
            </motorConfiguration>
        </motorConfigurations>
        <fillUnit>
            <fillUnitConfigurations>
                <fillUnitConfiguration>
                    <fillUnits>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="diesel" capacity="727"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="def" capacity="37"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="air" capacity="3000"/>
                    </fillUnits>
                </fillUnitConfiguration>
            </fillUnitConfigurations>
        </fillUnit>
        </vehicle>
        """;

        Document document = XmlHelper.loadXmlFromString(xml);
        MotorSpecParser motorSpecParser = new MotorSpecParser();
        Optional<MotorSpec> result = motorSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals("l", result.get().getDisplayUnit());
    }

    @Test
    public void shouldReturnMethaneDisplayUnitWhenPresent() throws Exception {
        String xml = """
        <vehicle>
        <motorConfigurations>
            <motorConfiguration name="8R 280" hp="326" price="0" consumerConfigurationIndex="1">
                <motor torqueScale="1.377" minRpm="900" maxRpm="2200" maxForwardSpeed="53">
                </motor>
            </motorConfiguration>
            <motorConfiguration name="8R 410" hp="458" price="56500" consumerConfigurationIndex="5">
                <motor torqueScale="1.935" dampingRateScale="1.9"/>
                <transmission name="Powershift">
                </transmission>
            </motorConfiguration>
        </motorConfigurations>
        <fillUnit>
            <fillUnitConfigurations>
                <fillUnitConfiguration>
                    <fillUnits>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="methane" capacity="727"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="def" capacity="37"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="air" capacity="3000"/>
                    </fillUnits>
                </fillUnitConfiguration>
            </fillUnitConfigurations>
        </fillUnit>
        </vehicle>
        """;

        Document document = XmlHelper.loadXmlFromString(xml);
        MotorSpecParser motorSpecParser = new MotorSpecParser();
        Optional<MotorSpec> result = motorSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals("l", result.get().getDisplayUnit());
    }

    @Test
    public void shouldReturnElectricChargeDisplayUnitWhenPresent() throws Exception {
        String xml = """
        <vehicle>
        <motorConfigurations>
            <motorConfiguration name="8R 280" hp="326" price="0" consumerConfigurationIndex="1">
                <motor torqueScale="1.377" minRpm="900" maxRpm="2200" maxForwardSpeed="53">
                </motor>
            </motorConfiguration>
            <motorConfiguration name="8R 410" hp="458" price="56500" consumerConfigurationIndex="5">
                <motor torqueScale="1.935" dampingRateScale="1.9"/>
                <transmission name="Powershift">
                </transmission>
            </motorConfiguration>
        </motorConfigurations>
        <fillUnit>
            <fillUnitConfigurations>
                <fillUnitConfiguration>
                    <fillUnits>
                        <fillUnit unitTextOverride="$l10n_unit_kw" fillTypes="ELECTRICCHARGE" capacity="727"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="def" capacity="37"/>
                        <fillUnit unitTextOverride="$l10n_unit_literShort" fillTypes="air" capacity="3000"/>
                    </fillUnits>
                </fillUnitConfiguration>
            </fillUnitConfigurations>
        </fillUnit>
        </vehicle>
        """;

        Document document = XmlHelper.loadXmlFromString(xml);
        MotorSpecParser motorSpecParser = new MotorSpecParser();
        Optional<MotorSpec> result = motorSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals("kW", result.get().getDisplayUnit());
    }
}
