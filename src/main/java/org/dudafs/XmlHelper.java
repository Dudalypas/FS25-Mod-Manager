package org.dudafs;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class XmlHelper {
    public static String fixUnescapedAmpersands(String rawXml) {
        return rawXml.replaceAll("&(?!amp;|lt;|gt;|quot;|apos;|#\\d+;)", "&amp;");
    }
    public static String sanitizeBom(String rawXml){
        if (rawXml.startsWith("\uFEFF")) {
            rawXml = rawXml.substring(1);
        }
        return rawXml;
    }
    public static Document loadXmlFromZip(ZipFile zipFile, String entryName) throws IOException, ParserConfigurationException, SAXException {
        if(entryName.isEmpty()){
            System.out.println("EntryName is empty");
            return null;
        }
        if (zipFile.getEntry(entryName) == null) {
            System.out.println("Entry not found");
            return null;
        }
        ZipEntry entry = zipFile.getEntry(entryName);
        InputStream inputStream = zipFile.getInputStream(entry);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        String content = new String(inputStream.readAllBytes());
        content = content.replaceAll("(?s)<!--.*?-->", "");
        content = content.replaceAll(
                "(?<=[\"'])(?=[A-Za-z_:][A-Za-z0-9_.:-]*\\s*=)",
                " "
        );
        content = XmlHelper.sanitizeBom(content);
        content = XmlHelper.fixUnescapedAmpersands(content);

        return builder.parse(new InputSource(new StringReader(content)));
    }
}