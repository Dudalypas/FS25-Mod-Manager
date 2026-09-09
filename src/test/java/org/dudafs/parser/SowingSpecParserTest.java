package org.dudafs.parser;

import org.dudafs.model.specs.SowingSpec;
import org.dudafs.xml.XmlHelper;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import java.util.Optional;

import static org.dudafs.model.specs.SowingSpec.SeedFruitSourceType.EXPLICIT_TYPES;
import static org.dudafs.model.specs.SowingSpec.SeedFruitSourceType.CATEGORY;
import static org.junit.jupiter.api.Assertions.*;

public class SowingSpecParserTest {

    @Test
    public void shouldReturnSowingCategoriesWhenPresent() throws Exception {
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
        assertEquals("sowingMachine", result.get().getSeedFruitValue());
    }

    @Test
    public void shouldReturnNoDirectPlantingIfDoesntHaveIt() throws Exception {
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
        assertFalse(result.get().isUseDirectPlanting());
    }

    @Test
    public void shouldReturnUsesDirectPlantingIfItUsesIt() throws Exception {
        String xml = """
            <vehicle>
            <sowingMachine fillUnitIndex="1">
                <seedFruitTypeCategories>sowingMachine</seedFruitTypeCategories>
                <useDirectPlanting value="true" />
            </sowingMachine>
            </vehicle>
            """;
        Document document = XmlHelper.loadXmlFromString(xml);
        SowingSpecParser sowingSpecParser = new SowingSpecParser();
        Optional<SowingSpec> result = sowingSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertTrue(result.get().isUseDirectPlanting());
    }

    @Test
    public void shouldReturnNoDirectPlantingIfTheresNoDirectPlantingTag() throws Exception {
        String xml = """
            <vehicle>
            <sowingMachine fillUnitIndex="1">
                <seedFruitTypeCategories>sowingMachine</seedFruitTypeCategories>
            </sowingMachine>
            </vehicle>
            """;
        Document document = XmlHelper.loadXmlFromString(xml);
        SowingSpecParser sowingSpecParser = new SowingSpecParser();
        Optional<SowingSpec> result = sowingSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertFalse(result.get().isUseDirectPlanting());
    }

    @Test
    public void shouldReturnEmptyIfTheresNoTagsInSowingMachine() throws Exception {
        String xml = """
            <vehicle>
            <sowingMachine>
            </sowingMachine>
            </vehicle>
            """;
        Document document = XmlHelper.loadXmlFromString(xml);
        SowingSpecParser sowingSpecParser = new SowingSpecParser();
        Optional<SowingSpec> result = sowingSpecParser.parse(document);

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnCustomFruitCategoryUnchanged() throws Exception {
        String xml = """
               <sowingMachine fillUnitIndex="1">
                    <seedFruitTypes>SPINACH PEA GREENBEAN PARSNIP CARROT BEETROOT</seedFruitTypes>
                    <useDirectPlanting value="false" />
               </sowingMachine>
               """;
        Document document = XmlHelper.loadXmlFromString(xml);
        SowingSpecParser sowingSpecParser = new SowingSpecParser();
        Optional<SowingSpec> result = sowingSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals("SPINACH PEA GREENBEAN PARSNIP CARROT BEETROOT", result.get().getSeedFruitValue());
    }

    @Test
    public void shouldReturnExplicitTypesAsSourceWhenTheresSeedFruitTypesTag() throws Exception {
        String xml = """
               <sowingMachine fillUnitIndex="1">
                    <seedFruitTypes>SPINACH PEA GREENBEAN PARSNIP CARROT BEETROOT</seedFruitTypes>
                    <useDirectPlanting value="false" />
               </sowingMachine>
               """;
        Document document = XmlHelper.loadXmlFromString(xml);
        SowingSpecParser sowingSpecParser = new SowingSpecParser();
        Optional<SowingSpec> result = sowingSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals(EXPLICIT_TYPES, result.get().getSeedFruitSourceType());
    }

    @Test
    public void shouldReturnCategoryAsSourceWhenTheresSeedFruitTypeCategoriesTag() throws Exception {
        String xml = """
               <sowingMachine fillUnitIndex="1">
                    <seedFruitTypeCategories>sowingMachine</seedFruitTypeCategories>
                    <useDirectPlanting value="false" />
               </sowingMachine>
               """;
        Document document = XmlHelper.loadXmlFromString(xml);
        SowingSpecParser sowingSpecParser = new SowingSpecParser();
        Optional<SowingSpec> result = sowingSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals(CATEGORY, result.get().getSeedFruitSourceType());
    }
}
