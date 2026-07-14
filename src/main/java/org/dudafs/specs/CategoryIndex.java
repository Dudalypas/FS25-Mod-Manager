package org.dudafs.specs;

import org.dudafs.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.*;

public class CategoryIndex {
    private static Map<String, Set<String>> categoryToValues = new HashMap<>();

    public void buildIndex(File defaultFile, String categoryTagName) throws Exception {
        if(defaultFile != null && defaultFile.exists()) {
            loadInto(defaultFile, categoryTagName);
        }
    }

    private void loadInto(File file, String categoryTagName) throws Exception {
        Document document = XmlHelper.loadXml(file.getAbsolutePath());
        if(document == null) return;
        NodeList categories = document.getElementsByTagName(categoryTagName);
        for(int i = 0; i < categories.getLength(); i++) {
            Element category = (Element) categories.item(i);
            String name = category.getAttribute("name");
            String[] values = category.getTextContent().trim().split("\\s+");
            categoryToValues.computeIfAbsent(name, k -> new LinkedHashSet<>()).addAll(Arrays.asList(values));
        }
    }

    public Set<String> find (String categoryName) {
        return categoryToValues.getOrDefault(categoryName, Collections.emptySet());
    }
}
