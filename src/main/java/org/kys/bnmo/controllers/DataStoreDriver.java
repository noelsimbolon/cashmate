package org.kys.bnmo.controllers;

import java.awt.image.BufferedImage;
import java.io.File;

public class DataStoreDriver {
    public static void main(String[] args) {
        DataStore dataStore = new DataStore();

        try {
            // Load and Set Folder Path
            // Work
            dataStore.loadFolderPath();
            String dataTestFolderPath = (new File("test\\data")).getAbsolutePath();
            dataStore.setFolderPath(dataTestFolderPath, false);

            // Read and Write Image
            // Work
            // BufferedImage image = dataStore.readImage(filename);
            // dataStore.writeImage(filename, image);

            // TODO: Write and Read Data
            // Masih ada data yang gabisa diwrite
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
