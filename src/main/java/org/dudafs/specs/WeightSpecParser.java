package org.dudafs.specs;

import org.dudafs.FolderManager;
import org.dudafs.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipFile;

import static org.dudafs.XmlHelper.loadXml;
import static org.dudafs.XmlHelper.loadXmlFromZip;

public class WeightSpecParser {
    private final TireIndex tireIndex;

    public WeightSpecParser(TireIndex tireIndex) {
        this.tireIndex = tireIndex;
    }

    public Optional<WeightSpec> parse(Document document, File gameFolder, ZipFile zipFile) throws IOException, ParserConfigurationException, SAXException {
        Map<String, Double> densityKgPerLiter = Map.of(
                "DIESEL", 0.83,
                "DEF", 1.09,
                "AIR", 0.0
        );

        Element rootComponents = (Element) document.getElementsByTagName("components").item(0);

        if(rootComponents == null) {
            return Optional.empty();
        }

        NodeList components = rootComponents.getElementsByTagName("component");
        double mass = 0;

        if(components.getLength() == 0) {
            return Optional.empty();
        }

        // Calculates item weight without liquids or tires
        for (int i = 0; i < components.getLength(); i++) {
            Element component = (Element) components.item(i);
            mass += Double.parseDouble(component.getAttribute("mass"));
        }

        Element fillUnits = (Element) document.getElementsByTagName("fillUnits").item(0);

        // Calculates item weight with liquids
        if(fillUnits != null) {
            NodeList fillUnitNodes = fillUnits.getElementsByTagName("fillUnit");
            for (int i = 0; i < fillUnitNodes.getLength(); i++) {
                Element fillUnit = (Element) fillUnitNodes.item(i);

                String fillType = fillUnit.getAttribute("fillTypes").trim().toUpperCase(Locale.ROOT);
                Double density = densityKgPerLiter.get(fillType);
                if (densityKgPerLiter.get(fillType) != null && fillUnit.hasAttribute("capacity")) {
                    mass += Double.parseDouble(fillUnit.getAttribute("capacity")) * density;
                }
            }
        }

        Element wheelConfiguration = (Element) document.getElementsByTagName("wheelConfiguration").item(0);

        // Calculates item weight with tires
        if(wheelConfiguration != null) {
            NodeList wheels = wheelConfiguration.getElementsByTagName("wheel");
            for (int i = 0; i < wheels.getLength(); i++) {
                Element wheel = (Element) wheels.item(i);
                Element physics = (Element) wheel.getElementsByTagName("physics").item(0);
                boolean hasLocalMass = physics != null && physics.hasAttribute("mass");
                String path = wheel.getAttribute("filename");
                String dimensions = wheel.getAttribute("dimensions");

                if (hasLocalMass) {
                    mass += 1000 * Double.parseDouble(physics.getAttribute("mass"));
                }
                else if(!path.isEmpty()) {

                    // File path corrections
                    String gameFolderPath = gameFolder.getCanonicalPath();
                    Document wheelDoc;

                    if (path.startsWith("$")) {
                        gameFolderPath = gameFolderPath.replace("\\", "/");
                        path = path.replace("$", gameFolderPath + "/");
                        wheelDoc = loadXml(path);
                    } else {
                        wheelDoc = loadXmlFromZip(zipFile, path);
                    }

                    // Wheel mass calculation
                    if(wheelDoc != null) {
                        physics = (Element) wheelDoc.getElementsByTagName("physics").item(0);
                        if (physics != null) {
                            mass += 1000 * Double.parseDouble(physics.getAttribute("mass"));
                        }
                    }
                }
                else if (!dimensions.isEmpty()) {
                    Element wheelConfigurations = (Element) document.getElementsByTagName("wheelConfigurations").item(0);
                    String brand = wheelConfigurations.getAttribute("customBrandOrder");
                    String category = wheelConfigurations.getAttribute("tireCategories");
                    String firstDimension = dimensions.split(" ")[0];
                    if(brand.isEmpty()) {
                        NodeList combinations = wheelConfigurations.getElementsByTagName("tireCombination");
                        if(combinations.getLength() > 0) {
                            Element combination = (Element) combinations.item(0);
                            brand = combination.getAttribute("brand");
                        }
                    }

                    if(!brand.isEmpty()) {

                        TireInfo tire = tireIndex.find(firstDimension, brand, category);
                        if (tire != null) {
                            mass += tire.mass;
                        }
                        else{
                            System.out.println("No tire match: " + firstDimension + " " + brand + " " + category);
                        }
                    }
                }
                else{
                    mass += 100; // Default value of 100 kg per wheel if no value provided
                }
            }
        }

        return Optional.of(new WeightSpec((int) Math.round(mass)));
    }
}
