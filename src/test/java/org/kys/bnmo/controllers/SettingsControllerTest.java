package org.kys.bnmo.controllers;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SettingsControllerTest {

    private static DataStore dataStore;
    private static SettingsController settingsController;

    @BeforeAll
    static void setUp() throws IOException {
        dataStore = new DataStore();
        String dataStoreTestFolderPath = (new File("src\\test\\data")).getAbsolutePath();
        dataStore.setFolderPath(dataStoreTestFolderPath, false);
        dataStore.setFileFormat("json", false);

        settingsController = new SettingsController();
    }

    @Test
    @Order(1)
    void loadConfig() throws IOException {
        // Act
        settingsController.loadConfig();

        // Assert
        Assertions.assertEquals("json", DataStore.getFileFormat());
        Assertions.assertEquals(System.getProperty("user.dir") + "\\src\\test\\data", DataStore.getFolderPath());

        // Reset folder path and file format
        String dataStoreTestFolderPath = (new File("src\\test\\data")).getAbsolutePath();
        dataStore.setFolderPath(dataStoreTestFolderPath, false);
        dataStore.setFileFormat("json", false);
    }

    @Test
    @Order(2)
    void getFolderPath() {
        // Act
        String folderPath = settingsController.getFolderPath();

        // Assert
        Assertions.assertEquals(System.getProperty("user.dir") + "\\src\\test\\data", folderPath);
    }

    @Test
    @Order(3)
    void getFileFormat() {
        // Act
        String fileFormat = settingsController.getFileFormat();

        // Assert
        Assertions.assertEquals("json", fileFormat);
    }

    @Test
    @Order(4)
    void changeFileFormatToXMLResetToJSON() {
        // Act
        settingsController.changeFileFormat("xml");

        // Assert
        Assertions.assertEquals("xml", DataStore.getFileFormat());

        // Reset file format back to json
        dataStore.setFileFormat("json", false);
    }

    @Test
    @Order(5)
    void changeFileFormatToOBJResetToJSON() {
        // Act
        settingsController.changeFileFormat("obj");

        // Assert
        Assertions.assertEquals("obj", DataStore.getFileFormat());

        // Reset file format back to json
        dataStore.setFileFormat("json", false);
    }

    @Test
    @Order(6)
    void changeFolderPath() throws IOException {
        // Reset file format back to json
        dataStore.setFileFormat("json", false);

        // Arrange
        String directoryPath = System.getProperty("user.dir") + "\\src\\test\\dummy";
        var directory = new File(directoryPath);
        if (directory.exists()) {
            deleteDirectory(directory);
        }
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Act
        settingsController.changeFolderPath(directoryPath);

        // Assert
        Assertions.assertEquals(directoryPath, DataStore.getFolderPath());

        // Reset folder path and remove made path
        dataStore.setFolderPath(System.getProperty("user.dir") + "\\src\\test\\data", false);
        if (directory.exists()) {
            deleteDirectory(directory);
        }
    }

    private void deleteDirectory(@NotNull File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }
}