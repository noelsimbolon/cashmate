package org.kys.bnmo.controllers;

import org.kys.bnmo.model.Customer;
import org.kys.bnmo.model.InventoryItem;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

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
            // Inventory Item
            // Work
            ArrayList<InventoryItem> items = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                items.add(new InventoryItem(10, "item " + (i + 1), 100, "Food", "food.png"));
            }

            dataStore.writeData("inventory-item.xml", items);
            ArrayList<InventoryItem> readItems = dataStore.readData("inventory-item.xml", InventoryItem.class);

            for (int i = 0; i < 5; i++) {
                System.out.println(items.get(i).getItemID());
                System.out.println(readItems.get(i).getItemID());
            }

            // Customer
            // Belum Work
            // ArrayList<Customer> customers = new ArrayList<>();
            // for (int i = 0; i < 5; i++) {
            //     customers.add(new Customer());
            // }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
