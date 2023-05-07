package org.kys.bnmo.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kys.bnmo.model.InventoryItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class DataStoreTest {

    private static DataStore dataStore;

    @BeforeAll
    static void setUp() throws IOException {
        dataStore = new DataStore();
        String dataStoreTestFolderPath = (new File("src\\test\\data")).getAbsolutePath();
        dataStore.setFolderPath(dataStoreTestFolderPath, false);
        dataStore.setFileFormat("json", false);
    }

    @Test
    void writeReadDataInOBJ() {
        // Set file format to OBJ
        dataStore.setFileFormat("obj", true);

        // Instantiate a new item
        var inventoryItem = new InventoryItem("Bread", "Food", 10, 25000, 10000, "food.png");

        var newItemList = new ArrayList<InventoryItem>();
        newItemList.add(inventoryItem);

        // Write test data to file
        dataStore.writeData("inventory-item", newItemList);

        // Read test data from file
        ArrayList<InventoryItem> items = dataStore.readData("inventory-item", InventoryItem.class);

        // Test that the read data matches the written data
        Assertions.assertEquals(1, items.size());
        Assertions.assertEquals(inventoryItem.getItemID(), items.get(0).getItemID());
        Assertions.assertEquals(inventoryItem.getItemName(), items.get(0).getItemName());

        // Set file format back to JSON
        dataStore.setFileFormat("json", true);
    }

    @Test
    void setFolderPathTest() throws IOException {
        // Prepare test data
        String oldFolderPath = DataStore.getFolderPath();
        String newFolderPath = oldFolderPath + "/test";

        // Set new folder path
        dataStore.setFolderPath(newFolderPath, false);

        // Test that the new folder path was set correctly
        Assertions.assertEquals(newFolderPath, DataStore.getFolderPath());

        // Set old folder path back
        dataStore.setFolderPath(oldFolderPath, false);
    }

    @Test
    void setFileFormatTest() {
        // Prepare test data
        String oldFileFormat = DataStore.getFileFormat();
        String newFileFormat = "obj";

        // Set new file format
        dataStore.setFileFormat(newFileFormat, false);

        // Test that the new format was set correctly
        Assertions.assertEquals(newFileFormat, DataStore.getFileFormat());

        // Set the old file format back
        dataStore.setFileFormat(oldFileFormat, false);
    }

    @Test
    void setFileFormatIllegalTest() {
        // Prepare an illegal file format
        String illegalFormat = "txt";
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                dataStore.setFileFormat(illegalFormat, false));
    }

}
