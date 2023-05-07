package org.kys.bnmo.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kys.bnmo.model.InventoryItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class InventoryItemController {
    private final DataStore dataStore;

    public InventoryItemController() {
        dataStore = new DataStore();
    }

    public Image getFXImage(String fileName) throws IOException {
        return SwingFXUtils.toFXImage(dataStore.readImage(fileName), null);
    }

    public void loadConfig() throws IOException {
        dataStore.loadConfig();
    }

    public ArrayList<InventoryItem> readInventoryItems() {
        return dataStore.readData("inventory-item", InventoryItem.class);
    }

    public void writeInventoryItems(ArrayList<InventoryItem> inventoryItems) {
        dataStore.writeData("inventory-item", inventoryItems);
    }

    @Nullable
    public InventoryItem getInventoryItemByUUID(@NotNull ArrayList<InventoryItem> inventoryItems, UUID uuid) {
        for (InventoryItem item : inventoryItems) {
            if (item.getItemID().equals(uuid)) {
                return item;
            }
        }

        return null;
    }
}
