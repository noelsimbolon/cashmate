package org.kys.bnmo.components;

import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.scene.Parent;

public class FormBuilder implements ComponentFactory {

    private final VBox root;

    private final VBox inputFields;

    public FormBuilder() {
        this.root = new VBox();
        this.inputFields = new VBox();
        root.getStyleClass().add("form-container");
        inputFields.getStyleClass().add("input-field-container");
        root.getChildren().add(inputFields);
    }

    public void addTitle(String title) {
        Label titleLabel = new Label(title);
        Font font = Font.loadFont(getClass().getResource("/fonts/FontAwesome.otf").toExternalForm(), 24);
        titleLabel.setFont(font);
        titleLabel.getStyleClass().add("form-title");
        root.getChildren().add(0, titleLabel);
    }

    public void addTextBox(String label, String placeholder) {
        // Layout
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        // Label
        Label labelComponent = new Label(label);
        Font font = Font.loadFont(getClass().getResource("/fonts/FontAwesome.otf").toExternalForm(), 16);
        labelComponent.setFont(font);
        labelComponent.getStyleClass().add("form-label");

        // Text field
        TextField textField = new TextField();
        textField.setPromptText(placeholder);
        textField.getStyleClass().add("form-text-field");
        HBox.setHgrow(textField, Priority.ALWAYS);

        // Add components to root
        row.getChildren().addAll(labelComponent, textField);
        row.getStyleClass().add("form-input");
        inputFields.getChildren().add(row);
    }

    public void addDropdown(String label, String placeholder, String[] items) {
        // Layout
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        // Label
        Label labelComponent = new Label(label);
        Font font = Font.loadFont(getClass().getResource("/fonts/FontAwesome.otf").toExternalForm(), 16);
        labelComponent.setFont(font);
        labelComponent.getStyleClass().add("form-label");

        // Dropdown
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText(placeholder);
        comboBox.getStyleClass().add("form-dropdown");
        comboBox.getItems().addAll(items);
        HBox.setHgrow(comboBox, Priority.ALWAYS);

        // Add components to root
        row.getChildren().addAll(labelComponent, comboBox);
        row.getStyleClass().add("form-input");
        inputFields.getChildren().add(row);
    }

    public void addButton(String label) {
        // Create button
        Button button = new Button(label);
        Font font = Font.loadFont(getClass().getResource("/fonts/FontAwesome.otf").toExternalForm(), 20);
        button.setFont(font);
        button.getStyleClass().add("form-button");
        HBox.setHgrow(button, Priority.ALWAYS);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Add components to root
        HBox row = new HBox(spacer, button);
        row.getStyleClass().add("form-row");
        row.setAlignment(Pos.CENTER_RIGHT);
        root.getChildren().add(row);
    }


    public Parent getComponent() {

        try {
            String css = this.getClass()
                    .getResource("/styles/forms.css")
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
