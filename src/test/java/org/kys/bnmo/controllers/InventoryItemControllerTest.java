package org.kys.bnmo.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kys.bnmo.model.InventoryItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class InventoryItemControllerTest {

    private static DataStore dataStore;
    private static InventoryItemController inventoryItemController;

    @BeforeAll
    static void setUp() throws IOException {
        dataStore = new DataStore();
        String dataStoreTestFolderPath = (new File("src\\test\\data")).getAbsolutePath();
        dataStore.setFolderPath(dataStoreTestFolderPath, false);
        dataStore.setFileFormat("json", false);

        inventoryItemController = new InventoryItemController();
    }

    @Test
    void loadConfig() throws IOException {
        // Act
        inventoryItemController.loadConfig();

        // Assert
        Assertions.assertEquals("json", DataStore.getFileFormat());
        Assertions.assertEquals(System.getProperty("user.dir") + "\\src\\test\\data", DataStore.getFolderPath());

        // Reset folder path and file format
        String dataStoreTestFolderPath = (new File("src\\test\\data")).getAbsolutePath();
        dataStore.setFolderPath(dataStoreTestFolderPath, false);
        dataStore.setFileFormat("json", false);
    }

    @Test
    void readInventoryItems() {
        // Arrange
        var items = new ArrayList<InventoryItem>();
        var item1 = new InventoryItem("Croissant", "Food", 10, 25000.0, 10000.0, "food.png");
        var item2 = new InventoryItem("Coca Cola", "Beverage", 15, 15000.0, 5000.0, "beverage.png");
        items.add(item1);
        items.add(item2);

        // Act
        dataStore.writeData("inventory-item", items);
        ArrayList<InventoryItem> readItems = inventoryItemController.readInventoryItems();

        // Asert
        Assertions.assertEquals(item1.getItemID(), readItems.get(0).getItemID());
        Assertions.assertEquals(item2.getItemID(), readItems.get(1).getItemID());
    }

    @Test
    void writeInventoryItems() {
        // Arrange
        var items = new ArrayList<InventoryItem>();
        var item1 = new InventoryItem("Croissant", "Food", 10, 25000.0, 10000.0, "food.png");
        var item2 = new InventoryItem("Coca Cola", "Beverage", 15, 15000.0, 5000.0, "beverage.png");
        items.add(item1);
        items.add(item2);

        // Act
        inventoryItemController.writeInventoryItems(items);
        ArrayList<InventoryItem> readItems = dataStore.readData("inventory-item", InventoryItem.class);

        // Asert
        Assertions.assertEquals(item1.getItemID(), readItems.get(0).getItemID());
        Assertions.assertEquals(item2.getItemID(), readItems.get(1).getItemID());
    }

    @Test
    void getInventoryItemByUUID() {
        // Arrange
        var items = new ArrayList<InventoryItem>();
        var item1 = new InventoryItem("Croissant", "Food", 10, 25000.0, 10000.0, "food.png");
        var item2 = new InventoryItem("Coca Cola", "Beverage", 15, 15000.0, 5000.0, "beverage.png");
        items.add(item1);
        items.add(item2);

        // Act
        dataStore.writeData("inventory-item", items);
        ArrayList<InventoryItem> readItems = dataStore.readData("inventory-item", InventoryItem.class);
        InventoryItem targetItem = inventoryItemController.getInventoryItemByUUID(readItems, item2.getItemID());

        // Asert
        assert targetItem != null;
        Assertions.assertEquals(item2.getItemID(), targetItem.getItemID());
    }

    @Test
    void deleteInventoryItemByUUID() {
        // Arrange
        var items = new ArrayList<InventoryItem>();
        var item1 = new InventoryItem("Croissant", "Food", 10, 25000.0, 10000.0, "food.png");
        var item2 = new InventoryItem("Coca Cola", "Beverage", 15, 15000.0, 5000.0, "beverage.png");
        items.add(item1);
        items.add(item2);

        // Act
        dataStore.writeData("inventory-item", items);
        inventoryItemController.deleteInventoryItemByUUID(item1.getItemID());
        ArrayList<InventoryItem> readItems = dataStore.readData("inventory-item", InventoryItem.class);

        // Asert
        Assertions.assertEquals(item2.getItemID(), readItems.get(0).getItemID());
    }
}
