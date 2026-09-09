package org.dudafs.parser;

import org.dudafs.model.ModInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModDescParserTest {
    private Path zipPath;
    ModDescParser modDescParser;

    @BeforeEach
    void setUp() throws URISyntaxException {
        URL resource = getClass().getClassLoader().getResource("testFolder/test.zip");
        if(resource == null) {
            throw new IllegalStateException("test.zip not found");
        }
        zipPath = Paths.get(resource.toURI());

        modDescParser = new ModDescParser();
    }

    @Test
    public void shouldReturnFilename() throws Exception {

        try (ZipFile zip = new ZipFile(String.valueOf(zipPath))) {
            ModInfo result = modDescParser.parseModDesc(zip);

            assertEquals("test.zip", result.getFileName());
        }
    }

    @Test
    public void shouldReturnModName() throws Exception {

        try (ZipFile zip = new ZipFile(String.valueOf(zipPath))) {
            ModInfo result = modDescParser.parseModDesc(zip);

            assertEquals("Volkswagen Passat", result.getModName());
        }
    }

    @Test
    public void shouldReturnAuthorName() throws Exception {

        try (ZipFile zip = new ZipFile(String.valueOf(zipPath))) {
            ModInfo result = modDescParser.parseModDesc(zip);

            assertEquals("DSTX", result.getAuthorName());
        }
    }

    @Test
    public void shouldReturnModVersion() throws Exception {

        try (ZipFile zip = new ZipFile(String.valueOf(zipPath))) {
            ModInfo result = modDescParser.parseModDesc(zip);

            assertEquals("1.1.0.0", result.getModVersion());
        }
    }

    @Test
    public void shouldReturnStoreItemPaths() throws Exception {

        try (ZipFile zip = new ZipFile(String.valueOf(zipPath))) {
            ModInfo result = modDescParser.parseModDesc(zip);

            assertEquals("[Passat.xml]", result.getStoreItemPaths().toString());
        }
    }

}
