package org.dudafs;

import org.dudafs.specs.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

import static org.dudafs.CategoryIndex.find;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("FS25 Equipment Indexer");

        File configFile = new File("config.properties");
        Properties config = new Properties();
        try(FileInputStream fis = new FileInputStream(configFile)) {
            config.load(fis);
        }
        catch (FileNotFoundException e) {
            System.out.println("Config file not found");
        }

        File modsFolder = FolderManager.getFolder(config, configFile, "mods folder path");
        File gameFolder = FolderManager.getFolder(config, configFile, "game folder path");

        String gameFolderPath = gameFolder.toString().replace("\\", "/");

        boolean running = true;

        while (running) {
            System.out.println();
            System.out.println("1. List mod packages: ");
            System.out.println("2. List store items: ");
            System.out.println("3. Change mods folder: ");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    ModScanner.forEachModZip(modsFolder, zip -> {
                        ModDescParser parser = new ModDescParser();
                        ModInfo result = null;
                        try {
                            result = parser.parseModDesc(zip);
                        } catch (ParserConfigurationException | IOException | SAXException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("\n" + result.fileName);
                        System.out.println(result.modName);
                        System.out.println(result.authorName);
                        System.out.println(result.modVersion);});
                    break;
                case "2":
                    ModDescParser modParser = new ModDescParser();
                    StoreItemParser storeParser = new StoreItemParser();
                    List<StoreItem> items = new ArrayList<>();
                    ModScanner.forEachModZip(modsFolder, zip -> {
                        try {
                            ModInfo modInfo = modParser.parseModDesc(zip);
                                for (String storeItem : modInfo.storeItemPaths) {
                                    StoreItem item = storeParser.parseStoreItem(zip, storeItem, gameFolder);
                                    if (!storeItem.isEmpty() && !item.getCategory().equals("placeable")) {

                                        // Display Name

                                        System.out.println("\n" + item.getDisplayName());

                                        // Motor Spec

                                        item.getSpec(MotorSpec.class).ifPresent( motor -> {
                                            if(Objects.equals(motor.getMinPower(), motor.getMaxPower())){
                                                System.out.println("Power: " + motor.getMinPower() + " hp");
                                            }
                                            else{
                                                System.out.println("Power: " + motor.getMinPower() + "-" + motor.getMaxPower() + " hp");
                                            }
                                            System.out.println("Transmission: " + motor.getTransmission());
                                            System.out.println("Fuel Capacity: " + motor.getFuelCapacity() + " " + motor.getDisplayUnit());
                                            System.out.println("Max Speed: " + motor.getMaxSpeed() + " km/h");
                                        });


                                        // Needed Power Spec

                                        item.getSpec(NeededPowerSpec.class).ifPresent( neededPower -> {
                                            System.out.println("Needed power: " + neededPower.getNeededPower() + " hp");
                                        });

                                        // Bale Spec

                                        item.getSpec(BaleSpec.class).ifPresent( bale -> {
                                            if (Objects.equals(bale.getbaleType(), "Round") && bale.getMinBaleDiameter() != 0) {
                                                System.out.println("Round: " + bale.getMinBaleDiameter() + "-" + bale.getMaxBaleDiameter() + " cm");
                                            } else if (Objects.equals(bale.getbaleType(), "Square") && bale.getMinBaleLength() != 0) {
                                                System.out.println("Square: " + bale.getMinBaleLength() + "-" + bale.getMaxBaleLength() + " cm");
                                            }
                                            else if (bale.getMinBaleDiameter() != 0 && bale.getMinBaleLength() != 0){
                                                System.out.println("Round Bales: " + bale.getMinBaleDiameter() + "-" + bale.getMaxBaleDiameter() + " cm");
                                                System.out.println("Square Bales: " + bale.getMinBaleLength() + "-" + bale.getMaxBaleLength() + " cm");
                                            }
                                        });

                                        // Weight Spec

                                        item.getSpec(WeightSpec.class).ifPresent( weight -> {
                                            DecimalFormat tonFormat = new DecimalFormat("#.#");

                                            if(weight.getWeight() > 1000) {
                                                System.out.printf("Weight: %s t%n", tonFormat.format(weight.getWeight() / 1000.0));
                                            }
                                            else{
                                                System.out.printf("Weight: %.0f kg%n", weight.getWeight());
                                            }
                                        });

                                        // Working Width Spec

                                        item.getSpec(WorkingWidthSpec.class).ifPresent(width -> {
                                            System.out.println("Working Width: " + width.getWorkingWidth() + " meters");
                                        });

                                        // Working Speed Spec

                                        item.getSpec(WorkingSpeedSpec.class).ifPresent(speed -> {
                                            System.out.println("Working Speed: " + speed.getWorkingSpeed() + " km/h");
                                        });

                                        // Sowing Spec

                                        item.getSpec(SowingSpec.class).ifPresent(seed -> {
                                            if(seed.isUseDirectPlanting()) {
                                                System.out.println("Has direct seed function.");
                                            }
                                            else{
                                                System.out.println("Doesn't have direct seed function.");
                                            }

                                            Set<String> cropTypes = find(seed.getSeedFruitTypeCategories().trim().toUpperCase(Locale.ROOT));

                                            System.out.println("Crop types: " + cropTypes);
                                        });

                                        // Fill Spec

                                        item.getSpec(FillSpec.class).ifPresent(fill -> {
                                            if (!fill.getFillUnits().equals("None")) {
                                                System.out.print("Capacity: ");
                                                if (fill.getMaxCapacity() > 0 && fill.getMinCapacity() > 0 && fill.getMaxCapacity() != fill.getMinCapacity()) {
                                                    System.out.println(fill.getMinCapacity()  + "-" + fill.getMaxCapacity() + " " + fill.getDisplayUnit());
                                                }
                                                else {
                                                    System.out.println(fill.getMaxCapacity() + " " + fill.getDisplayUnit());
                                                }

                                                Set<String> fillTypes = find(fill.getFillTypes().trim().toUpperCase(Locale.ROOT));

                                                System.out.print("Fill types: ");
                                                if(!fillTypes.isEmpty()) {
                                                    System.out.println(fillTypes);
                                                }
                                                else{
                                                    System.out.println(fill.getFillTypes().trim().toUpperCase(Locale.ROOT));
                                                }
                                            }
                                        });

                                        items.add(item);
                                    }
                                }

                        } catch (ParserConfigurationException | IOException | SAXException e) {
                            System.out.println("Failed to parse" + zip);
                        }
                    });

                    System.out.println("Export to CSV?");
                    String answer = scanner.nextLine();
                    if(answer.equalsIgnoreCase("yes")) {
                        SaveCSV.save(items);
                    }
                    break;
                case "3":
                    modsFolder = FolderManager.changeFolder(config, configFile, "modsFolderPath");
                    break;
                case "4":
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
        scanner.close();
    }
}
