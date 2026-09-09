package org.dudafs.parser;

import org.dudafs.model.specs.WorkingWidthSpec;
import org.dudafs.xml.XmlHelper;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkingWidthSpecParserTest{

    @Test
    public void shouldReturnWorkingWidthIfPresent() throws Exception {
        String xml = """
                    <vehicle>
                    <storeData>
                        <specs>
                            <workingWidth>12</workingWidth>
                        </specs>
                    </storeData>
                    </vehicle>
                """;
        Document document = XmlHelper.loadXmlFromString(xml);
        WorkingWidthSpecParser workingWidthSpecParser = new WorkingWidthSpecParser();
        Optional<WorkingWidthSpec> result = workingWidthSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals(12, result.get().getWorkingWidth());
    }

    @Test
    public void shouldReturnEmptyWorkingWidthIfMissing() throws Exception {
        String xml = """
                    <vehicle>
                    <storeData>
                        <specs>
                        </specs>
                    </storeData>
                    </vehicle>
                """;
        Document document = XmlHelper.loadXmlFromString(xml);
        WorkingWidthSpecParser workingWidthSpecParser = new WorkingWidthSpecParser();
        Optional<WorkingWidthSpec> result = workingWidthSpecParser.parse(document);

        assertTrue(result.isEmpty());
    }
}
