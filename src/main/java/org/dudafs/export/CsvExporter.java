package org.dudafs.export;

import org.dudafs.xml.XmlHelper;
import org.dudafs.model.StoreItem;
import org.dudafs.model.specs.ItemSpec;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CsvExporter {
    public static void export(List<StoreItem> items) throws IOException {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int fileResult = fileChooser.showOpenDialog(null);

        if (fileResult != JFileChooser.APPROVE_OPTION) {
            System.out.println("No folder selected.");
        }
        String outputPath = fileChooser.getSelectedFile().getAbsolutePath();

        LinkedHashSet<String> columns = new LinkedHashSet<>();
        List<Map<String, String>> rows = new ArrayList<>();

        for (StoreItem item : items) {
            Map<String, String> row = new LinkedHashMap<>();
            row.put("Brand", item.getBrand());
            row.put("Name", item.getName());
            for (ItemSpec spec : item.getSpecs()) {
                row.putAll(spec.toCsvFields());
            }
            columns.addAll(row.keySet());
            rows.add(row);
        }

        File outputFile = new File(outputPath, "export.csv");
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(String.join(",", columns) + "\n");

            for (Map<String, String> row : rows) {
                List<String> values = new ArrayList<>();
                for (String column : columns) {
                    values.add(XmlHelper.escape(row.getOrDefault(column, "")));
                }
                writer.write(String.join(",", values) + "\n");
            }
        }

    }
}