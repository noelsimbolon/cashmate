package org.kys.bnmo.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.imageio.ImageIO;

public class DataStore {
    private static String folderPath = System.getProperty("user.dir");
    private Adapter adapter;

    private static final Map<String, Adapter> adapterMap = new HashMap<>() {{
        put("json", new JSONAdapter());
        put("obj", new OBJAdapter());
        put("xml", new XMLAdapter());
    }};

    private void setAdapter(String filename) {
        String format = filename.split("\\.")[1];
        this.adapter = getSuitableAdapter(format);
    }

    private Adapter getSuitableAdapter(String format) {
        if (format == null) throw new NullPointerException("File Format hasn't been initialized yet");
        if (!adapterMap.containsKey(format)) throw new IllegalArgumentException(format + " is not supported");

        return adapterMap.get(format);
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

    public void loadFolderPath() throws IOException {
        if ((new File("config.xml")).exists()) {
            folderPath = readData("config.xml", String.class).get(0);
        } else {
            setFolderPath(System.getProperty("user.dir") + "\\data", false);
        }
    }

    public void setFolderPath(String newFolderPath, boolean moveData) throws IOException {
        if (moveData) moveData(newFolderPath);

        folderPath = (new File(System.getProperty("user.dir"))).getAbsolutePath();
        ArrayList<String> config = new ArrayList<>();
        config.add(newFolderPath);
        writeData("config.xml", config);

        folderPath = newFolderPath;
    }

    public <T> ArrayList<T> readData(String filename, Class<T> dataType) {
        File file = new File(folderPath + "\\" + filename);
        setAdapter(file.getName());
        return adapter.readFile(file.getAbsolutePath(), dataType);
    }

    public void writeData(String filename, ArrayList<?> data) {
        File file = new File(folderPath + "\\" + filename);
        setAdapter(file.getName());

        File parentFolder = file.getParentFile();
        if (!parentFolder.exists()) parentFolder.mkdirs();
        adapter.writeFile(file.getAbsolutePath(), data);
    }

    public BufferedImage readImage(String filename) throws IOException {
        File file = new File(folderPath + "\\images\\" + filename);
        return ImageIO.read(file);
    }

    public void writeImage(String filename, BufferedImage image) {
        File file = new File(folderPath + "\\images\\" + filename);
        File parentFolder = file.getParentFile();
        if (!parentFolder.exists()) parentFolder.mkdirs();

        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
