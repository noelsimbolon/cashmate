package org.kys.bnmo.components.tabs;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kys.bnmo.components.bases.FormBuilder;

public class SettingTab extends TabContainer {
    private static final FormBuilder formBuilder = new FormBuilder();

    private Stage stage;

    public SettingTab(Stage stage)
    {
        this.stage = stage;
    }

    @Override
    protected Pane getContent() {

        // Initialize form to add member
        formBuilder.addFilePicker("Save Folder Location", stage);
        formBuilder.addDropdown("Save file format", "Select the member level", new String[] {"JSON", "XML", "OBJ"});
        formBuilder.addButton("Save", null);

        // TODO: Connect save button to save the settings

        return formBuilder.getAndResetComponent();
    }

    @Override
    protected void additionalAction()
    {
        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Settings");
    }
}
