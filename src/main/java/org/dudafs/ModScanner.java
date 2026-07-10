package org.dudafs;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.zip.ZipFile;

public class ModScanner {
    public static void forEachModZip(File modsFolder, Consumer<ZipFile> action) {
        File[] zipFiles = modsFolder.listFiles((dir, name) -> name.endsWith(".zip"));
        if (zipFiles == null || zipFiles.length == 0) {
            System.out.println("No files found.");
            return;
        }

        for (File zipFile : zipFiles) {
            try(ZipFile zip = new ZipFile(zipFile)) {
                action.accept(zip);
            }
            catch (IOException e) {
                System.out.println("Error reading zip file: " + zipFile.getName());
            }
        }
    }
}
