package org.kys.bnmo.components.tabs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kys.bnmo.components.bases.FormBuilder;
import org.kys.bnmo.controllers.SettingsController;

import java.io.IOException;
import java.util.Objects;

public class SettingTab extends TabContainer {
    private static final FormBuilder formBuilder = new FormBuilder();
    private static final SettingsController settingsController = new SettingsController();
    private Stage stage;

    // State saver
    private StringProperty folderPath;
    private Property<String> fileFormat;
    EventHandler<ActionEvent> saveButtonAction;

    public SettingTab(Stage stage) {
        settingsController.loadConfig();
        this.folderPath = new SimpleStringProperty(settingsController.getFolderPath());
        this.fileFormat = new SimpleStringProperty(settingsController.getFileFormat());
        this.stage = stage;

        this.saveButtonAction = (event) -> {
            System.out.println(folderPath.getValue());
            System.out.println(fileFormat.getValue());

            settingsController.loadConfig();

            if (!Objects.equals(this.folderPath.getValue(), settingsController.getFolderPath())) {
                try {
                    settingsController.changeFolderPath(this.folderPath.getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (!Objects.equals(this.fileFormat.getValue(), settingsController.getFileFormat())) {
                settingsController.changeFileFormat(this.fileFormat.getValue());
            }
        };
    }

    @Override
    protected Pane getContent() {

        // Initialize form to add member
        formBuilder.addFolderPicker("Data Store Location", stage, folderPath);
        formBuilder.addDropdown("Data Store Format", "Select a Format", new String[]{"json", "xml", "obj"}, fileFormat);
        formBuilder.addButton("Save", saveButtonAction);

        return formBuilder.getAndResetComponent();
    }

    @Override
    protected void additionalAction() {
        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Settings");
    }
}
