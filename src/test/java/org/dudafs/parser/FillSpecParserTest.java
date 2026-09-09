package org.dudafs.parser;

import org.dudafs.model.specs.FillSpec;
import org.dudafs.xml.XmlHelper;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


public class FillSpecParserTest {

    @Test
    public void shouldReturnFillTypeIfPresent() throws Exception {
        String xml = """
            <fillUnit>
                <fillUnitConfigurations>
                    <fillUnitConfiguration>
                        <fillUnits>
                             <fillUnit shopDisplayUnit="CUBICMETER" fillTypeCategories="BULK" capacity="47000" allowAILoading="true">
                                <exactFillRootNode node="exactFillRootNode" />
                                <autoAimTargetNode node="fillAutoAimTarget" startZ="2.6" endZ="-4.2" invert="false" fillUnitIndex="1" startPercentage="25" />
                            </fillUnit>
                        </fillUnits>
                    </fillUnitConfiguration>
                </fillUnitConfigurations>
            </fillUnit>
            """;

        Document document = XmlHelper.loadXmlFromString(xml);

        FillSpecParser fillSpecParser = new FillSpecParser();
        Optional<FillSpec> result = fillSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals("bulk", result.get().getFillTypes());
    }

    @Test
    public void shouldReturnFillOneCapacityValueWhenTheresOnlyOneValue() throws Exception {
        String xml = """
            <fillUnit>
                <fillUnitConfigurations>
                    <fillUnitConfiguration>
                        <fillUnits>
                             <fillUnit shopDisplayUnit="CUBICMETER" fillTypeCategories="BULK" capacity="47000" allowAILoading="true">
                                <exactFillRootNode node="exactFillRootNode" />
                                <autoAimTargetNode node="fillAutoAimTarget" startZ="2.6" endZ="-4.2" invert="false" fillUnitIndex="1" startPercentage="25" />
                            </fillUnit>
                        </fillUnits>
                    </fillUnitConfiguration>
                </fillUnitConfigurations>
            </fillUnit>
            """;

        Document document = XmlHelper.loadXmlFromString(xml);

        FillSpecParser fillSpecParser = new FillSpecParser();
        Optional<FillSpec> result = fillSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals(47000, result.get().getMaxCapacity());
        assertEquals(result.get().getMinCapacity(), result.get().getMaxCapacity());
    }

    @Test
    public void shouldReturnMinCapacityWhenTheresTwoValues() throws Exception {
        String xml = """
               <fillUnit>
                <fillUnitConfigurations>
                    <fillUnitConfiguration price="4000">
                        <fillUnits>
                            <fillUnit shopDisplayUnit="CUBICMETER" fillTypeCategories="BULK" capacity="22500" allowAILoading="true">
                            </fillUnit>
                        </fillUnits>
                    </fillUnitConfiguration>
                    <fillUnitConfiguration price="8000">
                        <fillUnits>
                            <fillUnit shopDisplayUnit="CUBICMETER" fillTypeCategories="BULK" capacity="34000" allowAILoading="true">
                            </fillUnit>
                        </fillUnits>
                    </fillUnitConfiguration>
                </fillUnitConfigurations>
            </fillUnit>
            """;

        Document document = XmlHelper.loadXmlFromString(xml);

        FillSpecParser fillSpecParser = new FillSpecParser();
        Optional<FillSpec> result = fillSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertFalse(result.get().getMinCapacity() == result.get().getMaxCapacity());
        assertEquals(22500, result.get().getMinCapacity());
    }

    @Test
    public void shouldReturnMaxCapacityWhenTheresTwoValues() throws Exception {
        String xml = """
               <fillUnit>
                <fillUnitConfigurations>
                    <fillUnitConfiguration price="4000">
                        <fillUnits>
                            <fillUnit shopDisplayUnit="CUBICMETER" fillTypeCategories="BULK" capacity="22500" allowAILoading="true">
                            </fillUnit>
                        </fillUnits>
                    </fillUnitConfiguration>
                    <fillUnitConfiguration price="8000">
                        <fillUnits>
                            <fillUnit shopDisplayUnit="CUBICMETER" fillTypeCategories="BULK" capacity="34000" allowAILoading="true">
                            </fillUnit>
                        </fillUnits>
                    </fillUnitConfiguration>
                </fillUnitConfigurations>
            </fillUnit>
            """;

        Document document = XmlHelper.loadXmlFromString(xml);

        FillSpecParser fillSpecParser = new FillSpecParser();
        Optional<FillSpec> result = fillSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertFalse(result.get().getMinCapacity() == result.get().getMaxCapacity());
        assertEquals(34000, result.get().getMaxCapacity());
    }

    @Test
    public void shouldReturnDisplayUnitAsCubicMeters() throws Exception {
        String xml = """
               <fillUnit>
                <fillUnitConfigurations>
                    <fillUnitConfiguration price="4000">
                        <fillUnits>
                            <fillUnit shopDisplayUnit="CUBICMETER" fillTypeCategories="BULK" capacity="22500" allowAILoading="true">
                            </fillUnit>
                        </fillUnits>
                    </fillUnitConfiguration>
                    <fillUnitConfiguration price="8000">
                        <fillUnits>
                            <fillUnit shopDisplayUnit="CUBICMETER" fillTypeCategories="BULK" capacity="34000" allowAILoading="true">
                            </fillUnit>
                        </fillUnits>
                    </fillUnitConfiguration>
                </fillUnitConfigurations>
            </fillUnit>
            """;

        Document document = XmlHelper.loadXmlFromString(xml);

        FillSpecParser fillSpecParser = new FillSpecParser();
        Optional<FillSpec> result = fillSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals("m³", result.get().getDisplayUnit());
    }

    @Test
    public void shouldReturnBaleAsDisplayUnitWhenCapacityIsEqualToOne() throws Exception {
        String xml = """
        <fillUnit>
        <fillUnitConfigurations>
            <fillUnitConfiguration>
                <fillUnits>
                    <fillUnit unitTextOverride="$l10n_unit_bale" fillTypes="SQUAREBALE" capacity="1"/>
                </fillUnits>
            </fillUnitConfiguration>
        </fillUnitConfigurations>
    </fillUnit>
    """;

        Document document = XmlHelper.loadXmlFromString(xml);

        FillSpecParser fillSpecParser = new FillSpecParser();
        Optional<FillSpec> result = fillSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals("bale", result.get().getDisplayUnit());
    }

    @Test
    public void shouldReturnBalesAsDisplayUnitWhenCapacityIsMoreThanOne() throws Exception {
        String xml = """
        <fillUnit>
        <fillUnitConfigurations>
            <fillUnitConfiguration>
                <fillUnits>
                    <fillUnit unitTextOverride="$l10n_unit_bale" fillTypes="ROUNDBALE" capacity="12"/>
                </fillUnits>
            </fillUnitConfiguration>
        </fillUnitConfigurations>
    </fillUnit>
    """;

        Document document = XmlHelper.loadXmlFromString(xml);

        FillSpecParser fillSpecParser = new FillSpecParser();
        Optional<FillSpec> result = fillSpecParser.parse(document);

        assertTrue(result.isPresent());
        assertEquals("bales", result.get().getDisplayUnit());
    }
}
