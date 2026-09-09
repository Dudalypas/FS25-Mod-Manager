package org.dudafs.parser;

import org.dudafs.model.specs.WorkingSpeedSpec;
import org.dudafs.xml.XmlHelper;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkingSpeedParserTest {
    @Test
    public void shouldReturnWorkingSpeedIfPresent() throws Exception {
        String xml = """
                    <vehicle>
                    <storeData>
                        <specs>
                        </specs>
                    </storeData>
                    <base>
                        <speedLimit value="15" />
                    </base>
                    </vehicle>
                """;
        Document document = XmlHelper.loadXmlFromString(xml);
        WorkingSpeedSpecParser workingSpeedSpecParser = new WorkingSpeedSpecParser();
        Optional<WorkingSpeedSpec> result = workingSpeedSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals(15, result.get().getWorkingSpeed());
    }

    @Test
    public void shouldReturnEmptyWorkingSpeedIfMissing() throws Exception {
        String xml = """
                    <vehicle>
                    <storeData>
                        <specs>
                        </specs>
                    </storeData>
                    </vehicle>
                """;
        Document document = XmlHelper.loadXmlFromString(xml);
        WorkingSpeedSpecParser workingSpeedSpecParser = new WorkingSpeedSpecParser();
        Optional<WorkingSpeedSpec> result = workingSpeedSpecParser.parse(document);

        assertTrue(result.isEmpty());
    }
}
