package org.kys.bnmo.components.tabs;

import javafx.scene.layout.Pane;
import org.kys.bnmo.components.bases.FormBuilder;

public class SettingTab extends TabContainer {
    private static final FormBuilder formBuilder = new FormBuilder();
    @Override
    protected Pane getContent() {

        // Initialize form to add member
        formBuilder.addTextBox("Config file", "Enter file/folder location");
        formBuilder.addDropdown("Save file format", "Select the member level", new String[] {"JSON", "XML", "OBJ"});
        formBuilder.addButton("Save");

        return formBuilder.getAndResetComponent();
    }

    @Override
    public void reset() {

        super.reset();
        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Settings");
    }
}
