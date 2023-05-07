package org.kys.bnmo.controllers;

import java.io.IOException;

public class SettingsController {
    private final DataStore dataStore;

    public SettingsController() {
        this.dataStore = new DataStore();
    }

    public void loadConfig() {
        dataStore.loadConfig();
    }

    public String getFolderPath() {
        return DataStore.getFolderPath();
    }

    public String getFileFormat() {
        return DataStore.getFileFormat();
    }

    public void changeFileFormat(String newFileFormat) {
        dataStore.setFileFormat(newFileFormat, true);
    }

    public void changeFolderPath(String newFolderPath) throws IOException {
        dataStore.setFolderPath(newFolderPath, true);
    }
}
