package org.dudafs.parser;

import org.dudafs.index.TireIndex;
import org.dudafs.model.specs.WeightSpec;
import org.dudafs.xml.XmlHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.zip.ZipFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeightSpecParserTest {

    private Path zipPath;
    private File gameFolder;
    private WeightSpecParser weightSpecParser;

    @BeforeEach
    void setUp() throws URISyntaxException, IOException {
        URL resource = getClass().getClassLoader().getResource("testFolder");
        if(resource == null) {
            throw new IllegalStateException("testFolder not found");
        }
        Path testFolder = Paths.get(resource.toURI());
        zipPath = testFolder.resolve("test.zip");
        gameFolder = new File(String.valueOf(testFolder.toFile()));

        weightSpecParser = new WeightSpecParser();

        TireIndex.buildIndex(new File(gameFolder + "/data/shared/wheels/tires"));
    }

    @Test
    void shouldReturnMassWhenWheelMassIsLocal() throws IOException, ParserConfigurationException, SAXException {
        String xml = """
            <vehicle>
            <annotation>Copyright (C) GIANTS Software GmbH, All Rights Reserved.</annotation>
            <storeData>
            </storeData>
            <base>
                <components>
                    <component centerOfMass="0 1.05 -2.28" solverIterationCount="25" mass="4500"/>
                    <component centerOfMass="0 0 0" solverIterationCount="15" mass="4500"/>
                    <joint component1="1" component2="2" node="frontAxisJoint" />
                </components>
            </base>
            <fillUnit>
                 <fillUnitConfigurations>
                     <fillUnitConfiguration>
                         <fillUnits>
                             <fillUnit fillTypes="diesel" capacity="500"/>
                             <fillUnit fillTypes="def" capacity="30"/>
                             <fillUnit fillTypes="air" capacity="40"/>
                         </fillUnits>
                     </fillUnitConfiguration>
                 </fillUnitConfigurations>
            </fillUnit>
            <wheels>
                <wheelConfigurations>
                    <wheelConfiguration name="Default" price="2500">
                        <wheels autoRotateBackSpeed="6.0" speedDependentRotateBack="false">
                            <wheel isCareWheel="true" >
                                <physics mass="0.7"/>
                            </wheel>
                            <wheel isCareWheel="true">
                                <physics mass="0.7"/>
                            </wheel>
                            <wheel isCareWheel="true" >
                                <physics mass="0.7"/>
                            </wheel>
                            <wheel isCareWheel="true" >
                                <physics mass="0.7"/>
                            </wheel>
                        </wheels>
                    </wheelConfiguration>
                </wheelConfigurations>
            </wheels>
            </vehicle>
        """;

        Document document = XmlHelper.loadXmlFromString(xml);

        if(document == null) {
            throw new IllegalStateException("Test xml not found");
        }

        try (ZipFile zip = new ZipFile(zipPath.toFile())) {
            Optional<WeightSpec> result = weightSpecParser.parse(document, gameFolder, zip);

            assertTrue(result.isPresent());

            // Component Mass | 4500 + 4500 = 9000
            // Diesel Mass | 500 * 0.83 = 415
            // DEF Mass 30 * 1.09 = 32.7
            // Air Mass | 40 * 0 = 0
            // Wheel Mass | (0.7 * 1000) * 4 = 2800
            // Total = 12247.7
            // (int) Math.round(12247.7) = 12248

            assertEquals(12248, result.get().getWeight());
        }
    }

    @Test
    void shouldReturnMassWhenWheelMassIsDefinedInLocalFolder() throws IOException, ParserConfigurationException, SAXException {
        String xml = """
            <vehicle>
            <annotation>Copyright (C) GIANTS Software GmbH, All Rights Reserved.</annotation>
            <storeData>
            </storeData>
            <base>
                <components>
                    <component centerOfMass="0 1.05 -2.28" solverIterationCount="25" mass="4500"/>
                    <component centerOfMass="0 0 0" solverIterationCount="15" mass="4500"/>
                    <joint component1="1" component2="2" node="frontAxisJoint" rotLimit="0 0 8" rotLimitSpring="0 0 488" rotLimitDamping="0 0 15" transLimit="0 0.005 0" transLimitSpring="0 650 0" transLimitDamping="0 35 0" breakable="false"/>
                </components>
            </base>
            <fillUnit>
                 <fillUnitConfigurations>
                     <fillUnitConfiguration>
                         <fillUnits>
                             <fillUnit fillTypes="diesel" capacity="500"/>
                             <fillUnit fillTypes="def" capacity="30"/>
                             <fillUnit fillTypes="air" capacity="40"/>
                         </fillUnits>
                     </fillUnitConfiguration>
                 </fillUnitConfigurations>
            </fillUnit>
            <wheels>
            <wheelConfigurations>
                <wheelConfiguration name="$l10n_configuration_default" price="3500" brand="TRELLEBORG" saveId="TRELLEBORG_BROAD">
                    <wheels baseConfig="default">
                        <wheel filename="Wheels/TM3000/710_60R30.xml" configId="jd" >
                            <physics restLoad="2.5" yOffset="0.03"/>
                        </wheel>
                        <wheel filename="Wheels/TM3000/710_60R30.xml" configId="jd" >
                            <physics restLoad="2.5" yOffset="0.03"/>
                        </wheel>
                        <wheel filename="Wheels/TM3000/800_70R38.xml" configId="jd_inner" >
                            <physics restLoad="2.5" yOffset="0.03"/>
                        </wheel>
                        <wheel filename="Wheels/TM3000/800_70R38.xml" configId="jd_inner" >
                            <physics restLoad="2.5" yOffset="0.03"/>
                        </wheel>
                    </wheels>
                    <foliageBendingModifier index="2" minX="-1.35" maxX="1.35"/>
                </wheelConfiguration>
            </wheelConfigurations>
            </wheels>
            </vehicle>
            """;

        Document document = XmlHelper.loadXmlFromString(xml);
        if(document == null) {
            throw new IllegalStateException("Test xml not found");
        }
        try (ZipFile zip = new ZipFile(zipPath.toFile())) {
            Optional<WeightSpec> result = weightSpecParser.parse(document, gameFolder, zip);

            assertTrue(result.isPresent());

            // Component Mass | 4500 + 4500 = 9000
            // Diesel Mass | 500 * 0.83 = 415
            // DEF Mass 30 * 1.09 = 32.7
            // Air Mass | 40 * 0 = 0
            // Wheel Mass | ((0.449 * 1000) * 2) + ((0.294 * 1000) * 2) = 1486
            // Total = 10933.7
            // (int) Math.round(10933.7) = 10934

            assertEquals(10934, result.get().getWeight());
        }
    }

    @Test
    void shouldReturnMassWhenWheelMassIsDefinedInGameFolder() throws IOException, ParserConfigurationException, SAXException {
        String xml = """
            <vehicle>
            <annotation>Copyright (C) GIANTS Software GmbH, All Rights Reserved.</annotation>
            <storeData>
            </storeData>
            <base>
                <components>
                    <component centerOfMass="0 1.05 -2.28" solverIterationCount="25" mass="4500"/>
                    <component centerOfMass="0 0 0" solverIterationCount="15" mass="4500"/>
                    <joint component1="1" component2="2" node="frontAxisJoint" rotLimit="0 0 8" rotLimitSpring="0 0 488" rotLimitDamping="0 0 15" transLimit="0 0.005 0" transLimitSpring="0 650 0" transLimitDamping="0 35 0" breakable="false"/>
                </components>
            </base>
            <fillUnit>
                 <fillUnitConfigurations>
                     <fillUnitConfiguration>
                         <fillUnits>
                             <fillUnit fillTypes="diesel" capacity="500"/>
                             <fillUnit fillTypes="def" capacity="30"/>
                             <fillUnit fillTypes="air" capacity="40"/>
                         </fillUnits>
                     </fillUnitConfiguration>
                 </fillUnitConfigurations>
            </fillUnit>
            <wheels>
            <wheelConfigurations>
                <wheelConfiguration name="$l10n_configuration_default" price="3500" brand="TRELLEBORG" saveId="TRELLEBORG_BROAD">
                    <wheels baseConfig="default">
                    <wheel filename="$data/shared/wheels/tires/trelleborg/TM900/600_70R30.xml" configId="jd" isLeft="true" hasTireTracks="true" hasParticles="true">
                        <physics rotSpeed="1" restLoad="2.4" repr="axisFrontLeft"  driveNode="wheelFrontLeft"  forcePointRatio="0.5" initialCompression="40" suspTravel="0.22" spring="30" damper="40" yOffset="0.03"/>
                        <steering node="steeringBar" nodeMinTransX="0.06" nodeMaxTransX="-0.06"/>
                        <fender node="fenderFrontLeft" rotMax="22"/>
                    </wheel>
                    <wheel filename="$data/shared/wheels/tires/trelleborg/TM900/600_70R30.xml" configId="jd" isLeft="false" hasTireTracks="true" hasParticles="true">
                        <physics rotSpeed="1" restLoad="2.4" repr="axisFrontRight" driveNode="wheelFrontRight" forcePointRatio="0.5" initialCompression="40" suspTravel="0.22" spring="30" damper="40" yOffset="0.03"/>
                        <fender node="fenderFrontRight" rotMin="-22"/>
                    </wheel>
                    <wheel filename="$data/shared/wheels/tires/trelleborg/TM900/710_70R42.xml" configId="jd_inner" isLeft="true" hasTireTracks="true" hasParticles="true">
                        <physics rotSpeed="0" restLoad="2.3" repr="wheelBackLeft"  forcePointRatio="0.5" initialCompression="25" suspTravel="0.22" spring="37" damper="40" yOffset="0.03"/>
                    </wheel>
                    <wheel filename="$data/shared/wheels/tires/trelleborg/TM900/710_70R42.xml" configId="jd_inner" isLeft="false" hasTireTracks="true" hasParticles="true">
                        <physics rotSpeed="0" restLoad="2.3" repr="wheelBackRight" forcePointRatio="0.5" initialCompression="25" suspTravel="0.22" spring="37" damper="40" yOffset="0.03"/>
                    </wheel>
                </wheels>
                    <foliageBendingModifier index="2" minX="-1.35" maxX="1.35"/>
                </wheelConfiguration>
            </wheelConfigurations>
            </wheels>
            </vehicle>
            """;

        Document document = XmlHelper.loadXmlFromString(xml);
        if(document == null) {
            throw new IllegalStateException("Test xml not found");
        }
        try (ZipFile zip = new ZipFile(zipPath.toFile())) {
            Optional<WeightSpec> result = weightSpecParser.parse(document, gameFolder, zip);

            assertTrue(result.isPresent());

            // Component Mass | 4500 + 4500 = 9000
            // Diesel Mass | 500 * 0.83 = 415
            // DEF Mass 30 * 1.09 = 32.7
            // Air Mass | 40 * 0 = 0
            // Wheel Mass | ((0.267 * 1000) * 2) + ((0.446 * 1000) * 2) = 1426
            // Total = 10873.7
            // (int) Math.round(10873.7) = 10874

            assertEquals(10874, result.get().getWeight());
        }
    }

    @Test
    void shouldReturnMassWhenWheelPathIsNotDefined() throws IOException, ParserConfigurationException, SAXException {
        String xml = """
            <vehicle>
            <annotation>Copyright (C) GIANTS Software GmbH, All Rights Reserved.</annotation>
            <storeData>
            </storeData>
            <base>
                <components>
                    <component centerOfMass="0 1.05 -2.28" solverIterationCount="25" mass="4500"/>
                    <component centerOfMass="0 0 0" solverIterationCount="15" mass="4500"/>
                    <joint component1="1" component2="2" node="frontAxisJoint" rotLimit="0 0 8" rotLimitSpring="0 0 488" rotLimitDamping="0 0 15" transLimit="0 0.005 0" transLimitSpring="0 650 0" transLimitDamping="0 35 0" breakable="false"/>
                </components>
            </base>
            <fillUnit>
                 <fillUnitConfigurations>
                     <fillUnitConfiguration>
                         <fillUnits>
                             <fillUnit fillTypes="diesel" capacity="500"/>
                             <fillUnit fillTypes="def" capacity="30"/>
                             <fillUnit fillTypes="air" capacity="40"/>
                         </fillUnits>
                     </fillUnitConfiguration>
                 </fillUnitConfigurations>
            </fillUnit>
            <wheels>
            <wheelConfigurations tireCategories="TRACTOR" customBrandOrder="BKT">
                <wheelConfiguration name="$l10n_configuration_valueDefault" price="0" saveId="DEFAULT" numDynamicConfigurations="1">
                <wheels autoRotateBackSpeed="2">
                    <wheel dimensions="600_65R34" configId="jd" isLeft="true" hasTireTracks="true" hasParticles="true">
                        <physics rotSpeed="1" restLoad="2.41" repr="axisFrontLeft" driveNode="wheelFrontLeft" forcePointRatio="0.5" initialCompression="25" suspTravel="0.12" spring="73" damper="18" yOffset="0.04"/>
                <innerRim filename="$data/shared/wheels/rims/rim006.i3d" node="0|0"/>
                        <outerRim filename="Extras/RIM.i3d" node="0"/>
                        <fender node="fenderLeftEU" rotMax="17"/>
                    </wheel>
                    <wheel dimensions="600_65R34" configId="jd" isLeft="false" hasTireTracks="true" hasParticles="true">
                        <physics rotSpeed="1" restLoad="2.41" repr="axisFrontRight" driveNode="wheelFrontRight" forcePointRatio="0.5" initialCompression="25" suspTravel="0.12" spring="73" damper="18" yOffset="0.04"/>
                        <innerRim filename="$data/shared/wheels/rims/rim006.i3d" node="0|1" />
                        <outerRim filename="Extras/RIM.i3d" node="1"/>
                        <fender node="fenderRightEU" rotMin="-17"/>
                    </wheel>
                    <wheel dimensions="710_75R42" isLeft="true" hasTireTracks="true" hasParticles="true">
                        <physics rotSpeed="0" restLoad="3.48" repr="wheelBackLeft" forcePointRatio="0.5" initialCompression="14" suspTravel="0.12" spring="177" damper="28" yOffset="0.04"/>
                        <innerRim filename="$data/shared/wheels/rims/rim008.i3d" node="0|0"/>
                        <outerRim filename="Extras/RIM.i3d" node="0"/>
                    </wheel>
                    <wheel dimensions="710_75R42" isLeft="false" hasTireTracks="true" hasParticles="true">
                        <physics rotSpeed="0" restLoad="3.48" repr="wheelBackRight" forcePointRatio="0.5" initialCompression="14" suspTravel="0.12" spring="177" damper="28" yOffset="0.04"/>
                        <innerRim filename="$data/shared/wheels/rims/rim008.i3d" node="0|1" />
                        <outerRim filename="Extras/RIM.i3d" node="1"/>
                    </wheel>
                    </wheels>
                </wheelConfiguration>
            </wheelConfigurations>
            </wheels>
            </vehicle>
            """;

        Document document = XmlHelper.loadXmlFromString(xml);
        if(document == null) {
            throw new IllegalStateException("Test xml not found");
        }
        try (ZipFile zip = new ZipFile(zipPath.toFile())) {
            Optional<WeightSpec> result = weightSpecParser.parse(document, gameFolder, zip);

            assertTrue(result.isPresent());

            // Component Mass | 4500 + 4500 = 9000
            // Diesel Mass | 500 * 0.83 = 415
            // DEF Mass 30 * 1.09 = 32.7
            // Air Mass | 40 * 0 = 0
            // Wheel Mass | ((0.295 * 1000) * 2) + ((0.459 * 1000) * 2) = 1508
            // Total = 10955.7
            // (int) Math.round(10955.7) = 10956

            assertEquals(10956, result.get().getWeight());
        }
    }

    @Test
    void shouldReturnMassWhenWheelMassIsNotDefined() throws IOException, ParserConfigurationException, SAXException {
        String xml = """
            <vehicle>
            <annotation>Copyright (C) GIANTS Software GmbH, All Rights Reserved.</annotation>
            <storeData>
            </storeData>
            <base>
                <components>
                    <component centerOfMass="0 1.05 -2.28" solverIterationCount="25" mass="4500"/>
                    <component centerOfMass="0 0 0" solverIterationCount="15" mass="4500"/>
                    <joint component1="1" component2="2" node="frontAxisJoint" rotLimit="0 0 8" rotLimitSpring="0 0 488" rotLimitDamping="0 0 15" transLimit="0 0.005 0" transLimitSpring="0 650 0" transLimitDamping="0 35 0" breakable="false"/>
                </components>
            </base>
            <fillUnit>
                 <fillUnitConfigurations>
                     <fillUnitConfiguration>
                         <fillUnits>
                             <fillUnit fillTypes="diesel" capacity="500"/>
                             <fillUnit fillTypes="def" capacity="30"/>
                             <fillUnit fillTypes="air" capacity="40"/>
                         </fillUnits>
                     </fillUnitConfiguration>
                 </fillUnitConfigurations>
            </fillUnit>
            <wheels>
            <wheelConfigurations>
            <wheelConfiguration name="$l10n_configuration_valueDefault" price="0">
                <wheels autoRotateBackSpeed="2.5">
                    <wheel hasTireTracks="true" hasParticles="true" >
                        <physics rotSpeed="1" restLoad="0.5" repr="axisFrontLeft" driveNode="wheelFrontLeft" isleft="true" radius="0.35" width="0.27" forcePointRatio="0.5" initialCompression="25" suspTravel="0.09" spring="20" damper="5" frictionScale="2.5" />
                        <tire tireTrackAtlasIndex="1"/>
                    </wheel>
                    <wheel hasTireTracks="true" hasParticles="true" >
                        <physics rotSpeed="1" restLoad="0.5" repr="axisFrontRight" driveNode="wheelFrontRight" isleft="false" radius="0.35" width="0.27" forcePointRatio="0.5" initialCompression="25" suspTravel="0.09" spring="20" damper="5" frictionScale="2.5" />
                        <tire tireTrackAtlasIndex="1"/>
                    </wheel>
                    <wheel hasTireTracks="true" hasParticles="true" >
                        <physics rotSpeed="0" restLoad="0.7" repr="LRW" isleft="true" radius="0.35" width="0.27" forcePointRatio="0.5" initialCompression="25" suspTravel="0.09" spring="20" damper="5" frictionScale="2.5" />
                        <tire tireTrackAtlasIndex="1"/>
                    </wheel>
                    <wheel hasTireTracks="true" hasParticles="true" >
                        <physics rotSpeed="0" restLoad="0.7" repr="RRW" isleft="false" radius="0.35" width="0.27" forcePointRatio="0.5" initialCompression="25" suspTravel="0.09" spring="20" damper="5" frictionScale="2.5"  />
                        <tire tireTrackAtlasIndex="1"/>
                    </wheel>
                </wheels>
            </wheelConfiguration>
            </wheelConfigurations>
            </wheels>
            </vehicle>
            """;

        Document document = XmlHelper.loadXmlFromString(xml);
        if(document == null) {
            throw new IllegalStateException("Test xml not found");
        }
        try (ZipFile zip = new ZipFile(zipPath.toFile())) {
            Optional<WeightSpec> result = weightSpecParser.parse(document, gameFolder, zip);

            assertTrue(result.isPresent());

            // Component Mass | 4500 + 4500 = 9000
            // Diesel Mass | 500 * 0.83 = 415
            // DEF Mass 30 * 1.09 = 32.7
            // Air Mass | 40 * 0 = 0
            // Wheel Mass | 100 * 4 = 400
            // Total = 9847.7
            // (int) Math.round(9847.7) = 9848

            assertEquals(9848, result.get().getWeight());
        }
    }

    @Test
    void shouldReturnEmptyIfMassIsMissing() throws IOException, ParserConfigurationException, SAXException {
        String xml = """
            <vehicle>
            <annotation>Copyright (C) GIANTS Software GmbH, All Rights Reserved.</annotation>
            <storeData>
            </storeData>
            <base>
            </base>
            </vehicle>
            """;

        Document document = XmlHelper.loadXmlFromString(xml);
        if(document == null) {
            throw new IllegalStateException("Test xml not found");
        }
        try (ZipFile zip = new ZipFile(zipPath.toFile())) {
            Optional<WeightSpec> result = weightSpecParser.parse(document, gameFolder, zip);

            assertTrue(result.isEmpty());
        }
    }
}
