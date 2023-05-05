package org.kys.bnmo.components.tabs;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.kys.bnmo.components.bases.FormBuilder;
import org.kys.bnmo.helpers.IconButtonHelper;

public class AddMemberTab extends TabContainer {

    private static final FormBuilder formBuilder = new FormBuilder();

    private static final IconButtonHelper iconButtonHelper = new IconButtonHelper();

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
        Button backButton = new Button();
        new IconButtonHelper().setButtonGraphic(backButton, "/icon/BackArrow.png", 20, 20);
        backButton.getStyleClass().add("back-button");
        getHeader().getChildren().add(0, backButton);

        getRoot().getStyleClass().add("fill-tab-content");
        addHeaderTitle("Add Member");
    }
}
