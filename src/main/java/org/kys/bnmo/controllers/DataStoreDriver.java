package org.kys.bnmo.controllers;

public class DataStoreDriver {
    public static void main(String[] args) {
        DataStore dataStore = new DataStore();

        try {
            // Load and Set Folder Path
            dataStore.loadFolderPath();
            dataStore.setFolderPath("D:\\FARHAN\\Kuliah\\Semester 4\\OOP\\Tugas\\bnmo\\data", false);

            // TODO: Write and Read Data
            // Masih ada data yang gabisa diwrite
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
