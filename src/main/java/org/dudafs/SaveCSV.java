package org.dudafs;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SaveCSV {
    public static void save(List<StoreItem> items) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int fileResult = fileChooser.showOpenDialog(null);

        if (fileResult != JFileChooser.APPROVE_OPTION) {
            System.out.println("No folder selected.");
        }
        File folder = fileChooser.getSelectedFile();

        CsvExporter.export(items, folder.getAbsolutePath());

    }
}
