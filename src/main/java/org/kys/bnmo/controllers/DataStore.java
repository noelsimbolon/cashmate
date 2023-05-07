package org.kys.bnmo.controllers;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.kys.bnmo.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataStore {

    private final static ArrayList<String> defaultConfig = new ArrayList<>() {{
        add(System.getProperty("user.dir") + "\\data");  // Data Location
        add("json");  // Data Format
    }};
    private static final Map<String, Adapter> adapterMap = new HashMap<>() {{
        put("json", new JSONAdapter());
        put("obj", new OBJAdapter());
        put("xml", new XMLAdapter());
    }};
    private static final Map<String, Class> classFileNameMap = new HashMap<>() {{
        put("customer", Customer.class);
        put("inventory-item", InventoryItem.class);
        put("member", Member.class);
        put("order", Order.class);
        put("transaction", UnpopulatedTransaction.class);
    }};
    @Getter
    private static String folderPath;
    @Getter
    private static String fileFormat;
    private Adapter adapter;

    private void setAdapter(String filename) {
        this.adapter = getSuitableAdapter(fileFormat);
    }

    private Adapter getSuitableAdapter(String format) {
        if (format == null) throw new NullPointerException("File Format hasn't been initialized yet");
        if (!adapterMap.containsKey(format)) throw new IllegalArgumentException(format + " is not supported");

        return adapterMap.get(format);
    }

    public void loadConfig() {
        File configFile = new File("config.xml");

        folderPath = System.getProperty("user.dir");
        if (configFile.exists()) {
            fileFormat = "xml";
            ArrayList<String> config = readData("config", String.class);
            folderPath = config.get(0);
            fileFormat = config.get(1);
        } else {
            fileFormat = "xml";
            writeData("config", new ArrayList<>(defaultConfig));
            folderPath = defaultConfig.get(0);
            fileFormat = defaultConfig.get(1);
        }
    }

    public void writeConfigAPI(@NotNull String filename, String content) throws Exception {
        if (filename.equals("config")) throw new Exception("Unallowed to overwrite default config file");

        String oldFolderPath = folderPath;
        String oldFormat = fileFormat;

        folderPath = System.getProperty("user.dir");
        fileFormat = "xml";

        File file = new File(filename);
        writeData(filename, new ArrayList<>() {{
            add(content);
        }});

        folderPath = oldFolderPath;
        fileFormat = oldFormat;
    }

    public String loadConfigAPI(@NotNull String filename) throws Exception {
        if (filename.equals("config")) throw new Exception("Unallowed to read default config file");

        String oldFolderPath = folderPath;
        String oldFormat = fileFormat;

        folderPath = System.getProperty("user.dir");
        fileFormat = "xml";

        File file = new File(filename);

        ArrayList<String> config = readData(filename, String.class);

        folderPath = oldFolderPath;
        fileFormat = oldFormat;

        if (config.size() == 1) {
            return config.get(0);
        } else {
            throw new Exception("Invalid Config Format");
        }
    }

    private void moveData(String newFolderPath) throws IOException {
        File oldFolder = new File(folderPath);
        File newFolder = new File(newFolderPath);

        if (!oldFolder.exists()) return;

        if (!newFolder.exists()) {
            boolean isSuccess = newFolder.mkdirs();
            if (!isSuccess) throw new IOException("Failed to create new folder");
        }

        for (File file : Objects.requireNonNull(oldFolder.listFiles())) {
            Files.move(file.toPath(), new File(newFolder, file.getName()).toPath());
        }
    }

    public void setFolderPath(String newFolderPath, boolean moveData) throws IOException {
        if (moveData) moveData(newFolderPath);

        File configFile = new File("config.xml");
        if (!configFile.exists()) loadConfig();

        String tempFileFormat = fileFormat;
        folderPath = System.getProperty("user.dir");
        fileFormat = "xml";

        ArrayList<String> config = readData("config", String.class);
        config.set(0, newFolderPath);
        writeData("config", config);

        fileFormat = tempFileFormat;
        folderPath = newFolderPath;
    }

    private void changeDatabaseFormat(String newFileFormat) {
        File folder = new File(folderPath);
        if (!folder.exists()) return;

        File[] files = folder.listFiles();
        if (files == null) return;

        String oldFormat = fileFormat;
        for (File file : files) {
            if (file.getName().endsWith(oldFormat)) {
                String fileName = file.getName().replaceFirst("[.][^.]+$", "");
                ArrayList<?> data = readData(fileName, classFileNameMap.get(fileName));
                fileFormat = newFileFormat;
                writeData(fileName, data);
                fileFormat = oldFormat;

                // Remove the old file
                File oldFile = new File(folderPath, fileName + "." + oldFormat);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
        }
    }

    public void setFileFormat(@NotNull String newFileFormat, boolean changeData) throws IllegalArgumentException {
        if (!newFileFormat.equals("xml") &&
                !newFileFormat.equals("json") &&
                !newFileFormat.equals("obj")) {
            throw new IllegalArgumentException("Not a valid file format.");
        }

        if (changeData) changeDatabaseFormat(newFileFormat);

        File configFile = new File("config.xml");
        if (!configFile.exists()) loadConfig();

        String tempFolderPath = folderPath;
        folderPath = System.getProperty("user.dir");
        fileFormat = "xml";

        ArrayList<String> config = readData("config", String.class);
        config.set(1, newFileFormat);
        writeData("config", config);

        folderPath = tempFolderPath;
        fileFormat = newFileFormat;
    }

    public <T> ArrayList<T> readData(String filename, Class<T> dataType) {
        File file = new File(folderPath + "\\" + filename + "." + fileFormat);
        setAdapter(file.getName());
        return adapter.readFile(file.getAbsolutePath(), dataType);
    }

    public void writeData(String filename, ArrayList<?> data) {
        File file = new File(folderPath + "\\" + filename + "." + fileFormat);
        setAdapter(file.getName());

        File parentFolder = file.getParentFile();
        if (!parentFolder.exists()) parentFolder.mkdirs();

        adapter.writeFile(file.getAbsolutePath(), data);
    }
}
