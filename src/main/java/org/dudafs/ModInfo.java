package org.dudafs;

import java.util.ArrayList;
import java.util.List;

public class ModInfo {
    public String fileName;
    public String modName;
    public String authorName;
    public String modVersion;
    public List<String> storeItemPaths;

    public ModInfo(String fileName, String modName, String authorName, String modVersion, List<String> storeItemPaths) {
        this.fileName = fileName;
        this.modName = modName;
        this.authorName = authorName;
        this.modVersion = modVersion;
        this.storeItemPaths = storeItemPaths;
    }

    public String getFileName() {
        return fileName;
    }

    public String getModName() {
        return modName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getModVersion() {
        return modVersion;
    }

    public List<String> getStoreItemPaths() {
        return storeItemPaths;
    }
}