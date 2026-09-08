package org.dudafs.tests;

import org.dudafs.model.specs.NeededPowerSpec;
import org.dudafs.parser.NeededPowerSpecParser;
import org.dudafs.xml.XmlHelper;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NeededPowerSpecParserTest {

    @Test
    public void shouldParseNeededPower() throws Exception {
        String xml = """
                    <vehicle type="attachableFrontloader">
                    <annotation>Copyright (C) GIANTS Software GmbH, All Rights Reserved.</annotation>
                    <storeData>
                        <name>SomeFrontLoader</name>
                        <specs>
                            <neededPower>150</neededPower>
                            <combination filterCategory="frontLoaderTools" />
                        </specs>
                        <functions>
                            <function>$l10n_function_loader</function>
                        </functions>
                        <image>vehicles/mx/t410evo/store_loader.png</image>
                        <price>676767</price>
                        <lifetime>600</lifetime>
                        <rotation>0</rotation>
                        <brand>FrontLoader</brand>
                        <category>frontLoaders</category>
                        <vertexBufferMemoryUsage>142224</vertexBufferMemoryUsage>
                        <indexBufferMemoryUsage>369614</indexBufferMemoryUsage>
                        <textureMemoryUsage>3456800</textureMemoryUsage>
                        <audioMemoryUsage>0</audioMemoryUsage>
                        <instanceVertexBufferMemoryUsage>0</instanceVertexBufferMemoryUsage>
                        <instanceIndexBufferMemoryUsage>0</instanceIndexBufferMemoryUsage>
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
    public void shouldNotReturnNeededPowerIfEmpty() throws Exception {
        String xml = """
                    <vehicle type="Tractor">
                    <annotation>Copyright (C) GIANTS Software GmbH, All Rights Reserved.</annotation>
                    <storeData>
                        <name>7R</name>
                        <specs>
                            <power>305</power>
                            <maxSpeed>50</maxSpeed>
                        </specs>
                        <functions>
                            <function>$l10n_function_tractor</function>
                        </functions>
                        <image>vehicles/mx/t410evo/store_loader.png</image>
                        <price>290000</price>
                        <lifetime>600</lifetime>
                        <rotation>0</rotation>
                        <brand>JOHNDEERE</brand>
                        <category>tractorsL</category>
                        <vertexBufferMemoryUsage>142224</vertexBufferMemoryUsage>
                        <indexBufferMemoryUsage>369614</indexBufferMemoryUsage>
                        <textureMemoryUsage>3456800</textureMemoryUsage>
                        <audioMemoryUsage>0</audioMemoryUsage>
                        <instanceVertexBufferMemoryUsage>0</instanceVertexBufferMemoryUsage>
                        <instanceIndexBufferMemoryUsage>0</instanceIndexBufferMemoryUsage>
                    </storeData>
                    </vehicle>
                    """;

        Document document = XmlHelper.loadXmlFromString(xml);

        NeededPowerSpecParser neededPowerSpecParser = new NeededPowerSpecParser();
        Optional<NeededPowerSpec> result = neededPowerSpecParser.parse(document);

        assertTrue(result.isEmpty());
    }

}
