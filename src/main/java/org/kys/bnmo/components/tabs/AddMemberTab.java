package org.kys.bnmo.components.tabs;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.components.bases.FormBuilder;
import org.kys.bnmo.components.documents.BillDocument;
import org.kys.bnmo.helpers.StyleLoadHelper;

public class AddMemberTab extends TabContainer {
    private static final FormBuilder formBuilder = new FormBuilder();
    @Override
    protected Pane getContent() {

        // Initialize form to add member
        formBuilder.addTitle("Member Details");
        formBuilder.addTextBox("Name", "Enter member name");
        formBuilder.addTextBox("Telephone", "Enter telephone number");
        formBuilder.addDropdown("Member Level", "Select the member level", new String[] {"Regular", "VIP", "Non-Active"});
        formBuilder.addTextBox("Points", "Enter amount of points");
        formBuilder.addButton("Save");

        return formBuilder.getAndResetComponent();
    }

    @Override
    protected void additionalAction()
    {
        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Add Member");
    }
}
