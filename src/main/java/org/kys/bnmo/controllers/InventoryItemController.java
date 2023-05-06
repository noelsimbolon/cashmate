package org.kys.bnmo.controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.io.IOException;

public class InventoryItemController {
    private final DataStore dataStore;

    public InventoryItemController() {
        dataStore = new DataStore();
    }

    public Image getFXImage(String fileName) throws IOException {
        return SwingFXUtils.toFXImage(dataStore.readImage(fileName), null);
    }
}
