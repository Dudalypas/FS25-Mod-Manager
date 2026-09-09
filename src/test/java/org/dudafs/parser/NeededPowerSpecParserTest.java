package org.dudafs.parser;

import org.dudafs.model.specs.NeededPowerSpec;
import org.dudafs.xml.XmlHelper;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NeededPowerSpecParserTest {

    @Test
    public void shouldReturnNeededPowerWhenPresent() throws Exception {
        String xml = """
                    <vehicle>
                    <storeData>
                        <specs>
                            <neededPower>150</neededPower>
                        </specs>
                    </storeData>
                    </vehicle>
                    """;

        Document document = XmlHelper.loadXmlFromString(xml);

        NeededPowerSpecParser neededPowerSpecParser = new NeededPowerSpecParser();
        Optional<NeededPowerSpec> result = neededPowerSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals(150, result.get().getNeededPower());
    }

    @Test
    public void shouldReturnEmptyWhenNeededPowerIsMissing() throws Exception {
        String xml = """
                    <vehicle>
                    <storeData>
                        <specs>
                        </specs>
                    </storeData>
                    </vehicle>
                    """;

        Document document = XmlHelper.loadXmlFromString(xml);

        NeededPowerSpecParser neededPowerSpecParser = new NeededPowerSpecParser();
        Optional<NeededPowerSpec> result = neededPowerSpecParser.parse(document);

        assertTrue(result.isEmpty());
    }

}
