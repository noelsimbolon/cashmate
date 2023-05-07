package org.kys.bnmo.components.tabs;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kys.bnmo.components.bases.FormBuilder;
import org.kys.bnmo.controllers.SettingsController;
import org.kys.bnmo.helpers.plugins.PluginLoader;
import org.kys.bnmo.plugins.adapters.PageAdapter;
import org.kys.bnmo.plugins.adapters.SettingAdapter;
import org.kys.bnmo.plugins.base.PluginService;
import org.kys.bnmo.plugins.interfaces.PluginServiceInterface;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;

public class SettingTab extends TabContainer {
    private static final FormBuilder formBuilder = new FormBuilder();
    private static final SettingsController settingsController = new SettingsController();
    private Stage stage;

    // State saver
    private StringProperty folderPath;
    private StringProperty jarPath;
    private Property<String> fileFormat;
    private EventHandler<ActionEvent> saveButtonAction;

    public SettingTab(Stage stage) {
        settingsController.loadConfig();
        this.jarPath = new SimpleStringProperty("");
        this.folderPath = new SimpleStringProperty(settingsController.getFolderPath());
        this.fileFormat = new SimpleStringProperty(settingsController.getFileFormat());
        this.stage = stage;

        this.saveButtonAction = (event) -> {
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

            try {
                File jarFile = new File(jarPath.getValue());
                Files.copy(jarFile.toPath(), new File("$plugins", jarFile.getName()).toPath());
            }

            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        };
    }

    @Override
    protected Pane getContent() {

        // Initialize form to add member
        formBuilder.addFolderPicker("Data Store Location", stage, folderPath);
        formBuilder.addFilePicker("Load Plugin", stage, jarPath, new FileChooser.ExtensionFilter("Jar files (*.jar)", "*.jar"));
        formBuilder.addDropdown("Data Store Format", "Select a Format", new String[]{"json", "xml", "obj"}, fileFormat);

        PluginLoader pluginLoader = new PluginLoader();

        PluginService service = new PluginService(null, new SettingAdapter(formBuilder), null);

        pluginLoader.runClasses(service);

        List<EventHandler<ActionEvent>> handlers = new ArrayList<>(service.getSettingSaveActions());
        handlers.add(saveButtonAction);

        formBuilder.addButton("Save", handlers.toArray(new EventHandler[0]));


        return formBuilder.getAndResetComponent();
    }

    @Override
    protected void additionalAction() {
        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Settings");
    }
}
