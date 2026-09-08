package org.dudafs.parser;

import org.dudafs.model.specs.SowingSpec;
import org.dudafs.xml.XmlHelper;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SowingSpecParser {
    @Test
    public void shouldReturnSowingCategoriesIfPresent() throws Exception {
        String xml = """
            <vehicle>
            <sowingMachine fillUnitIndex="1">
                <seedFruitTypeCategories>sowingMachine</seedFruitTypeCategories>
                <useDirectPlanting value="false" />
            </sowingMachine>
            </vehicle>
            """;
        Document document = XmlHelper.loadXmlFromString(xml);
        SowingSpecParser sowingSpecParser = new SowingSpecParser();
        Optional<SowingSpec> result = sowingSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals("sowingMachine", result.get().getWorkingSpeed());
    }
}
