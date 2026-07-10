# Farming Simulator 25 Mod Manager

## Description

A Java project built to sharpen my OOP fundamentals and hands-on coding after relying heavily on AI-assisted tools. Good old geeksforgeeks and w3schools as well as stack overflow comes in handy.

The tool scans a local FS25 mods folder, parses mod metadata and store item specs directly from the packaged .zip files (no need to launch the game), and will let you browse, filter, and export equipment data.

## Milestones

### 1. Console MVP (in progress)
- Scan a mods folder and list all installed mod packages (name, author, version)
- Parse store item data (brand, model, category, power, working specs, capacity, etc.) directly from each mod's XML
- Handle real-world XML quirks (unescaped characters, BOM) from inconsistently-authored mod files, as not everything is from ModHub usually
- Persist mods folder path between runs
- Filter equipment by category
- Export data to .csv / .xml

### 2. Desktop GUI (planned)
- JavaFX interface for browsing and filtering mods/equipment visually
- Replace console menu with a proper UI

### 3. Stretch goal (exploring)
- Possible Spring Boot web version, containerized with Docker (learning it too)

## Tech Stack
- Java
- DOM XML parsing (javax.xml.parsers)
- java.util.zip for reading mod archives directly
- Swing (JFileChooser) — moving to JavaFX bit by bit