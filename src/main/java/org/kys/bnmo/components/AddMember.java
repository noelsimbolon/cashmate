package org.kys.bnmo.components;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.kys.bnmo.helpers.LoadHelper;
import org.kys.bnmo.helpers.StyleLoadHelper;

public class AddMember implements ComponentFactory {
    private VBox root;

    public AddMember() {

    }

    public Pane getComponent() {
        // Initialize root
        this.root = new VBox();

        // Create the page title
        Label title = new Label("Add Member");
        title.getStyleClass().add("add-member-title");

        // Initialize form to add member
        FormBuilder memberForm = new FormBuilder();
        memberForm.addTitle("Member Details");
        memberForm.addTextBox("Name", "Enter member name");
        memberForm.addTextBox("Telephone", "Enter telephone number");
        memberForm.addDropdown("Member Level", "Select the member level", new String[] {"Regular", "VIP", "Non-Active"});
        memberForm.addTextBox("Points", "Enter amount of points");
        memberForm.addButton("Save");

        // Add components to the root
        root.getChildren().add(title);
        root.getChildren().add(memberForm.getAndResetComponent());

        // Style the root
        root.getStyleClass().add("add-member-container");

        StyleLoadHelper helper = new StyleLoadHelper("/styles/addMember.css");

        helper.load(root);

        return root;
    }

}
