package org.kys.bnmo.components;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AddMember implements ComponentFactory {
    private final VBox root;

    public AddMember() {
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
        root.getChildren().add(memberForm.getComponent());

        // Style the root
        root.getStyleClass().add("add-member-container");
    }

    public Parent getComponent() {

        try {
            String css = this.getClass()
                    .getResource("/styles/addMember.css")
                    .toExternalForm();

            root.getStylesheets().add(css);
        }

        catch (NullPointerException e)
        {
            System.out.println("Failed to load css for memberList component!");
        }

        return root;
    }

}
