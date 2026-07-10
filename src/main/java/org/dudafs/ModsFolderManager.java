package org.dudafs;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ModsFolderManager {
    public static File getModsFolder(Properties config, File configFile) throws IOException {
        if (config.getProperty("Path") != null) {
            return new File(config.getProperty("Path"));
        }

        return promptForFolder(config, configFile);
    }

    public static File changeModsFolder(Properties config, File configFile) throws IOException {
        return promptForFolder(config, configFile);
    }

    private static File promptForFolder(Properties config, File configFile) throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int fileResult = fileChooser.showOpenDialog(null);

        if (fileResult != JFileChooser.APPROVE_OPTION) {
            System.out.println("No folder selected.");
        }
        File modsFolder = fileChooser.getSelectedFile();

        config.setProperty("Path", modsFolder.getAbsolutePath());
        try(FileWriter writer = new FileWriter(configFile)) {
            config.store(writer, "Modified path");
        }

        return modsFolder;
    }
}
