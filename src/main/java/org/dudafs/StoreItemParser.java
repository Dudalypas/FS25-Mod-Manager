package org.dudafs;

import org.dudafs.specs.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.ZipFile;

public class StoreItemParser {
    public StoreItem parseStoreItem(ZipFile zipFile, String storeItemPath) throws IOException, ParserConfigurationException, SAXException {
        Document document = null;
        try {
            document = XmlHelper.loadXmlFromZip(zipFile, storeItemPath);
        }
        catch (SAXException e) {
            System.err.println("Error parsing: " + zipFile.getName() + ": " + storeItemPath);
            System.err.println("Reason: " + e.getMessage());
        }

        assert document != null;
        String brand = "NONE";
        if (document.getElementsByTagName("brand").item(0) != null) {
            brand = document.getElementsByTagName("brand").item(0).getTextContent();
        }

        String model = "NONE";
        Element name = null;
        if (document.getElementsByTagName("name").item(0) != null) {
            name = (Element) document.getElementsByTagName("name").item(0);
            model = name.getTextContent();
            if (name.getElementsByTagName("en").item(0) != null) {
                model = name.getElementsByTagName("en").item(0).getTextContent();
            }
        }

        String category = "NONE";
        if(document.getElementsByTagName("category").item(0) != null) {
            category = document.getElementsByTagName("category").item(0).getTextContent();
        }
        else {
            category = document.getElementsByTagName("species").item(0).getTextContent();
        }

        int price = 0;
        if (document.getElementsByTagName("price").getLength() != 0) {
            price = Integer.parseInt(document.getElementsByTagName("price").item(0).getTextContent());
        }

        StoreItem storeItem = new StoreItem(brand, model, category, price);

        // Motor Spec

        MotorSpecParser motorSpecParser = new MotorSpecParser();
        Optional<MotorSpec> parsedMotorSpec = motorSpecParser.parse(document);
        parsedMotorSpec.ifPresent(storeItem::addSpec);

        // Needed Power Spec

        NeededPowerSpecParser neededPowerSpecParser = new NeededPowerSpecParser();
        Optional<NeededPowerSpec> parsedNeededPowerSpec = neededPowerSpecParser.parse(document);
        parsedNeededPowerSpec.ifPresent(storeItem::addSpec);

        // Bale Spec

        BaleSpecParser baleSpecParser = new BaleSpecParser();
        Optional<BaleSpec> parsedBaleSpec = baleSpecParser.parse(document);
        parsedBaleSpec.ifPresent(storeItem::addSpec);

        // Working Width Spec

        WorkingWidthSpecParser workingWidthSpecParser = new WorkingWidthSpecParser();
        Optional<WorkingWidthSpec> parsedWorkingWidthSpec = workingWidthSpecParser.parse(document);
        parsedWorkingWidthSpec.ifPresent(storeItem::addSpec);

        // Working Speed Spec

        WorkingSpeedSpecParser workingSpeedSpecParser = new WorkingSpeedSpecParser();
        Optional<WorkingSpeedSpec> parsedWorkingSpeedSpec = workingSpeedSpecParser.parse(document);
        parsedWorkingSpeedSpec.ifPresent(storeItem::addSpec);

        // Sowing Spec

        SowingSpecParser sowingSpecParser = new SowingSpecParser();
        Optional<SowingSpec> parsedSowingSpec = sowingSpecParser.parseSowingSpec(document);
        parsedSowingSpec.ifPresent(storeItem::addSpec);

        // Fill Spec

        FillSpecParser fillSpecParser = new FillSpecParser();
        Optional<FillSpec> parsedFillSpec = fillSpecParser.parse(document);
        parsedFillSpec.ifPresent(storeItem::addSpec);

        return storeItem;
    }
}
