package org.dudafs.parser;

import org.dudafs.model.specs.BaleSpec;
import org.dudafs.xml.XmlHelper;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaleSpecParserTest {

    @Test
    public void shouldReturnBaleTypeSquare() throws Exception {
        String xml = """
                <baleTypes>
                    <baleType isRoundBale="false" width="1.2" height="0.9" length="1.8" consumableUsage="0.0150">
                    </baleType>
                    <baleType isRoundBale="false" width="1.2" height="0.9" length="2.2" consumableUsage="0.0185" isDefault="true">
                    </baleType>
                    <baleType isRoundBale="false" width="1.2" height="0.9" length="2.4" consumableUsage="0.0200">
                    </baleType>
                </baleTypes>
                """;

        Document document = XmlHelper.loadXmlFromString(xml);

        BaleSpecParser baleSpecParser = new BaleSpecParser();
        Optional<BaleSpec> result = baleSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals("Square", result.get().getbaleType());
    }

    @Test
    public void shouldReturnBaleTypeRound() throws Exception {
        String xml = """
                   <baleTypes>
                        <baleType isRoundBale="true" width="1.2" diameter="1.25">
                        </baleType>
                        <baleType isRoundBale="true" width="1.2" diameter="1.50">
                        </baleType>
                    </baleTypes>
                   """;

        Document document = XmlHelper.loadXmlFromString(xml);

        BaleSpecParser baleSpecParser = new BaleSpecParser();
        Optional<BaleSpec> result = baleSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals("Round", result.get().getbaleType());
    }

    @Test
    public void shouldReturnEmptyWhenTheresNoBaleType() throws Exception {
        String xml = """
                   <baleTypes>
                    </baleTypes>
                   """;

        Document document = XmlHelper.loadXmlFromString(xml);

        BaleSpecParser baleSpecParser = new BaleSpecParser();
        Optional<BaleSpec> result = baleSpecParser.parse(document);

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnSquareBaleSizeWhenTheresOnlyOneValue() throws Exception {
        String xml = """
        <baleTypes>
            <baleType isRoundBale="false" width="0.45" height="0.35" length="1.2" consumableUsage="0.01">
            </baleType>
        </baleTypes>
        """;

        Document document = XmlHelper.loadXmlFromString(xml);

        BaleSpecParser baleSpecParser = new BaleSpecParser();
        Optional<BaleSpec> result = baleSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals(result.get().getMaxBaleLength(), result.get().getMinBaleLength());
        assertEquals(120, result.get().getMinBaleLength());
    }

    @Test
    public void shouldReturnSquareBaleSizeWhenTheresSeveralValues() throws Exception {
        String xml = """
        <baleTypes>
            <baleType isRoundBale="false" width="1.2" height="0.9" length="1.8" consumableUsage="0.0150">
            </baleType>
            <baleType isRoundBale="false" width="1.2" height="0.9" length="2.2" consumableUsage="0.0185" isDefault="true">
            </baleType>
            <baleType isRoundBale="false" width="1.2" height="0.9" length="2.4" consumableUsage="0.0200">
            </baleType>
        </baleTypes>
        """;

        Document document = XmlHelper.loadXmlFromString(xml);

        BaleSpecParser baleSpecParser = new BaleSpecParser();
        Optional<BaleSpec> result = baleSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals(180, result.get().getMinBaleLength());
        assertEquals(240, result.get().getMaxBaleLength());
    }

    @Test
    public void shouldReturnRoundBaleSizeWhenTheresOnlyOneValue() throws Exception {
        String xml = """
        <baleTypes>
            <baleType isRoundBale="true" width="1.2" diameter="1.25">
            </baleType>
        </baleTypes>
        """;

        Document document = XmlHelper.loadXmlFromString(xml);

        BaleSpecParser baleSpecParser = new BaleSpecParser();
        Optional<BaleSpec> result = baleSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals(result.get().getMaxBaleDiameter(), result.get().getMinBaleDiameter());
        assertEquals(125, result.get().getMinBaleDiameter());
    }

    @Test
    public void shouldReturnRoundBaleSizeWhenTheresSeveralValues() throws Exception {
        String xml = """
        <baleTypes>
            <baleType isRoundBale="true" width="1.2" diameter="1.25">
            </baleType>
            <baleType isRoundBale="true" width="1.2" diameter="1.40">
            </baleType>
            <baleType isRoundBale="true" width="1.2" diameter="1.80">
            </baleType>
        </baleTypes>
        """;

        Document document = XmlHelper.loadXmlFromString(xml);

        BaleSpecParser baleSpecParser = new BaleSpecParser();
        Optional<BaleSpec> result = baleSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals(125, result.get().getMinBaleDiameter());
        assertEquals(180, result.get().getMaxBaleDiameter());
    }

    //@Test
    public void shouldReturnWrapper() throws Exception {
        String xml = """
    <vehicle>
        <baleTypes>
            <baleType isRoundBale="true" width="1.2" diameter="1.25">
                <nodes baleNode="baleNode125"/>
                <animations fillAnimation="baleFillAnimation125" unloadAnimation="baleDropAnimation125" dropAnimationTime="6"/>
            </baleType>
        </baleTypes>
        <baleWrapper>
                <baleTypes>
                    <baleType width="1.2" diameter="1.25" wrapUsage="0.1">
                    </baleType>
                </baleTypes>
        </baleWrapper>
    </vehicle>
    """;

        Document document = XmlHelper.loadXmlFromString(xml);

        BaleSpecParser baleSpecParser = new BaleSpecParser();
        Optional<BaleSpec> result = baleSpecParser.parse(document);

        assertTrue(result.isEmpty());
    }


}
