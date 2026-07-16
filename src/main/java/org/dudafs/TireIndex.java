package org.dudafs;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TireIndex {
    private static final Map<String, TireInfo> index = new HashMap<>();

    public static void buildIndex(File tiresRootFolder) throws IOException {
        try(Stream<Path> paths = Files.walk(tiresRootFolder.toPath())) {
            paths.filter(path -> path.toString().endsWith(".xml")).forEach(xmlPath -> {
                try {
                    indexTireFile(xmlPath);
                } catch (IOException | ParserConfigurationException | SAXException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private static void indexTireFile(Path xmlPath) throws IOException, ParserConfigurationException, SAXException {
        try {
            String content = Files.readString(xmlPath);
            content = XmlHelper.sanitizeBom(content);
            content = XmlHelper.fixUnescapedAmpersands(content);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(content)));

            Element metadata = (Element) document.getElementsByTagName("metadata").item(0);


            if (metadata == null) {
                return;
            }

            String brand = metadata.getAttribute("brand");
            String category = metadata.getAttribute("category");
            String dimensions = xmlPath.getFileName().toString().replace(".xml", "");
            Element physics = (Element) document.getElementsByTagName("physics").item(0);
            double mass = 0;
            mass += 1000 * Double.parseDouble(physics.getAttribute("mass"));

            TireInfo tire = new TireInfo();
            tire.brand = brand;
            tire.category = category;
            tire.dimensions = dimensions;
            tire.mass = mass;

            index.put(buildKey(dimensions, brand, category), tire);
        } catch (Exception e) {
            System.out.println("Failed to index tire: " + xmlPath + e.getMessage());
        }
    }

    public static TireInfo find(String dimensions, String brand, String category){
        return index.get(buildKey(dimensions, brand, category));
    }

    private static String buildKey(String dimensions, String brand, String category) {
        return dimensions + "|" + brand + "|" + category;
    }
}
