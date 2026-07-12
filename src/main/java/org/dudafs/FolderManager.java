package org.dudafs;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class FolderManager {
    public static File getFolder(Properties config, File configFile, String path) throws IOException {
        if (config.getProperty(path) != null) {
            return new File(config.getProperty(path));
        }

        return promptForFolder(config, configFile, path);
    }

    public static File changeFolder(Properties config, File configFile, String path) throws IOException {
        return promptForFolder(config, configFile, path);
    }

    private static File promptForFolder(Properties config, File configFile, String path) throws IOException {
        System.out.println("Please choose a " + path);
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int fileResult = fileChooser.showOpenDialog(null);

        if (fileResult != JFileChooser.APPROVE_OPTION) {
            System.out.println("No folder selected.");
        }
        File folder = fileChooser.getSelectedFile();

        config.setProperty(path, folder.getAbsolutePath());
        try(FileWriter writer = new FileWriter(configFile)) {
            config.store(writer, "Modified path");
        }

        return folder;
    }
}
