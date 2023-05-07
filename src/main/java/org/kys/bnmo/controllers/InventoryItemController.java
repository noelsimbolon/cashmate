package org.kys.bnmo.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kys.bnmo.helpers.plugins.PluginLoader;
import org.kys.bnmo.model.InventoryItem;
import org.kys.bnmo.model.Member;
import org.kys.bnmo.model.Modifiable;
import org.kys.bnmo.plugins.adapters.PageAdapter;
import org.kys.bnmo.plugins.base.PluginService;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InventoryItemController {
    private final DataStore dataStore;

    public InventoryItemController() {
        dataStore = new DataStore();
    }

    private void processGetData(List<InventoryItem> items)
    {
        PluginLoader pluginLoader = new PluginLoader();
        pluginLoader.runClasses(new PluginService(null , null, new Modifiable(null, items, null, true)));

    }

    private void processSetData(List<InventoryItem> items)
    {
        PluginLoader pluginLoader = new PluginLoader();
        pluginLoader.runClasses(new PluginService(null , null, new Modifiable(null, items, null,false)));

    }

    public void loadConfig() {
        dataStore.loadConfig();
    }

    public ArrayList<InventoryItem> readInventoryItems() {
        ArrayList<InventoryItem> data = dataStore.readData("inventory-item", InventoryItem.class);
        processGetData(data);
        return data;
    }

    public void writeInventoryItems(ArrayList<InventoryItem> inventoryItems) {
        processSetData(inventoryItems);
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

    public void deleteInventoryItemByUUID(UUID uuid) {
        ArrayList<InventoryItem> inventoryItems = readInventoryItems();

        for (InventoryItem item : inventoryItems) {
            if (item.getItemID().equals(uuid)) {
                inventoryItems.remove(item);
                break;
            }
        }

        writeInventoryItems(inventoryItems);
    }
}
