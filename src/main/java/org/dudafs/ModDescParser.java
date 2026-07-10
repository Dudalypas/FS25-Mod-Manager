package org.dudafs;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;


public class ModDescParser {
    public ModInfo parseModDesc(ZipFile zipFile) throws ParserConfigurationException, IOException, SAXException {
        Document document = XmlHelper.loadXmlFromZip(zipFile, "modDesc.xml");
        String fileName = new File(zipFile.getName()).getName();
        assert document != null;
        Element title = (Element) document.getElementsByTagName("title").item(0);
        Element name = (Element) title.getElementsByTagName("en").item(0);

        Node authorName = document.getElementsByTagName("author").item(0);
        Node version = document.getElementsByTagName("version").item(0);

        List<String> storeItemPaths = new ArrayList<String>();
        NodeList storeItems = document.getElementsByTagName("storeItem");
        for (int i = 0; i < storeItems.getLength(); i++) {
            Element storeItem = (Element) storeItems.item(i);

            if (storeItem.hasAttribute("xmlFilename")) {
                storeItemPaths.add(storeItem.getAttribute("xmlFilename"));
            }
        }

        return new ModInfo(fileName, name.getTextContent(), authorName.getTextContent(), version.getTextContent(), storeItemPaths);
    }
}
